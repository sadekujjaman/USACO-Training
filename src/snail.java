/*
ID: shacse01
LANG: JAVA
TASK: snail
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class snail {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static int ddx[] = {1, 0, -1, 0};
	private static int ddy[] = {0, -1, 0, 1};
	
	
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
		
		
		InputReader in = new InputReader(new FileInputStream("snail.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snail.out")));

		
/*

8 4
E2
A6
G1
F5


*/
		
		int n = in.nextInt();
		int m = in.nextInt();
		char[][] grid = new char[n][n];
		
		for(int i = 0; i < n; i++) {
			Arrays.fill(grid[i], '.');
		}
		
		for(int i = 0; i < m; i++) {
			String str = in.next();
			char ch = str.charAt(0);
			int a = Integer.parseInt(str.substring(1));
			int b = ch - 'A';
			a--;
			grid[a][b] = '#';
		}
		
//		for(int i = 0; i < n; i++) {
//			System.out.println(Arrays.toString(grid[i]));
//		}
		
		boolean[][] visited = new boolean[n][n];
		
		dfs(0, 0, 3, n, grid, visited, 1);
		dfs(0, 0, 0, n, grid, visited, 1);
        
		out.println(ans);
		
		out.flush();
		out.close();
		System.exit(0);
	}

	static int ans = 0;

	private static void dfs(int x, int y, int direction, int n, char[][] grid, boolean[][] visited, int step) {
		ans = max(ans, step);
		visited[x][y] = true;
		int x1 = x + ddx[direction];
		int y1 = y + ddy[direction];
		if (x1 >= 0 && y1 >= 0 && x1 < n && y1 < n && grid[x1][y1] == '.') {
			if (!visited[x1][y1]) {
				dfs(x1, y1, direction, n, grid, visited, step + 1);
			}
		} else {
			if (direction == 0 || direction == 2) {
				x1 = x + ddx[1];
				y1 = y + ddy[1];
				if (x1 >= 0 && y1 >= 0 && x1 < n && y1 < n && grid[x1][y1] == '.' && !visited[x1][y1]) {
					dfs(x1, y1, 1, n, grid, visited, step + 1);
				}
				x1 = x + ddx[3];
				y1 = y + ddy[3];
				if (x1 >= 0 && y1 >= 0 && x1 < n && y1 < n && grid[x1][y1] == '.' && !visited[x1][y1]) {
					dfs(x1, y1, 3, n, grid, visited, step + 1);
				}
			} else {
				x1 = x + ddx[0];
				y1 = y + ddy[0];
				if (x1 >= 0 && y1 >= 0 && x1 < n && y1 < n && grid[x1][y1] == '.' && !visited[x1][y1]) {
					dfs(x1, y1, 0, n, grid, visited, step + 1);
				}
				x1 = x + ddx[2];
				y1 = y + ddy[2];
				if (x1 >= 0 && y1 >= 0 && x1 < n && y1 < n && grid[x1][y1] == '.' && !visited[x1][y1]) {
					dfs(x1, y1, 2, n, grid, visited, step + 1);
				}
			}

		}
		visited[x][y] = false;
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
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;
		
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
		double x;
		double y;
		public Point(double x, double y){
			this.x = x;
			this.y = y;
		}
		
		public Point() {
			
		}

		@Override
		public int hashCode() {
			double prime = 31;
			double result = 1;
			result = prime * result + this.x; 
			result = prime * result + this.y;
			return (int)result;
		}
		
		@Override
		public String toString() {
			return this.x + "," + this.y;
		}
		
		@Override
		public boolean equals(Object obj) {
			Point p = (Point)obj;
			return p.x == x && p.y == y;
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