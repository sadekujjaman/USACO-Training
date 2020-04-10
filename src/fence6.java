/*
ID: shacse01
LANG: JAVA
TASK: fence6
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class fence6 {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000000;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 107;
	private static final int MOD = 9901;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;
	
	
	
	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("fence6.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence6.out")));

		
/*

10
1 16 2 2
2 7
10 6
2 3 2 2
1 7
8 3
3 3 2 1
8 2
4
4 8 1 3
3
9 10 5
5 8 3 1
9 10 4
6
6 6 1 2 
5 
1 10
7 5 2 2 
1 2
8 9
8 4 2 2
2 3
7 9
9 5 2 3
7 8
4 5 10
10 10 2 3
1 6
4 9 5



*/

		
		int fench = in.nextInt();
		int[][] adj = new int[MAX][MAX];
		Edge[] edges = new Edge[MAX];
		Map<Set<Integer>, Integer> map = new HashMap<>();
		
		int verticesNum = 0;
		for(int i = 0; i < fench; i++) {
			int edgeNo = in.nextInt();
			int len = in.nextInt();
			int side1 = in.nextInt();
			int side2 = in.nextInt();
			Set<Integer> set1 = new TreeSet<>();
			
			set1.add(edgeNo);
			for(int j = 0; j < side1; j++) {
				int a = in.nextInt();
				set1.add(a);
			}
			int u = 0;
			int v = 0;
			if(map.containsKey(set1)) {
				u = map.get(set1);
			}
			else {
				u = verticesNum;
				map.put(set1, u);
				verticesNum++;
			}
			Set<Integer> set2 = new TreeSet<>();
			set2.add(edgeNo);
			
			for(int j = 0; j < side2; j++) {
				int a = in.nextInt();
				set2.add(a);
			}
			if(map.containsKey(set2)) {
				v = map.get(set2);
			}
			else {
				v = verticesNum;
				map.put(set2, v);
				verticesNum++;
			}
			adj[u][v] = len;
			adj[v][u] = len;
			edges[i] = new Edge(u, v, len);
		}
//		System.out.println(verticesNum);
		int min = INT_INF;
		
//		for(int i = 0; i < verticesNum; i++) {
//			for(int j = 0; j < verticesNum; j++) {
//				System.out.print(adj[i][j] + " ");
//			}
//			System.out.println();
//		}
		
	
		for(int i = 0; i < fench; i++) {
			int u = edges[i].u;
			int v = edges[i].v;
			int len = edges[i].len;
//			System.out.println(i + " " + u + " " + v + " " + len);
			adj[u][v] = 0;
			adj[v][u] = 0;
			int minPath = djstra(u, v, verticesNum, adj);
//			System.out.println(i + " " + u + " " + v + " "  + minPath);
			min = min(min, minPath + len);
			adj[u][v] = len;
			adj[v][u] = len;
		}
		
		out.println(min);
	
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static int djstra(int src, int dest, int verticesNum, int[][] adj) {
		int[] distance = new int[verticesNum];
		Arrays.fill(distance, INT_INF);
		boolean[] visited = new boolean[verticesNum];
		
		PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				
				return o1.dist - o2.dist;
			}
		});
		distance[src] = 0;
		queue.add(new Node(src, distance[src]));
		
		while(!queue.isEmpty()) {
			Node unode=  queue.remove();
			if(unode.u == dest) {
				return distance[unode.u];
			}
			if(!visited[unode.u]) {
				visited[unode.u] = true;
				for(int i = 0; i < verticesNum; i++) {
					if(adj[unode.u][i] > 0 && distance[i] > distance[unode.u] + adj[unode.u][i]) {
						distance[i] = distance[unode.u] + adj[unode.u][i];
						queue.add(new Node(i, distance[i]));
					}
				}
			}
		}
		return distance[dest];
	}
	
	private static class Node{
		int u;
		int dist;
		Node(int u, int dist) {
			this.u = u;
			this.dist = dist;
		}
	}

	private static class Edge{
		int u;
		int v;
		int len;
		Edge(int u, int v, int len){
			this.u = u;
			this.v = v;
			this.len = len;
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