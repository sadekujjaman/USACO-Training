/*
ID: shacse01
LANG: JAVA
TASK: starry
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class starry {

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

		InputReader in = new InputReader(new FileInputStream("starry.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("starry.out")));



/*
23
15
10001000000000010000000
01111100011111000101101
01000000010001000111111
00000000010101000101111
00000111010001000000000
00001001011111000000000
10000001000000000000000
00101000000111110010000
00001000000100010011111
00000001110101010100010
00000100110100010000000
00010001110111110000000
00100001110000000100000
00001000100001000100101
00000001110001000111000


*/

		int w = in.nextInt();
		int h = in.nextInt();
		char[][] grid = new char[h][w];
		for (int i = 0; i < h; i++) {
			grid[i] = in.next().toCharArray();
		}

		List<Block> blocks = new ArrayList<>();

		boolean[][] visited = new boolean[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (!visited[i][j] && grid[i][j] == '1') {
					Block block = bfs(j, i, w, h, grid, visited);
//					for(int k = 0; k < block.grid.length; k++) {
//						System.out.println(Arrays.toString(block.grid[k]));
//					}
//					System.out.println();

					blocks.add(block);
				}
			}
		}

		List<Block> uniqueBlock = new ArrayList<>();
		uniqueBlock.add(blocks.get(0));
		int cnt = 0;
		blocks.get(0).id = cnt;

		for (int i = 1; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			boolean match = isMatch(block, uniqueBlock, grid);
			if (!match) {
				cnt++;
				uniqueBlock.add(block);
				block.id = cnt;
			}
		}

