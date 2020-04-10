/*
ID: shacse01
LANG: JAVA
TASK: frameup
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class frameup {

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
		
		InputReader in = new InputReader(new FileInputStream("src/test.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
//		InputReader in = new InputReader(new FileInputStream("frameup.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("frameup.out")));

		
/*



*/

		int n = in.nextInt();
		int m = in.nextInt();
		char[][] grid = new char[n][m];
		
		int minX[] = new int[30];
		int minY[] = new int[30];
		int maxX[] = new int[30];
		int maxY[] = new int[30];
		
		Arrays.fill(minX, INT_INF);
		Arrays.fill(minY, INT_INF);
		Arrays.fill(maxX, -1);
		Arrays.fill(maxY, -1);
		
		List<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			grid[i] = in.next().toCharArray();
			for(int j = 0; j < m; j++) {
				if(grid[i][j] != '.') {
					int ch = grid[i][j] - 'A';
					minX[ch] = min(minX[ch], j);
					maxX[ch] = max(maxX[ch], j);
					minY[ch] = min(minY[ch], i);
					maxY[ch] = max(maxY[ch], i);
					if(!list.contains(ch)) {
						list.add(ch);
					}
				}
			}
		}
		
//		System.out.println("minX " + Arrays.toString(minX));
//		System.out.println("minY " + Arrays.toString(minY));
//		System.out.println("maxX " + Arrays.toString(maxX));
//		System.out.println("maxY " + Arrays.toString(maxY));
		
		Set<Integer> adj[] = new HashSet[30];
		for(int i = 0; i < adj.length; i++) {
			adj[i] = new HashSet<>();
		}
		for (int i : list) {
			for (int x = minX[i]; x <= maxX[i]; x++) {
				int v = grid[minY[i]][x] - 'A';
				if (i != v) {
					adj[i].add(v);
				}
				v = grid[maxY[i]][x] - 'A';
				if (i != v) {
					adj[i].add(v);
				}
			}
			for (int y = minY[i]; y <= maxY[i]; y++) {
				int v = grid[y][minX[i]] - 'A';
				if (i != v) {
					adj[i].add(v);
				}
				v = grid[y][maxX[i]] - 'A';
				if (i != v) {
					adj[i].add(v);
				}
			}
		}
		
//		for(int i : list) {
//			System.out.print(i + " =>> ");
//			for(int e : adj[i]) {
//				System.out.print(e + " ");
//			}
//			System.out.println();
//		}

		int indegree[] = new int[30];
		for(int i : list) {
			for (int e : adj[i]) {
				indegree[e]++;
			}
		}

		List<Integer> stack = new ArrayList<>();
		boolean[] visited = new boolean[30];
		
		allTopologicalSorts(visited, indegree, stack, adj, list);

		Collections.sort(ansList);
		for (String str : ansList) {
			out.println(str);
		}
		
		 
		out.flush();
		out.close();
		System.exit(0);
	}
	
	static List<String> ansList = new ArrayList<>();
	
	/*
	 * https://www.geeksforgeeks.org/all-topological-sorts-of-a-directed-acyclic-graph/
	 * 
	 */
	
	private static void allTopologicalSorts(boolean[] visited, int[] indegree, List<Integer> stack,
			Set<Integer>[] adj, List<Integer> list) {
		// To indicate whether all topological are found
		// or not
		boolean flag = false;

		for (int i : list) {
			// If indegree is 0 and not yet visited then
			// only choose that vertex
			if (!visited[i] && indegree[i] == 0) {

				// including in result
				visited[i] = true;
				stack.add(i);
				for (int adjacent : adj[i]) {
					indegree[adjacent]--;
				}
				allTopologicalSorts(visited, indegree, stack, adj, list);

				// resetting visited, res and indegree for
				// backtracking
				visited[i] = false;
				stack.remove(stack.size() - 1);
				for (int adjacent : adj[i]) {
					indegree[adjacent]++;
				}

				flag = true;
			}
		}
		if (!flag) {
			StringBuilder sb = new StringBuilder();
			stack.forEach(e -> sb.append((char) (e + 'A')));
			ansList.add(sb.toString());
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