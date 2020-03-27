/*
ID: shacse01
LANG: JAVA
TASK: concom
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class concom {

	private static int dx[] = { 1, 0, -1, 0 };
	private static int dy[] = { 0, -1, 0, 1 };

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
		
		
		InputReader in = new InputReader(new FileInputStream("concom.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));

		
/*

6
1 2 30
2 3 52
3 4 51
4 5 70
5 4 20
4 3 20



*/
		
		int t = in.nextInt();
		
		int n = 100;
		
		int dp[][] = new int[n + 7][n + 7];
		int adj[][] = new int[n + 7][n + 7];
		for(int i = 0; i < t; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			if(u != v) {
				adj[u][v] = w;
				
			}
			
		}
		
		List<Pair> pairs = new ArrayList<>();

		boolean found = true;
		while(found) {
			found = false;
			for(int i = 1; i <= n; i++) {
				for(int j = 1; j <= n; j++) {
					if(dp[i][j] == 0 && adj[i][j] > 50) {
						dp[i][j] = 1;
						found = true;
						for(int k = 1; k <= n; k++) {
							if(i != k && j != k) {
								adj[i][k] += adj[j][k];
								if(adj[i][k] > 50) {
									adj[i][k] = 51;
									dp[i][j] = 1;
								}
							}
						}
					}
				}
			}
		}
		
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				if(dp[i][j] == 1) {
					pairs.add(new Pair(i, j));
				}
			}
		}
		
		
		for(Pair p : pairs) {
			out.println(p);
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
			return this.u + " " + this.v;
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
		int a;
		int b;
		boolean visited = false;
		public Point(int a, int b){
			this.a = a;
			this.b = b;
		}
		
		@Override
		public int hashCode() {
			return a + b;
		}
		
		@Override
		public String toString() {
			return a + " " + b;
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