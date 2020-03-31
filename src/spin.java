/*
ID: shacse01
LANG: JAVA
TASK: spin
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class spin {

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
		
		
		InputReader in = new InputReader(new FileInputStream("spin.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));

		
/*


*/
		
		int n = 5;
		
		Wheel[] wheels = new Wheel[n];
		for(int i = 0; i < n; i++) {
			int speed = in.nextInt();;
			int wedge = in.nextInt();
			Wedge[] wedges = new Wedge[wedge];
			for(int j = 0; j < wedge; j++) {
				int start = in.nextInt();
				int size = in.nextInt();
				wedges[j] = new Wedge(start, size);
			}
			wheels[i] = new Wheel(speed, wedges);
//			System.out.println(wheels[i].wedges[0]);
		}
		int ans = -1;
		for(int t = 0; t < 360; t++) {
			boolean[][] occupy = new boolean[n][361];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < wheels[i].wedges.length; j++) {
					int st = wheels[i].wedges[j].start;
					int end = (st + wheels[i].wedges[j].size) % 360;
					if(st >= end) {
						for(int k = st; k <= 360; k++) {
							occupy[i][k] = true;
						}
						for(int k = 0; k <= end; k++) {
							occupy[i][k] = true;
						}
					}
					else {
						
						for(int k = st; k <= end; k++) {
							occupy[i][k] = true;
						}
					}
				}
			}
			for(int j = 0; j < 360; j++) {
				boolean isOk = true;
				for(int i = 0; i < n; i++) {
//					System.out.println(visited[i][j]);
					if(!occupy[i][j]) {
						isOk = false;
						break;
					}
				}
				
				if(isOk == true) {
					ans = t;
					break;
				}
			}
//			System.out.println(ans);
			if(ans != -1) {
				break;
			}
			for (int i = 0; i < 5; i++){
				for (int j = 0; j < wheels[i].wedges.length; j++) {
					wheels[i].wedges[j].start += wheels[i].speed;
					wheels[i].wedges[j].start %= 360;
				}
			}
			
		}
		if(ans != -1) {
			out.println(ans);
		}
		else {
			out.println("none");
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static class Wheel{
		int speed;
		Wedge[] wedges;
		Wheel(int speed, Wedge[] wedges){
			this.speed = speed;
			this.wedges = wedges;
		}
		
	}
	
	private static class Wedge{
		int start;
		int size;
		Wedge(int start, int size){
			this.start = start;
			this.size = size;
		}
		
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