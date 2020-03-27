/*
ID: shacse01
LANG: JAVA
TASK: preface
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class preface {

	private static int dx[] = { 1, 0, -1, 0 };
	private static int dy[] = { 0, -1, 0, 1 };

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = Integer.MAX_VALUE;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 1007;
	private static final long MOD = 1000000007;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;

	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("preface.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));

		
/*


*/

		Map<Integer, String> map = new TreeMap<Integer, String>(Collections.reverseOrder());
		map.put(1, "I");
		map.put(4, "IV");
		map.put(5, "V");
		map.put(9, "IX");
		map.put(10, "X");
		map.put(40, "XL");
		map.put(50, "L");
		map.put(90, "XC");
		map.put(100, "C");
		map.put(400, "CD");
		map.put(500, "D");
		map.put(900, "CM");
		map.put(1000, "M");
		
		int ICount = 0;
		int VCount = 0;
		int XCount = 0;
		int LCount = 0;
		int CCount = 0;
		int DCount = 0;
		int MCount = 0;
		
		int n = in.nextInt();
		for(int i = 1; i <= n; i++) {
			String str = getRomanNumeral(i, map);
//			System.out.println(str);
			for(int j = 0; j < str.length(); j++) {
				if(str.charAt(j) == 'I') {
					ICount++;
				}
				if(str.charAt(j) == 'V') {
					VCount++;
				}
				if(str.charAt(j) == 'X') {
					XCount++;
				}
				if(str.charAt(j) == 'L') {
					LCount++;
				}
				if(str.charAt(j) == 'C') {
					CCount++;
				}
				if(str.charAt(j) == 'D') {
					DCount++;
				}
				if(str.charAt(j) == 'M') {
					MCount++;
				}
			}
		}
		
		if(ICount > 0) {
			out.println("I " + ICount);
		}
		if(VCount > 0) {
			out.println("V " + VCount);
		}
		if(XCount > 0) {
			out.println("X " + XCount);
		}
		if(LCount > 0) {
			out.println("L " + LCount);
		}
		if(CCount > 0) {
			out.println("C " + CCount);
		}
		if(DCount > 0) {
			out.println("D " + DCount);
		}
		if(MCount > 0) {
			out.println("M " + MCount);
		}
		
		
		out.flush();
		out.close();
		System.exit(0);
	}
	


	private static String getRomanNumeral(int n, Map<Integer, String> map) {
		StringBuilder sb = new StringBuilder();
		for(Integer e : map.keySet()) {
			if(n >= e) {
				int count = n / e;
				n = n % e;
				for(int i = 0; i < count; i++) {
					sb.append(map.get(e));
				}
			}
		}
		return sb.toString();
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