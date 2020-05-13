/*
ID: shacse01
LANG: JAVA
TASK: fc
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class fc {

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
		
		
		InputReader in = new InputReader(new FileInputStream("fc.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fc.out")));

		
/*

4
4 8
4 12
5 9.3
7 8


*/

		int n = in.nextInt();
		Point[] points = new Point[n];
		for(int i = 0; i < n; i++) {
			double x = in.nextDouble();
			double y = in.nextDouble();
			points[i] = new Point(x, y); 
		}
		
		List<Point> convexHull = getConvexHull(points);
		
		double res = 0.0;
		for(int i = 0; i < convexHull.size() - 1; i++) {
			res += getDistance(convexHull.get(i), convexHull.get(i + 1));
		}
		res += getDistance(convexHull.get(convexHull.size() - 1), convexHull.get(0));
		
		out.printf("%.2f\n", res);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	
	/*
	 * https://github.com/sadekujjaman/Geometry/blob/master/Geometry/src/convexhull/GrahamMe.java
	 */
	
	private static List<Point> getConvexHull(Point[] points) {
		
		Point minPoint = points[0];
		int minIndex = 0;
		for(int i  = 1; i < points.length; i++) {
			if((points[i].y < minPoint.y) || 
					(points[i].y == minPoint.y &&
					points[i].x < minPoint.x)) {
				minPoint = points[i];
				minIndex = i;
			}
		}
		
		Point temp = points[0];
		points[0] = points[minIndex];
		points[minIndex] = temp;
		
		Point p0 = points[0];
		Arrays.sort(points, 1, points.length, new Comparator<Point>() {

			@Override
			public int compare(Point p1, Point p2) {
				int orientation = orientation(p0, p1, p2);
				if(orientation == 0) {
					return getDistance(p0, p2) >= getDistance(p0, p1) ? -1 : 1;
				}
				return orientation;
			}
		});
		
		Stack<Point> stack = new Stack<>();
		if(points.length < 3) {
			for(int i = 0; i < points.length; i++) {
				stack.push(points[i]);
			}
		}
		else {
			stack.push(points[0]);
			stack.push(points[1]);
			stack.push(points[2]);
			for(int i = 3; i < points.length; i++) {
				int orientation = orientation(getNextToTop(stack), stack.peek(), points[i]);
				while(true) {
					if(orientation == -1 || orientation == 0) {
						break;
					}
					stack.pop();
					orientation = orientation(getNextToTop(stack), stack.peek(), points[i]);
				}
				stack.push(points[i]);
			}
		}
		
		
		return new ArrayList<>(stack);
	}

	private static Point getNextToTop(Stack<Point> stack) {
		Point top = stack.peek();
		stack.pop();
		Point nextToTop = stack.peek();
		stack.push(top);
		return nextToTop;
	}

	private static int orientation(Point p, Point q, Point r) {
		double val = ((q.y - p.y) * (r.x - q.x)) - ((q.x - p.x) * (r.y - q.y));
		if(val > 0.0) {
			return 1; // right | clock wise
		}
		else if(val < 0.0) {
			return -1; // left | counter close wise
		}
		return 0;
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