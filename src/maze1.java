/*
ID: shacse01
LANG: JAVA
TASK: maze1
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class maze1 {

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
		
		
		InputReader in = new InputReader(new FileInputStream("maze1.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze1.out")));

		
/*

2 1
+-+-+
    |
+ +-+

1 1
+-+
   
+-+


*/
		
		
		int w = in.nextInt();
		int h = in.nextInt();
		
		int n = (2 * h) + 1;
		int m = (2 * w) + 1;
		char[][] grid = new char[n][m];
		
		for(int i = 0; i < n; i++) {
			grid[i] = in.nextLine().toCharArray();
		}
		
		
//		for(int i = 0; i < n; i++) {
//			System.out.println(Arrays.toString(grid[i]));
//		}
		
		Point first = null;
		Point second = null;
		
		for(int i = 1; i < m; i += 2) {
			if(grid[0][i] == ' ') {
				if(first == null) {
					first = new Point(1, i);
				}
				else if(second == null) {
					second = new Point(1, i);
				}
			}
		}
		
		for(int i = 1; i < n; i += 2) {
			if(grid[i][m - 1] == ' ') {
				if(first == null) {
					first = new Point(i, m - 2);
				}
				else {
					second = new Point(i, m - 2);
				}
			}
		}
		
		for(int i = 1; i < m; i += 2) {
			if(grid[n - 1][i] == ' ') {
				if(first == null) {
					first = new Point(n - 2, i);
				}
				else if(second == null) {
					second = new Point(n - 2, i);
				}
			}
		}
		
		for(int i = 1; i < n; i += 2) {
			if(grid[i][0] == ' ') {
				if(first == null) {
					first = new Point(i, 1);
				}
				else if(second == null) {
					second = new Point(i, 1);
				}
			}
		}
		
//		System.out.println(first + ", " + second);
		int cost1[][] = new int[n][m];
		int cost2[][] = new int[n][m];
		bfs(n, m, first, grid, cost1);
		bfs(n, m, second, grid, cost2);
		
		
		int cost = 0;
		for(int i = 1; i < n - 1; i += 2) {
			for(int j = 1; j < m - 1; j+= 2) {
				int val = min(cost1[i][j], cost2[i][j]);
				cost = max(cost, val);
			}
		}
		
		out.println(cost + 1);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	
	

	private static int bfs(int n, int m, Point start, char[][] grid, int[][] cost) {
		
		Queue<Point> queue = new LinkedList<>();
		queue.add(start);
		boolean[][] visited = new boolean[n][m];
		visited[start.a][start.b] = true;
		int level = 0;
		cost[start.a][start.b] = 0;
		while(!queue.isEmpty()) {
			Point upoint = queue.remove();
			for(int i = 0; i < 4; i++) {
				int a = upoint.a + dx[i];
				int b = upoint.b + dy[i];
				int aa = a + dx[i];
				int bb = b + dy[i];
				if(a >= 1 && a <= n - 2 && b >= 1 && b <= m - 2 
						&& grid[a][b] == ' ' && !visited[a][b] && !visited[aa][bb]) {
					visited[a][b] = true;
					visited[aa][bb] = true;
					Point p = new Point(aa, bb);
					p.level = upoint.level + 1;
					level = max(p.level, level);
					cost[p.a][p.b] = p.level;
					queue.add(p);
				}
			}
		}
		
		
		return level;
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
		int level;
		public Point(int a, int b){
			this.a = a;
			this.b = b;
			level = 0;
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