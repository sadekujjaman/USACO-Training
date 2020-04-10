/*
ID: shacse01
LANG: JAVA
TASK: milk6
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class milk6 {

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
		
		
		InputReader in = new InputReader(new FileInputStream("milk6.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk6.out")));

		
/*



*/

		int n = in.nextInt();
		int m = in.nextInt();
		
		List<Edge>adjList[] = new ArrayList[n];
		for(int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<>();
		}
		Edge[] edges = new Edge[m];
		for(int i = 0; i < m; i++) {
			int s = in.nextInt();
			int t = in.nextInt();
			long cap = in.nextLong();
			s--;
			t--;
			addEdge(adjList, s, t, cap);
			edges[i] = new Edge(s, t, cap, i);
		}
		AdjDeepCopy a = new  AdjDeepCopy(adjList);
		long maxFlow = maxFlow(a.adj, 0, n - 1);
		
		long flow = maxFlow;
		Arrays.sort(edges, new Comparator<Edge>() {

			@Override
			public int compare(Edge e1, Edge e2) {
				int val = Long.compare(e2.cap, e1.cap);
				if(val == 0) {
					return Integer.compare(e1.index, e2.index);
				}
				return val;
			}
		});
		List<Integer> edgeIndex = new ArrayList<>();
		for(int i = 0; i < m; i++) {
			Edge e = edges[i];
			int s = e.s;
			int t = e.t;
			long cap = e.cap;
			AdjDeepCopy tempA = new  AdjDeepCopy(adjList);
			for(Edge e1 : tempA.adj[s]) {
				if(e1.t == t) {
					e1.cap -= cap;
					break;
				}
			}
			
			long f = maxFlow(tempA.adj, 0, n - 1);
			
			if(flow - f == cap) {
				edgeIndex.add(e.index + 1);
				flow = f;
			}
			else {
				for(Edge e1 : tempA.adj[s]) {
					if(e1.t == t) {
						e1.cap += cap;
					}
				}
			}
			adjList = tempA.adj;
			if(flow == 0) {
				break;
			}
		}
		
		Collections.sort(edgeIndex);
		
		out.println(maxFlow + " " + edgeIndex.size());
		for(Integer e : edgeIndex) {
			out.println(e);
		}
		
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static class AdjDeepCopy{
		List<Edge> adj[];
		 AdjDeepCopy(List<Edge> adj[]){
			this.adj = new ArrayList[adj.length];
			for(int i = 0; i < this.adj.length; i++) {
				this.adj[i] = new ArrayList<>();
			}
			for(int i = 0; i < this.adj.length; i++) {
				for(Edge e : adj[i]) {
					this.adj[i].add(new Edge(e.t, e.rev, e.cap));
				}
			}
		}
	}
	
	private static long maxFlow(List<Edge> adjList[], int src, int sink) {
		long maxFlow = 0;
		int dist[] = new int[adjList.length];
		while(dinicBFS(src, sink, adjList, dist)) {
//			System.out.println("BFS");
			while(true) {
				int ptr[] = new int[adjList.length];
				long bottleNeck = dinicDFS(src, sink, adjList, dist, ptr, INF);
//				System.out.println(bottleNeck);
				if(bottleNeck == 0) {
					break;
				}
				maxFlow += bottleNeck;
			}
		}
		return maxFlow;
	}
	
	private static long dinicDFS(int u, int sink, List<Edge>[] adjList, int[] dist, int[] ptr, long flow) {
		if(u == sink) {
			return flow;
		}
		for(; ptr[u] < adjList[u].size(); ptr[u]++) {
			Edge e = adjList[u].get(ptr[u]);
			if(dist[e.t] == dist[u] + 1 && e.f < e.cap) {
				long bottleNeck = dinicDFS(e.t, sink, adjList, dist, ptr, min(flow, e.cap - e.f));
				if(bottleNeck > 0) {
					e.f += bottleNeck;
					adjList[e.t].get(e.rev).f -= bottleNeck;
					return bottleNeck;
				}
			}
		}
		
		return 0;
	}

	private static boolean dinicBFS(int src, int sink, List<Edge>[] adjList, int[] dist) {
		Arrays.fill(dist, -1);
		dist[src] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(src);
		while(!queue.isEmpty()) {
			int u = queue.remove();
			for(Edge e : adjList[u]) {
				if(dist[e.t] == -1 && e.f < e.cap) {
					dist[e.t] = dist[u] + 1;
					queue.add(e.t);
				}
			}
		}
		return dist[sink] >= 0;
	}
	
	private static void addEdge(List<Edge> adjList[], int s, int t, long cap) {
		Edge e1 = new Edge(t, adjList[t].size(), cap);
		if(adjList[s].contains(e1)) {
			for(Edge e : adjList[s]) {
				if(e.t == t) {
					e.cap += cap;
					return;
				}
			}
		}
		else {
			adjList[s].add(new Edge(t, adjList[t].size(), cap));
			adjList[t].add(new Edge(s, adjList[s].size() - 1, 0));
		}
	}

	private static class Edge{
		int s;
		int t;
		int f;
		int rev;
		long cap;
		int index;
		
		Edge(int t, int rev, long cap){
			this.t = t;
			this.rev = rev;
			this.cap = cap;
		}
		
		Edge(int s, int t, long cap, int index){
			this.t = t;
			this.s = s;
			this.cap = cap;
			this.index = index;
		}
		@Override
		public int hashCode() {
			return t;
		}
		
		@Override
		public boolean equals(Object obj) {
			Edge e = (Edge)obj;
			return this.t == e.t;
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