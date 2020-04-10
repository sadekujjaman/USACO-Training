/*
ID: shacse01
LANG: JAVA
TASK: ditch
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class ditch {

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
		
		
		InputReader in = new InputReader(new FileInputStream("ditch.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ditch.out")));

		
/*


5 4
1 2 40
1 4 20
2 4 20
2 3 30
3 4 10


*/

		int m = in.nextInt();
		int n = in.nextInt();

		List<Edge>[] adjList = createGraph(n);
		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			long c = in.nextLong();
			u--;
			v--;
			addEdge(adjList, u, v, c);
		}
		
		
		long ans = maxFlow(adjList, 0, n - 1);
//		System.out.println(ans);
		out.println(ans);
		
		out.flush();
		out.close();
		System.exit(0);
	}

	private static long maxFlow(List<Edge>[] adjList, int src, int sink) {
		long flow = 0;
		int[] dist = new int[adjList.length];
		while (dinicBfs(adjList, src, sink, dist)) {
			int ptr[] = new int[adjList.length];
			while (true) {
				long bottleNeck = dinicDfs(src, sink, adjList, ptr, dist, INF);
				if (bottleNeck == 0) {
					break;
				}
				flow += bottleNeck;
			}
		}

		return flow;
	}

	private static long dinicDfs(int u, int sink, List<Edge>[] adjList, int[] ptr, int[] dist, long flow) {
		if (u == sink) {
			return flow;
		}

		for (; ptr[u] < adjList[u].size(); ptr[u]++) {
			Edge e = adjList[u].get(ptr[u]);
			if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
				long bottleNeck = dinicDfs(e.t, sink, adjList, ptr, dist, min(flow, e.cap - e.f));
				if (bottleNeck > 0) {
					e.f += bottleNeck;
					adjList[e.t].get(e.rev).f -= bottleNeck;
					return bottleNeck;
				}
			}
		}

		return 0;
	}

	private static boolean dinicBfs(List<Edge>[] adjList, int src, int sink, int[] dist) {
		Arrays.fill(dist, -1);
		dist[src] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(src);
		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (Edge e : adjList[u]) {
				if (dist[e.t] < 0 && e.f < e.cap) {
					dist[e.t] = dist[u] + 1;
					queue.add(e.t);
				}
			}
		}
		return dist[sink] >= 0;
	}

	private static void addEdge(List<Edge> adjList[], int u, int v, long cap) {
		adjList[u].add(new Edge(v, adjList[v].size(), cap));
		adjList[v].add(new Edge(u, adjList[u].size() - 1, 0L));
	}

	private static List<Edge>[] createGraph(int nodes) {
		List<Edge> adjList[] = new ArrayList[nodes];
		for (int i = 0; i < nodes; i++) {
			adjList[i] = new ArrayList<>();
		}
		return adjList;
	}

	private static class Edge {
		int t;
		int rev;
		long cap;
		long f;

		Edge(int t, int rev, long cap) {
			this.t = t;
			this.rev = rev;
			this.cap = cap;
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