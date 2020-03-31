/*
ID: shacse01
LANG: JAVA
TASK: contact
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class contact {

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
		
		
		InputReader in = new InputReader(new FileInputStream("contact.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("contact.out")));

		
/*

2 4 10
01010010010001000111101100001010011001111000010010011110010000000


*/
		
		
		
		int a = in.nextInt();
		int b = in.nextInt();
		int n = in.nextInt();

		StringBuilder sb1 = new StringBuilder();
		while(in.hasNext()) {
			sb1.append(in.next());
		}
		String str = sb1.toString();
		int len = str.length();
		
		Map<String, Integer> map = new HashMap<>();
		int max = 0;
		for(int i = a; i <= b; i++) {
			for(int j = 0; j <= len - i; j++) {
				String str1 = str.substring(j, j + i);
				try {
					int val = map.get(str1);
					val++;
					map.put(str1, val);
					max = max(max, val);
				}
				catch(Exception e) {
					map.put(str1, 1);
					max = max(max, 1);
				}
			}
		}
		
		List<Pattern> patterns[] = new ArrayList[max + 1];
		for(int i = 0; i <= max; i++) {
			patterns[i] = new ArrayList<>();
		}
		for(String str1 : map.keySet()) {
			int val = map.get(str1);
			patterns[val].add(new Pattern(str1));
		}
		
		for(int i = 1; i <= max; i++) {
			if(patterns[i].size() != 0) {
				Collections.sort(patterns[i]);
			}
		}
		
		int count = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = max; i > 0; i--) {
			if(patterns[i].size() == 0) {
				continue;
			}
			if(count > 0) {
				sb.append("\n");
			}
			count++;
			// 0 0 0 0 0 0
			// 0 0 0 0
			sb.append(i);
			sb.append("\n");
			List<Pattern> list = patterns[i];
			int sz = list.size();
			for(int j = 1; j <= sz; j++) {
				sb.append(list.get(j - 1).pattern);
				if(j % 6 == 0 && j != sz) {
					sb.append("\n");
				}
				else if(j < sz) {
					sb.append(" ");
				}
			}
			
			if(count == n) {
				break;
			}
			
			
		}
//		System.out.println(sb.toString().length());
		out.println(sb.toString());
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static class Pattern implements Comparable<Pattern>{
		String pattern;
		int patternValue;
		int patternLen;
		public Pattern(String pattern) {
			this.pattern = pattern;
			patternValue = Integer.parseInt(pattern, 2);
			patternLen = pattern.length();
		}
		
		@Override
		public int hashCode() {
			return this.patternValue;
		}
		
		@Override
		public boolean equals(Object obj) {
			Pattern p = (Pattern)obj;
			return this.patternValue == p.patternValue;
		}
		
		@Override
		public int compareTo(Pattern p2) {
			
			int val = Integer.compare(this.patternLen, p2.patternLen);
			if(val == 0) {
				return Integer.compare(this.patternValue, p2.patternValue);
			}
			return val;
		}
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