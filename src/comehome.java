/*
ID: shacse01
LANG: JAVA
TASK: comehome
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class comehome {

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
		
		
		InputReader in = new InputReader(new FileInputStream("comehome.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comehome.out")));

		
/*


*/
		
		int p = in.nextInt();
		int n = 55;
		int cost[][] = new int[n][n];
		List<Integer> adj[] = new ArrayList[n];
		for(int i = 0; i < n; i++) {
			Arrays.fill(cost[i], INT_INF);
			adj[i] = new ArrayList<Integer>();
		}
		boolean hasCow[] = new boolean[n];
		List<Integer> cows = new ArrayList<Integer>();
		for(int i = 0; i < p; i++) {
			char s1 = in.next().charAt(0);
			char s2 = in.next().charAt(0);
			int u = 0;
			int v = 0;
			if(s1 >= 'A' && s1 <= 'Z') {
				u = s1 - 'A' + 26;
				if(!hasCow[u]) {
					cows.add(u);
					hasCow[u] = true;
				}
			}
			else {
				u = s1 - 'a';
			}
			if(s2 >= 'A' && s2 <= 'Z') {
				v = s2 - 'A' + 26;
				if(!hasCow[v]) {
					cows.add(v);
					hasCow[v] = true;
				}
			}
			else {
				v = s2 - 'a';
			}
			
			int w = in.nextInt();
			cost[u][v] = min(cost[u][v], w);
			cost[v][u] = min(cost[v][u], w);
			adj[u].add(v);
			adj[v].add(u);
		}
		
		int minCow = n;
		int barn = 51;
		int minTime = INT_INF;
		for(int cow : cows) {
			if(cow != barn) {
				int time = shortestDistance(cow, barn, n, adj, cost);
				if(time < minTime) {
					minTime = time;
					minCow = cow;
				}
			}
		}
		
		char ch = (char) (minCow + 'A' - 26);
		out.println(ch + " " + minTime);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static int shortestDistance(int start, int end, int n, List<Integer>[] adj, int cost[][]) {
		boolean[] visited = new boolean[n];

		PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				return Double.compare(node1.dist, node2.dist);
			}
		});
//		Queue<Node> queue = new LinkedList<>();
		Node startNode = new Node(start, 0);
		queue.offer(startNode);
//		visited[start] = true;
		int maxNode = startNode.u;
		int maxLevel = startNode.dist;
		int distance[] = new int[n];
		for(int i = 0; i < n; i++) {
			distance[i] = INT_INF;
		}
		distance[start] = 0;
		while (!queue.isEmpty()) {
			Node unode = queue.remove();
			if(!visited[unode.u]) {
				visited[unode.u] = true;
				for(int v : adj[unode.u]) {
					if(!visited[v]) {
//						int dist = getDistance(points[unode.u], points[vv]);
						if(distance[unode.u] + cost[unode.u][v] < distance[v]) {
							distance[v] = distance[unode.u] + cost[unode.u][v];
							Node vnode = new Node(v, distance[v]);
							queue.offer(vnode);
						}
					}
				}
			}
				
		}
		
		return distance[end];
	}

	private static double getDistance(Point p1, Point p2) {
		double dx = p1.a - p2.a;
		double dy = p1.b - p2.b;
		
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	
	private static class Node{
		int u;
		int dist;
		int level = 0;
		Node(int u, int dist){
			this.u = u;
			this.dist = dist;
		}
	}
	
	private static void dfs(int u, int n, List<Integer>[] adj, boolean[] visited, List<Integer> component) {
		visited[u] = true;
		component.add(u);
		for(int v : adj[u]) {
			if(!visited[v]) {
				dfs(v, n, adj, visited, component);
			}
		}
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