/*
ID: shacse01
LANG: JAVA
TASK: buylow
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class buylow {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000000;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 5007;
	private static final int MOD = 9901;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;
	
	
	
	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("buylow.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("buylow.out")));

		
/*

12
68 69 54 64 68 64 70 67
78 62 98 87

*/

		int n = in.nextInt();

		int arr[] = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}

		int val = lis(arr, n);
		System.out.println(val + " " + big);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	static BigInteger big = BigInteger.ZERO;
	static int lis(int arr[],int n) 
    { 
		int lis[] = new int[n];
		int max = 0;
		
		for(int i = 0; i < n; i++) {
			lis[i] = 1;
			for(int j = i - 1; j >= 0; j--) {
				if(arr[i] < arr[j] && lis[i] < lis[j] + 1) {
					lis[i] = lis[j] + 1;
				}
			}
			max = max(max, lis[i]);
		}
        BigInteger[] count = new BigInteger[n];
        
        for(int i = 0; i < n; i++) {
        	count[i] = BigInteger.ZERO;
        }
        
        for(int i = 0; i < n; i++) {
        	if(lis[i] == 1) {
        		count[i] = BigInteger.ONE;
        	}
        	else {
        		int visited = -1;
        		for(int j = i - 1; j >= 0; j--) {
        			
        			if(arr[j] > arr[i] && lis[j] + 1 == lis[i] && arr[j] != visited) {
        				count[i] = count[i].add(count[j]);
        				visited = arr[j];
        			}
        		}
        	}
        	
        }
        int visited = -1;
        for(int i = n - 1; i >= 0; i--) {
        	if(lis[i] == max && visited != arr[i]) {
        		big = big.add(count[i]);
        		visited = arr[i];
        	}
        }
            return max; 
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