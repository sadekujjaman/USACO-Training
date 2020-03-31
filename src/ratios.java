/*
ID: shacse01
LANG: JAVA
TASK: ratios
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class ratios {

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
		
		
		InputReader in = new InputReader(new FileInputStream("ratios.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));

		
/*



*/
	
		int n = 3;
		int r[] = new int[n];
		for(int i = 0; i < n; i++) {
			r[i] = in.nextInt();
		}
		int ratios[][] = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				ratios[i][j] = in.nextInt();
			}
		}
		List<Mixture> list = new ArrayList<>();
		for (int i = 0; i <= 100; i++) {
			for (int j = 0; j <= 100; j++) {
				for (int k = 0; k <= 100; k++) {
					int sum1 = 0;
					int sum2 = 0;
					int sum3 = 0;
					sum1 = (i * ratios[0][0]) + (j * ratios[1][0]) + (k * ratios[2][0]);
					sum2 = (i * ratios[0][1]) + (j * ratios[1][1]) + (k * ratios[2][1]);
					sum3 = (i * ratios[0][2]) + (j * ratios[1][2]) + (k * ratios[2][2]);

					if (r[0] != 0 && r[1] != 0 && r[2] != 0 && sum1 % r[0] == 0 && sum2 % r[1] == 0
							&& sum3 % r[2] == 0) {
						int c1 = sum1 / r[0];
						int c2 = sum2 / r[1];
						int c3 = sum3 / r[2];
						if (c1 == c2 && c2 == c3 && c1 != 0) {
							list.add(new Mixture(i, j, k, c1));
						}
						continue;
					}
					int c1 = -1;
					int c2 = -1;
					int c3 = -1;
					if(r[0] == 0 && sum1 == 0) {
						c1 = 0;
					}
					else if(r[0] != 0 && sum1 % r[0] == 0) {
						c1 = sum1 / r[0];
					}
					
					if(r[1] == 0 && sum2 == 0) {
						c2 = 0;
					}
					else if(r[1] != 0 && sum2 % r[1] == 0) {
						c2 = sum2 / r[1];
					}
					
					if(r[2] == 0 && sum3 == 0) {
						c3 = 0;
					}
					else if(r[2] != 0 && sum3 % r[2] == 0) {
						c3 = sum3 / r[2];
					}

					if(c1 != -1 && c2 != -1 && c3 != -1) {
						int mx = max(c1, max(c2, c3));
						if(mx != 0) {
							if((mx == c1 || c1 == 0) && (mx == c2 || c2 == 0) && (mx == c3 || c3 == 0)) {
								list.add(new Mixture(i, j, k, mx));
							}	
						}
						
					}
				}

			}
		}
		
		
		Collections.sort(list, new Comparator<Mixture>() {

			@Override
			public int compare(Mixture m1, Mixture m2) {
				int val = Integer.compare(m1.unit, m2.unit);
				if(val == 0) {
					return Integer.compare(m1.a + m1.b + m1.c, m2.a + m2.b + m2.c);
				}
				return val;
			}
		});
		if(list.size() > 0) {
			Mixture mixture = list.get(0);
			out.println(mixture);
		}
		else {
			out.println("NONE");
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	
	private static class Mixture{
		int unit;
		int a;
		int b; 
		int c;
		public Mixture() {
		}
		Mixture(int a, int b, int c, int unit){
			this.unit = unit;
			this.a = a;
			this.b = b;
			this.c = c;
		}
		@Override
		public String toString() {
			return a + " " + b + " " + c + " " + unit;
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