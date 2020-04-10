/*
ID: shacse01
LANG: JAVA
TASK: fence
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class fence {

	private static int dx[] = {-1, 0, 1, 0 };
	private static int dy[] = {0, 1, 0, -1 };

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = Integer.MAX_VALUE;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 1007;
	private static final int MOD = 9901;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;

	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("fence.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));

		
/*


*/
	
		int m = 500;
		int n = in.nextInt();
	
		Graph graph = new Graph(m);
		for(int i = 0; i < n; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			u--;
			v--;
			graph.addEdge(u, v);
		}
		
		for(int i = 0; i < m; i++) {
			if(graph.adj[i].size() > 1) {
				Collections.sort(graph.adj[i]);
			}
		}
		
		List<Pair> pair = graph.getEulerCircuit();
		
		for(int i = 0; i < n; i++) {
			out.println(pair.get(i).u + 1);
			if(i == n - 1) {
				out.println(pair.get(i).v + 1);
			}
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}

	private static class Pair{
		int u;
		int v;
		Pair(int u, int v){
			this.u = u;
			this.v = v;
		}
		@Override
		public String toString() {
			return u + " " + v;
		}
	}
	private static class Graph{
		int vertices;
		List<Integer> adj[];
		
		public Graph(int vertices) {
			this.vertices = vertices;
			adj = new ArrayList[this.vertices];
			for(int i = 0; i < this.vertices; i++) {
				adj[i] = new ArrayList<>();
			}
		}
		
		public void addEdge(int u, int v) {
			adj[u].add(v);
			adj[v].add(u);
		}
		
		public List<Pair> getEulerCircuit(){
			List<Pair> res = new ArrayList<>();
			int u = 0;
			for(int i = 0; i < adj.length; i++) {
				if(adj[i].size() % 2 == 1) {
					u = i;
					break;
				}
			}
			findEulerCircuit(u, res);
			return res;
		}
		
		private void removeEdge(int u, int v) {
			adj[u].remove(Integer.valueOf(v));
			adj[v].remove(Integer.valueOf(u));
		}
		
		private int dfs(int u, boolean[] visited) {
			visited[u] = true;
			int count = 1;
			for(int v : adj[u]) {
				if(!visited[v]) {
					count += dfs(v, visited);
				}
			}
			return count;
		}
		
		private boolean isValidNextEdge(int u, int v) {
			if(adj[u].size() == 1) {
				return true;
			}
			
			boolean[] visited = new boolean[this.vertices];
			int count1 = dfs(u, visited);
			removeEdge(u, v);
			Arrays.fill(visited, false);
			int count2 = dfs(u, visited);
			addEdge(u, v);
			Collections.sort(adj[u]);
			Collections.sort(adj[v]);
			if(count1 > count2) {
				return false;
			}
			return true;
		}
		
		private void findEulerCircuit(int u, List<Pair> res) {
			for(int i = 0;i  < adj[u].size(); i++){
				int v=  adj[u].get(i);
				if(isValidNextEdge(u, v)) {
					res.add(new Pair(u, v));
					removeEdge(u, v);
					findEulerCircuit(v, res);
				}
			}
		}
		
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