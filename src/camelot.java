/*
ID: shacse01
LANG: JAVA
TASK: camelot
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class camelot {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000000;
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
		
		
		InputReader in = new InputReader(new FileInputStream("camelot.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("camelot.out")));

		
/*



*/
		
		int n = in.nextInt();
		int m = in.nextInt();
		
		
		int s = 0;
		List<Pair> knights = new ArrayList<>();
		Pair queen = null;
		while(in.hasNext()) {
			int x = in.next().charAt(0) - 'A';
			int y = in.nextInt();
			y--;
			if(s == 0) {
				queen = new Pair(x, y);
				s = 1;
			}
			else {
				knights.add(new Pair(x, y, n, m));
			}
		}
		
	
		if(knights.size() == 0) {
			out.println(0);
		}
		else {
			
			int[][] knightsDist = new int[n][m];
			
			
			for (Pair knight : knights) {
				int temp[][] = new int[n][m];
				for (int i = 0; i < n; i++) {
					Arrays.fill(temp[i], INT_INF);
				}
				bfs(n, m, knight, temp);
				knight.dist = temp;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						if (temp[i][j] != INT_INF && knightsDist[i][j] != INT_INF) {
							knightsDist[i][j] += temp[i][j];
						} else {
							knightsDist[i][j] = INT_INF;
						}

					}
				}
			}
			
//			for(int i = 0; i < n; i++) {
//				System.out.println(Arrays.toString(knightsDist[i]));
//			}
			
			int temp1[][][][] = new int[n][m][n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					for (int k = 0; k < n; k++) {
						Arrays.fill(temp1[i][j][k], INT_INF);
					}
				}
			}
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					bfs(n, m, new Pair(j, i), temp1[i][j]);
				}
			}
			int queenDist[][] = new int[n][m];
			bfs2(n, m, queen, queenDist);

//			for(int i = 0; i < n; i++) {
//				System.out.println(Arrays.toString(queenDist[i]));
//			}

			
			int ans = INT_INF;
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					int knightDistance = knightsDist[i][j];
					int queenDistance = queenDist[i][j];
					 for(int k = max(0,queen.y - 2); k <= min(queen.y + 2, n - 1); k++) {
		                    for(int l = max(0, queen.x - 2); l <= min(queen.x + 2, m - 1); l++) {
		                        for(Pair knight : knights) {
		                        	int temp = queenDist[k][l];
		                        	temp -= knight.dist[i][j];
		                        	temp += knight.dist[k][l];
		                        	temp += temp1[k][l][i][j];
		                        	queenDistance = min(queenDistance, temp);
		                        }
		                    }
		                }
					
					ans = min(ans, knightDistance + queenDistance);
//					System.out.println( i + " " + " " + j + " " + ans);
				}
			}
			out.println(ans);
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static void bfs2(int n, int m, Pair src, int[][] dist) {
		boolean[][] visited = new boolean[n][m];
		Queue<Pair> queue = new LinkedList<>();
		queue.add(src);
		visited[src.y][src.x] = true;
		int d[][] = new int[n][m];
		d[src.y][src.x] = 0;
//		grid[src.y][src.x]++;
		while(!queue.isEmpty()) {
			Pair uPair = queue.remove();
			
			for(int i = 0; i < 8; i++) {
				int vx = uPair.x + qdx[i];
				int vy = uPair.y + qdy[i];
				if(vx >= 0 && vx < m && vy >= 0 && vy < n && !visited[vy][vx]) {
					visited[vy][vx] = true;
					d[vy][vx] = d[uPair.y][uPair.x] + 1;
					queue.add(new Pair(vx, vy));
				}
			}
		}
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				dist[i][j] += d[i][j];
			}
		}
		
	}

	private static void bfs(int n, int m, Pair src, int[][] dist) {
		boolean[][] visited = new boolean[n][m];
		Queue<Pair> queue = new LinkedList<>();
		queue.add(src);
		visited[src.y][src.x] = true;
		dist[src.y][src.x] = 0;
		while(!queue.isEmpty()) {
			Pair uPair = queue.remove();
			
			for(int i = 0; i < 8; i++) {
				int vx = uPair.x + dx[i];
				int vy = uPair.y + dy[i];
				if(vx >= 0 && vx < m && vy >= 0 && vy < n && !visited[vy][vx]) {
					visited[vy][vx] = true;
					queue.add(new Pair(vx, vy));
					dist[vy][vx] = dist[uPair.y][uPair.x] + 1;
				}
			}
		}
	}
	
	private static class Pair{
		int x;
		int y;
		int dist[][];
		Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		Pair(int x, int y, int n, int m){
			this(x, y);
			dist = new int[n][m];
		}
		
		@Override
		public String toString() {
			return this.x + " " + this.y;
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