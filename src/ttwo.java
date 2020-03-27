/*
ID: shacse01
LANG: JAVA
TASK: ttwo
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class ttwo {

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
		
		
		InputReader in = new InputReader(new FileInputStream("ttwo.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ttwo.out")));

		
/*



*/
		
		int n = 10;
		char[][] grid = new char[n][n];
		Point farmer = null;
		Point cow = null;
		for(int i = 0; i < n; i++) {
			grid[i] = in.next().toCharArray();
			for(int j = 0; j < n; j++) {
				if(grid[i][j] == 'C') {
					cow = new Point(i, j);
				}
				if(grid[i][j] == 'F') {
					farmer = new Point(i, j);
				}
			}
		}
		
		int ans = bfs(n, grid, farmer, cow);
		out.println(ans);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	
	private static int bfs(int n, char[][] grid, Point farmer, Point cow) {
		
		boolean visited[][][][][][] = new boolean[n][n][n][n][n][n];
		int res = 0;
		int farmerDirection = 0;
		int cowDirection = 0;
		while(true) {
			
			if(farmer.equals(cow) || visited[farmer.a][farmer.b][farmerDirection][cow.a][cow.b][cowDirection]) {
				break;
			}
			visited[farmer.a][farmer.b][farmerDirection][cow.a][cow.b][cowDirection] = true;
			
			Point tempF = new Point(farmer.a + dx[farmerDirection], farmer.b + dy[farmerDirection]);
			Point tempC = new Point(cow.a + dx[cowDirection], cow.b + dy[cowDirection]);

			if (isSafe(tempF, n, grid)) {
				farmer = tempF;
			}
			else {
				farmerDirection = (farmerDirection + 1) % 4;
			}
			
			if (isSafe(tempC, n, grid)) {
				cow = tempC;
			}
			else {
				cowDirection = (cowDirection + 1) % 4;
			}
			res++;
		}
		
//		System.out.println(farmer + ", " + cow);
		if(farmer.equals(cow)) {
			return res;
		}
		return 0;
	}
	
	

	private static boolean isSafe(Point p, int n, char[][] grid) {
		int x = p.a;
        int y = p.b;
        if (x >= 0 && y >= 0 && x < n && y < n && grid[x][y] != '*') {
            return true;
        }
        return false;
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
			int prime = 31;
			int result = 1;
			result = prime * result + this.a; 
			result = prime * result + this.b;
			return result;
		}
		
		@Override
		public String toString() {
			return this.a + " " + this.b;
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