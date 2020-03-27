/*
ID: shacse01
LANG: JAVA
TASK: zerosum
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class zerosum {

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
		
		
		InputReader in = new InputReader(new FileInputStream("zerosum.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));

		
/*



*/
		
		
		int n = in.nextInt();
		call(2, 1, n, "1");
		Collections.sort(result);
		
		for(String s : result) {
			out.println(s);
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}

	private static List<String> result = new ArrayList<String>();
	
	private static void call(int current, int sum, int n, String ans) {
		
		if(current <= n) {
			String temp = ans + "+" + (current);
			call(current + 1, sum + current, n, temp);
			temp = ans + "-" + (current);
			call(current + 1, sum - current, n, temp);
			for(int j = ans.length() - 1; j >= 0; j--) {
				if(ans.charAt(j) == '+') {
					int sub = Integer.parseInt(ans.substring(j + 1));
					int val = sum - sub + ((sub * 10) + current);
					temp = ans + "" + current;
					call(current + 1, val, n, temp);
					break;
				}
				else if(ans.charAt(j) == '-') {
					int sub = Integer.parseInt(ans.substring(j + 1));
					int val = sum + sub - ((sub * 10) + current);
					temp = ans + "" + current;
					call(current + 1, val, n, temp);
					break;
				}
				else if(j == 0) {
					int sub = Integer.parseInt(ans + current);
					temp = ans + "" + current;
					call(current + 1, sub, n, temp);
					break;
				}
			}
		}
		else {
//			System.out.println(ans + " " + sum);
			if(sum == 0) {
//				System.out.println("GET");
				StringBuilder sb = new StringBuilder();
				sb.append(ans.charAt(0));
				for(int i = 1; i < ans.length(); i++) {
					if(Character.isDigit(ans.charAt(i)) && Character.isDigit(ans.charAt(i - 1))) {
						sb.append(" ");
					}
					sb.append(ans.charAt(i));
				}
//				System.out.println(ans);
//				System.out.println(sb.toString());
				result.add(sb.toString());
			}
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



	private static class Pair{
		String str;
		double val;
		Pair(String str, double val){
			this.str = str;
			this.val = val;
		}
		@Override
		public boolean equals(Object obj) {
			Pair p = (Pair)obj;
			return str.equals(p.str);
		}
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