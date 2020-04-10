/*
ID: shacse01
LANG: JAVA
TASK: shuttle
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class shuttle {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000000;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 57;
	private static final int MOD = 9901;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;
	
	
	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("shuttle.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shuttle.out")));

		
/*



*/

		int n = in.nextInt();
		StringBuilder initialStr = new StringBuilder();
		StringBuilder finalStr = new StringBuilder();
		for(int i = 0; i < n; i++) {
			initialStr.append("W");
			finalStr.append("B");
		}
		initialStr.append("_");
		finalStr.append("_");
	
		for(int i = 0; i < n; i++) {
			initialStr.append("B");
			finalStr.append("W");
		}
		// solve 1 -> dfs | recursion
		dfs(initialStr, finalStr, new ArrayList<Integer>());
		
		for(List<Integer> list : ans) {
			StringBuilder sb = new StringBuilder();
			int cnt = 0;
			for(Integer e : list) {
//				System.out.print(current.moves.get(i) + " ");
				cnt++;
				sb.append(e);
				if(cnt % 20 == 0 && cnt != list.size()) {
					sb.append("\n");
				}
				else if(cnt != list.size()) {
					sb.append(" ");
				}
			}
//			System.out.println("HERE");
			out.println(sb.toString());
			break;
		}
		
		// solve 2 -> bfs
