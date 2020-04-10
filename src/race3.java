/*
ID: shacse01
LANG: JAVA
TASK: race3
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class race3 {

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
		
		
		InputReader in = new InputReader(new FileInputStream("race3.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("race3.out")));

		
/*

1 2 -2
3 -2
3 -2
5 4 -2
6 4 -2
6 -2
7 8 -2
9 -2
5 9 -2
-2
-1

*/

		List<Integer> adj[] = new ArrayList[MAX];
		List<Integer> reverseAdj[] = new ArrayList[MAX];
		for(int i = 0; i < MAX; i++) {
			adj[i] = new ArrayList<>();
			reverseAdj[i] = new ArrayList<>();
		}
		
		int n = 0;
		while(true) {
			int a = in.nextInt();
			if(a == -1) {
				break;
			}
			
			while(a != -2) {
				if(a != n) {
					adj[n].add(a);
					reverseAdj[a].add(n);
				}
				
				a = in.nextInt();
			}
			n++;
			
		}
		
		int level[] = new int[n];
		bfs2(0, adj, level, n); // find level
		
		Set<Integer> unavoidablePoints = new TreeSet<>();
		Set<Integer> splitingPoints = new TreeSet<Integer>();
		
		
		for(int i = 1; i < n - 1; i++) {
			List<Integer> outGoing = new ArrayList<>(adj[i]);
			adj[i] = new ArrayList<Integer>();
			for(int e : reverseAdj[i]) {
				adj[e].remove(Integer.valueOf(i));
			}
			
			boolean isPossible = isPossibleReachToFinish(0, n - 1, n, adj);
			adj[i] = new ArrayList<>(outGoing);
			if(!isPossible) {
				unavoidablePoints.add(i);
//				System.out.println(i + " unavoid " + isPossible);
				boolean[] visited = new boolean[n];
				// run a bfs for check is parent can visited or not.
				bfs1(i, adj, visited, n);
				boolean possible = false;
				for(int e : reverseAdj[i]) {
					if(visited[e] && level[e] < level[i]) {
						possible = true;
						break;
					}
				}
				if(!possible) {
					splitingPoints.add(i);
				}
			}
			
			for(int e : reverseAdj[i]) {
				adj[e].add(i);
			}
		}
		
		if(unavoidablePoints.size() == 0) {
			out.println(0);
		}
		else {
			int sz = unavoidablePoints.size();
			out.print(sz + " ");
			int s = 0;
			for(Integer e : unavoidablePoints) {
				out.print(e);
				if(s == sz - 1) {
					out.print("\n");
				}
				else {
					out.print(" ");
				}
				s++;
			}
		}
		
		if(splitingPoints.size() == 0) {
			out.println(0);
		}
		else {
			int sz = splitingPoints.size();
			out.print(sz + " ");
			int s = 0;
			for(Integer e : splitingPoints) {
				out.print(e);
				if(s == sz - 1) {
					out.print("\n");
				}
				else {
					out.print(" ");
				}
				s++;
			}
		}
		
		
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static void bfs1(int start, List<Integer>[] adj, boolean[] visited, int n) {
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(start);
		visited[start] = true;
		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (int v : adj[u]) {
				if (!visited[v]) {
					visited[v] = true;
					queue.add(v);
				}
			}
		}
	}
	
	private static void bfs2(int start, List<Integer>[] adj, int[] level, int n) {
		boolean[] visited = new boolean[n];
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(start);
		visited[start] = true;
		level[start] = 0;
		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (int v : adj[u]) {
				if (!visited[v]) {
					visited[v] = true;
					queue.add(v);
					level[v] = level[u] + 1;
				}
			}
		}
	}

	private static boolean isPossibleReachToFinish(int start, int dest, int n, List<Integer>[] adj) {
		
		boolean[] visited = new boolean[n];
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(start);
		visited[start] = true;
		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (int v : adj[u]) {
				if (!visited[v]) {
					visited[v] = true;
					queue.add(v);
				}
			}
		}
		return visited[dest];
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