//		System.out.println(uniqueBlock.size());
		char[][] grid1 = new char[h][w];
		for (int i = 0; i < h; i++) {
			Arrays.fill(grid1[i], '0');
		}
		for (int i = 0; i < h; i++) {
			Arrays.fill(visited[i], false);
		}
		for (Block block : blocks) {
			bfs(block.start.x, block.start.y, w, h, block.id, grid, grid1, visited);
		}
		for (int i = 0; i < h; i++) {
//			System.out.println(Arrays.toString(grid1[i]));
			for (int j = 0; j < w; j++) {
				out.print(grid1[i][j] + "");
			}
			out.println();
		}
		out.flush();
		out.close();
		System.exit(0);
	}

	private static boolean isMatch(Block block, List<Block> uniqueBlocks, char[][] grid) {
		for (Block uniqueBlock : uniqueBlocks) {
			if (uniqueBlock.one != block.one) {
				continue;
			}
			boolean match = false;

			char[][] grid0 = uniqueBlock.grid;
			char[][] grid90 = rotate90(uniqueBlock.grid);
			char[][] grid180 = rotate90(grid90);
			char[][] grid270 = rotate90(grid180);

			char[][] flip0 = flip(uniqueBlock.grid);
			char[][] flip90 = flip(grid90);
			char[][] flip180 = flip(grid180);
			char[][] flip270 = flip(grid270);

			if (isEqual(block.grid, grid0)) {
				match = true;
			}

			if (isEqual(block.grid, grid90)) {
				match = true;
			}

			if (isEqual(block.grid, grid180)) {
				match = true;
			}

			if (isEqual(block.grid, grid270)) {
				match = true;
			}
			if (isEqual(block.grid, flip0)) {
				match = true;
			}
			if (isEqual(block.grid, flip90)) {
				match = true;
			}
			if (isEqual(block.grid, flip180)) {
				match = true;
			}
			if (isEqual(block.grid, flip270)) {
				match = true;
			}
			if (match) {
				block.id = uniqueBlock.id;
				return true;
			}
		}

		return false;
	}

	private static boolean isEqual(char[][] grid1, char[][] grid2) {
		int h1 = grid1.length;
		int w1 = grid1[0].length;

		int h2 = grid2.length;
		int w2 = grid2[0].length;

		if (h1 == h2 && w1 == w2) {
			for (int i = 0; i < h1; i++) {
				for (int j = 0; j < w1; j++) {
					if (grid1[i][j] != grid2[i][j]) {
						return false;
					}
				}
			}
		} else {
			return false;
		}

		return true;
	}

	private static char[][] flip(char[][] grid) {
		int h = grid.length;
		int w = grid[0].length;
		char[][] grid1 = new char[h][w];
		for (int i = 0, j = w - 1; i < w; i++, j--) {
			for (int k = 0; k < h; k++) {
				grid1[k][j] = grid[k][i];
			}
		}
		return grid1;
	}

	private static char[][] rotate90(char[][] grid) {
		int h = grid.length;
		int w = grid[0].length;
		char[][] grid1 = new char[w][h];
		for (int i = 0, j = h - 1; i < h; i++, j--) {
			for (int k = 0; k < w; k++) {
				grid1[k][j] = grid[i][k];
			}
		}
		return grid1;
	}

	private static void bfs(int x, int y, int w, int h, int id, char[][] grid, char[][] grid1, boolean[][] visited) {
		visited[y][x] = true;
		Queue<Pair> queue = new LinkedList<>();
		queue.add(new Pair(x, y));
		while (!queue.isEmpty()) {
			Pair uPair = queue.remove();
			grid1[uPair.y][uPair.x] = (char) (id + 'a');
			for (int i = 0; i < 8; i++) {
				int vx = uPair.x + qdx[i];
				int vy = uPair.y + qdy[i];
				if (vx >= 0 && vx < w && vy >= 0 && vy < h && !visited[vy][vx] && grid[vy][vx] == '1') {
					visited[vy][vx] = true;
					queue.add(new Pair(vx, vy));
				}
			}
		}
	}

	private static Block bfs(int x, int y, int w, int h, char[][] grid, boolean[][] visited) {
		visited[y][x] = true;
		Queue<Pair> queue = new LinkedList<>();
		queue.add(new Pair(x, y));
		int minX = x;
		;
		int minY = y;
		int maxX = x;
		int maxY = y;
		int one = 0;
		while (!queue.isEmpty()) {
			Pair uPair = queue.remove();
			one++;
			for (int i = 0; i < 8; i++) {
				int vx = uPair.x + qdx[i];
				int vy = uPair.y + qdy[i];
				if (vx >= 0 && vx < w && vy >= 0 && vy < h && !visited[vy][vx] && grid[vy][vx] == '1') {
					visited[vy][vx] = true;
					queue.add(new Pair(vx, vy));
					minX = min(minX, vx);
					minY = min(minY, vy);
					maxX = max(maxX, vx);
					maxY = max(maxY, vy);
				}
			}
		}
		Pair topLeft = new Pair(minX, minY);
		Pair bottomRight = new Pair(maxX, maxY);
		Block block = new Block(topLeft, bottomRight);
		block.one = one;

		int w1 = block.bottomRight.x - block.topLeft.x + 1;
		int h1 = block.bottomRight.y - block.topLeft.y + 1;

		char[][] grid1 = new char[h1][w1];
		for (int i = 0; i < grid1.length; i++) {
			Arrays.fill(grid1[i], '0');
		}
		boolean visited1[][] = new boolean[h][w];
		queue = new LinkedList<>();
		queue.add(new Pair(x, y));
		visited1[y][x] = true;
		while (!queue.isEmpty()) {
			Pair uPair = queue.remove();
			grid1[uPair.y - minY][uPair.x - minX] = '1';
			for (int i = 0; i < 8; i++) {
				int vx = uPair.x + qdx[i];
				int vy = uPair.y + qdy[i];
				if (vx >= 0 && vx < w && vy >= 0 && vy < h && !visited1[vy][vx] && grid[vy][vx] == '1') {
					visited1[vy][vx] = true;
					queue.add(new Pair(vx, vy));
				}
			}
		}
		block.grid = grid1;
		block.start = new Pair(x, y);
		return block;
	}

	private static class Block {
		Pair topLeft;
		Pair bottomRight;
		int id;
		int one = 0;
		Pair start;
		char[][] grid;

		Block(Pair topLeft, Pair bottomRight) {
			this.bottomRight = bottomRight;
			this.topLeft = topLeft;
		}

		@Override
		public String toString() {
			return topLeft + " | " + bottomRight;
		}

	}

	private static class Pair {
		int x;
		int y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
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