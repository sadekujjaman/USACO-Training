/*
ID: shacse01
LANG: JAVA
TASK: agrinet
*/

import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Saju
 *
 */

public class agrinet {

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
		
		
		InputReader in = new InputReader(new FileInputStream("agrinet.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("agrinet.out")));

		
/*


*/
		int n = in.nextInt();
		Prims prims = new Prims(n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				int w = in.nextInt();
				prims.addEdge(i, j, w);
			}
		}
		
		long minimumCost = prims.getMinimumCost();
		out.println(minimumCost);
		
		
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static class Prims{
		int vertices;
		int[][] adj;
		boolean[] addedToMST;
		int[] distance;
		int[] parent;
		
		public Prims(int n) {
			this.vertices = n;
			this.adj = new int[n][n];
			this.addedToMST = new boolean[n];
			this.distance = new int[n];
			this.parent = new int[n];
			Arrays.fill(distance, INT_INF);
		}
		
		void addEdge(int u, int v, int w) {
			adj[u][v] = w;
		}
		
		private int getMinmumNode() {
			long min = INF;
			int minNode = -1;
			for(int i = 0; i < vertices; i++) {
				if(!addedToMST[i] && min > distance[i]) {
					min = distance[i];
					minNode = i;
				}
			}
			return minNode;
		}
		
		long getMinimumCost() {
			long cost = mst();
			return cost;
		}

		private long mst() {
			distance[0] = 0;
			parent[0] = -1;
			
			for(int i = 0; i < vertices - 1; i++) {
				int u = getMinmumNode();
				addedToMST[u] = true;
				for (int v = 0; v < vertices; v++) {
					if (!addedToMST[v] && adj[u][v] != 0 && adj[u][v] <= distance[v]) {
						parent[v] = u;
						distance[v] = adj[u][v];
					}
				}
				
			}
			long sum = 0;
			for(int i = 1; i < vertices; i++) {
				sum += adj[i][parent[i]];
			}
			return sum;
		}
		
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