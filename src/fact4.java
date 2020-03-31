/*
ID: shacse01
LANG: JAVA
TASK: fact4
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class fact4 {

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
		
		
		InputReader in = new InputReader(new FileInputStream("fact4.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));

		
/*


*/
		
		int n =  in.nextInt();
		
		
		// solution -> 1
		
		int ans = solve1(n);
		out.println(ans);
		
		
		/*
		
		// solution -> 2
		
		ans = solve2(n);
		out.println(ans);
		
		*/
		
		/*
		// solution -> 3
		
		ans = solve3(n);
		out.println(ans);
		
		*/
		
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static int solve1(int n) {
		BigInteger[] fact = new BigInteger[n + 1];
		fact[0] = BigInteger.ONE;
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= n; i++) {
			fact[i] = fact[i - 1].multiply(BigInteger.valueOf(i));
//			System.out.println(i + "!=" + sb.toString() + " => " + fact[i]);
		}
		sb.append(fact[n].toString());
		int len = sb.length();
		for(int i = len - 1; i >= 0; i--) {
			if(sb.charAt(i) != '0') {
				return sb.charAt(i) - '0';
			}
		}
		return 0;
	}
	
	private static int solve2(int n) {
		BigInteger res = BigInteger.valueOf(1);
		int two = 0;
		int five = 0;
		for(int i = 1; i <= n; i++) {
			int val = i;
			while(val % 2 == 0) {
				val /= 2;
				two++;
			}
			while(val % 5 == 0) {
				five++;
				val /= 5;
			}
			res = res.multiply(BigInteger.valueOf(val));
		}
		
		if(two > five) {
			for(int i = five + 1; i <= two; i++) {
				res = res.multiply(BigInteger.valueOf(2));
			}
		}
		else if(two < five) {
			for(int i = two + 1; i <= five; i++) {
				res = res.multiply(BigInteger.valueOf(5));
			}
		}
		String str = res.toString();
		
		return str.charAt(str.length() - 1) - '0';
	}
	
	private static int solve3(int n) {
		int two = 0;
		int five = 0;
		int res = 1;
		for(int i = 1; i <= n; i++) {
			int val = i;
			while(val % 2 == 0) {
				val /= 2;
				two++;
			}
			while(val % 5 == 0) {
				five++;
				val /= 5;
			}
			res = (res * val) % 10;
		}
		
		if(two > five) {
			for(int i = five + 1; i <= two; i++) {
				res = (res * 2) % 10;
			}
		}
		else if(two < five) {
			for(int i = two + 1; i <= five; i++) {
				res = (res * 5) % 10;
			}
		}
		return res;
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