//		String res = bfs(initialStr, finalStr);
//		out.println(res);
		
		
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	static Set<List<Integer>> ans = new TreeSet<>(new Comparator<List<Integer>>() {

		@Override
		public int compare(List<Integer> o1, List<Integer> o2) {
			int val = Integer.compare(o1.size(), o2.size());
			if(val == 0) {
				int sz = o1.size();
				for(int i = 0; i < sz; i++) {
					if(o1.get(i) < o2.get(i)) {
						return -1;
					}
					else if(o1.get(i) > o2.get(i)) {
						return 1;
					}
				}
				return 0;
			}
			return val;
		}
	});
	
	private static void dfs(StringBuilder currentStr, StringBuilder finalStr, List<Integer> currentMoves) {
		if(isOk(currentStr, finalStr)) {
			ans.add(currentMoves);
			return;
		}
		
		int holeIndex = getHoleIndex(currentStr.toString());
		// move right => W_
		if(holeIndex > 0 && currentStr.charAt(holeIndex - 1) == 'W') {
			StringBuilder sb = new StringBuilder(currentStr);
			sb.setCharAt(holeIndex, 'W');
			sb.setCharAt(holeIndex - 1, '_');
			List<Integer> moves = new ArrayList<Integer>(currentMoves);
			moves.add(holeIndex);
			dfs(sb, finalStr, moves);
		}
		// move left -> _B
		if(holeIndex <  currentStr.length() - 1 && currentStr.charAt(holeIndex + 1) == 'B') {
			StringBuilder sb = new StringBuilder(currentStr);
			sb.setCharAt(holeIndex, 'B');
			sb.setCharAt(holeIndex + 1, '_');
			List<Integer> moves = new ArrayList<Integer>(currentMoves);
			moves.add(holeIndex + 2);
			dfs(sb, finalStr, moves);
		}
		
		// jump right => WB_
		if(holeIndex > 1 && currentStr.charAt(holeIndex - 2) == 'W' 
				&& currentStr.charAt(holeIndex - 1) == 'B') {
			StringBuilder sb = new StringBuilder(currentStr);
			sb.setCharAt(holeIndex, 'W');
			sb.setCharAt(holeIndex - 2, '_');
			List<Integer> moves = new ArrayList<Integer>(currentMoves);
			moves.add(holeIndex - 1);
			dfs(sb, finalStr, moves);
		}
		// jump left => _WB
		if(holeIndex < currentStr.length() - 2 && currentStr.charAt(holeIndex + 2) == 'B'
				&& currentStr.charAt(holeIndex + 1) == 'W') {
			StringBuilder sb = new StringBuilder(currentStr);
			sb.setCharAt(holeIndex, 'B');
			sb.setCharAt(holeIndex + 2, '_');
			List<Integer> moves = new ArrayList<Integer>(currentMoves);
			moves.add(holeIndex + 3);
			dfs(sb, finalStr, moves);
		}
		
	}

	private static String bfs(StringBuilder initialStr, StringBuilder finalStr) {
		Queue<Move> queue = new LinkedList<>();
		queue.add(new Move(initialStr, new ArrayList<Integer>()));
		int len = initialStr.length();
		while(!queue.isEmpty()) {
			Move current = queue.remove();
			StringBuilder currentStr = current.state;
//			System.out.println("Current "  + currentStr);
			if(isOk(currentStr, finalStr)) {
//				System.out.println(current.moves.toArray());
				int cnt = 0;
				StringBuilder sb = new StringBuilder();
				for(Integer e : current.moves) {
//					System.out.print(current.moves.get(i) + " ");
					cnt++;
					sb.append(e);
					if(cnt % 20 == 0 && cnt != current.moves.size()) {
						sb.append("\n");
					}
					else if(cnt != current.moves.size()) {
						sb.append(" ");
					}
				}
				return sb.toString();
			}
			
			int holeIndex = getHoleIndex(currentStr.toString());
			// move right => W_
			if(holeIndex > 0 && currentStr.charAt(holeIndex - 1) == 'W') {
				StringBuilder sb = new StringBuilder(currentStr);
				sb.setCharAt(holeIndex, 'W');
				sb.setCharAt(holeIndex - 1, '_');
				List<Integer> moves = new ArrayList<Integer>(current.moves);
				moves.add(holeIndex);
				queue.add(new Move(sb, moves));
//				System.out.println("r " + sb);
			}
			// move left -> _B
			if(holeIndex <  len - 1 && currentStr.charAt(holeIndex + 1) == 'B') {
				StringBuilder sb = new StringBuilder(currentStr);
				sb.setCharAt(holeIndex, 'B');
				sb.setCharAt(holeIndex + 1, '_');
				List<Integer> moves = new ArrayList<Integer>(current.moves);
				moves.add(holeIndex + 2);
				queue.add(new Move(sb, moves));
//				System.out.println("l " + sb);
			}
			
			// jump right => WB_
			if(holeIndex > 1 && currentStr.charAt(holeIndex - 2) == 'W' 
					&& currentStr.charAt(holeIndex - 1) == 'B') {
				StringBuilder sb = new StringBuilder(currentStr);
				sb.setCharAt(holeIndex, 'W');
				sb.setCharAt(holeIndex - 2, '_');
				List<Integer> moves = new ArrayList<Integer>(current.moves);
				moves.add(holeIndex - 1);
				queue.add(new Move(sb, moves));
//				System.out.println("jr " + sb);
			}
			// jump left => _WB
			if(holeIndex < len - 2 && currentStr.charAt(holeIndex + 2) == 'B'
					&& currentStr.charAt(holeIndex + 1) == 'W') {
				StringBuilder sb = new StringBuilder(currentStr);
				sb.setCharAt(holeIndex, 'B');
				sb.setCharAt(holeIndex + 2, '_');
				List<Integer> moves = new ArrayList<Integer>(current.moves);
				moves.add(holeIndex + 3);
				queue.add(new Move(sb, moves));
//				System.out.println("jl " + sb);
			}
			
		}
		return null;
	}
	
	private static int getHoleIndex(String currentStr) {
		for(int i = 0; i < currentStr.length(); i++) {
			if(currentStr.charAt(i) == '_') {
				return i;
			}
		}
		return 0;
	}


	private static boolean isOk(StringBuilder current, StringBuilder last) {
		return current.toString().compareTo(last.toString()) == 0;
	}
	
	private static class Move{
		StringBuilder state;
		List<Integer> moves;
		Move(StringBuilder state, List<Integer> moves){
			this.state = state;
			this.moves = moves;
		}
	}


	private static int abs(int x) {
		if(x < 0) {
			return -x;
		}
		return x;
	}
	
	private static int log(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}
	
	private static double getDistance(Point p1, Point p2) {
		double dx = p1.a - p2.a;
		double dy = p1.b - p2.b;
		
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	private static String getBinaryStr(int n, int bit) {
		StringBuilder sb = new StringBuilder();
		String s = Integer.toBinaryString(n);
		for(int i = s.length() + 1; i <= bit; i++) {
			sb.append("0");
		}
		sb.append(s);
		return sb.toString();
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}
	
	
	
	private static boolean isPrime(long num) {
		if(num < 2) {
			return false;
		}
		for(int i = 2; i * i <= num; i++) {
			if(num % i == 0) {
				return false;
			}
		}
		return true;
	}
	

	private static class Point{
		double a;
		double b;
		public Point(double a, double b){
			this.a = a;
			this.b = b;
		}
		
		@Override
		public int hashCode() {
			double prime = 31;
			double result = 1;
			result = prime * result + this.a; 
			result = prime * result + this.b;
			return (int)result;
		}
		
		@Override
		public String toString() {
			return this.a + "," + this.b;
		}
		
		@Override
		public boolean equals(Object obj) {
			Point p = (Point)obj;
			return p.a == a && p.b == b;
		}
	}
	
	private static boolean isPalindrome(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		String str1 = sb.reverse().toString();
		return str.equals(str1);
	}

	private static long ceil(long d, long div) {
		if(d % div == 0) {
			return d / div;
		}
		return (d + div) / div;
	}

	private static long max(long a, long b) {
		if (a >= b) {
			return a;
		}
		return b;
	}
	
	private static int max(int a, int b) {
		if (a >= b) {
			return a;
		}
		return b;
	}
	
	private static int min(int a, int b) {
		if (a >= b) {
			return b;
		}
		return a;
	}
	
	private static long min(long a, long b) {
		if (a >= b) {
			return b;
		}
		return a;
	}
	
	private static long pow(int base, int pow) {
		long val = 1L;
		for (int i = 1; i <= pow; i++) {
			val *= base;
		}
		return val;
	}
	
	private static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream));
			tokenizer = null;
		}

		public String next() {
			try {
				while (tokenizer == null || !tokenizer.hasMoreTokens()) {
					tokenizer = new StringTokenizer(reader.readLine());

				}
			} catch (IOException e) {
				return null;
			}
			return tokenizer.nextToken();
		}

		public String nextLine() {
			String line = null;
			try {
				tokenizer = null;
				line = reader.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return line;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public boolean hasNext() {
			try {
				while (tokenizer == null || !tokenizer.hasMoreTokens()) {
					tokenizer = new StringTokenizer(reader.readLine());
				}
			} catch (Exception e) {
				return false;
			}
			return true;
		}
	}
}