/*
ID: shacse01
LANG: JAVA
TASK: butter
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class butter {

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
		
		
		InputReader in = new InputReader(new FileInputStream("butter.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));

		
/*



*/
	
		int n = in.nextInt();
		int p = in.nextInt();
		int c = in.nextInt();
		int cowPastures[] = new int[n];
		for(int i = 0; i < n; i++) {
			int a = in.nextInt();
			a--;
			cowPastures[i] = a;
		}
		
		List<Edge> adj[] = new ArrayList[p];
		for(int i = 0; i < p; i++) {
			adj[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < c; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			u--;
			v--;
			adj[u].add(new Edge(u, v, w));
			adj[v].add(new Edge(v, u, w));
		}
		
		long[] totalCost = new long[p];
		
		for(int i = 0; i < n; i++) {
			dijstra(cowPastures[i], p, cowPastures, adj, totalCost);
		}
		
		long min = INF;
		for(int i = 0; i < p; i++) {
			min = min(min, totalCost[i]);
		}
		
		out.println(min);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static void dijstra(int src, int n, int[] cowPastures, List<Edge>[] adj, long[] totalCost) {
		boolean[] visited = new boolean[n];
		long[] distance = new long[n];
		Arrays.fill(distance, INF);
		
		PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {
				return Long.compare(n1.dest, n2.dest);
			}
		});
		
		
		distance[src] = 0;
		queue.add(new Node(src, distance[src]));
		
		while(!queue.isEmpty()) {
			Node unode = queue.remove();
			if(!visited[unode.u]) {
				visited[unode.u] = true;
				for(Edge e : adj[unode.u]) {
					int v = e.v;
					if(!visited[e.v]) {
						long cost = distance[unode.u] + e.w;
						if(cost < distance[v]) {
							distance[v] = cost;
							queue.add(new Node(v, distance[v]));
						}
					}
				}
			}
		}
		
		
		for(int i = 0; i < n; i++) {
			totalCost[i] += distance[i];
		}
		
//		System.out.println(Arrays.toString(distance));
//		System.out.println(Arrays.toString(totalCost));
		
	}
	
	private static class Node{
		int u;
		long dest;
		Node(int u, long dest){
			this.u = u;
			this.dest = dest;
		}
	}

	private static class Edge{
		int u;
		int v;
		int w;
		Edge(int u, int v, int w){
			this.u = u;
			this.v = v;
			this.w = w;
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