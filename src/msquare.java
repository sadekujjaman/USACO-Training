/*
ID: shacse01
LANG: JAVA
TASK: msquare
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class msquare {

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
		
		
		InputReader in = new InputReader(new FileInputStream("msquare.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));

		
/*



*/
	
		int src[] = new int[8];
		for(int i = 0; i < 8; i++) {
			src[i] = i + 1;
		}
		
		int dest[] = new int[8];
		for(int i = 0; i < 8; i++) {
			dest[i] = in.nextInt();
		}
		
		Queue<Move> queue = new LinkedList<>();
		Move st = new Move(0, "", src);
		queue.add(st);
		int num1 = getValue(dest);
		Set<Integer> set = new HashSet<>();
		while(!queue.isEmpty()) {
			Move current = queue.remove();
			
			if(current.val == num1) {
				
				out.println(current.count);
				for(int i = 0; i < current.count; i++) {
					out.print(current.str.charAt(i));
					if(i % 60 == 59) {
						out.print("\n");
					}
					
				}
				out.print("\n");
				break;
			}
			if(set.contains(current.val)) {
				continue;
			}
			set.add(current.val);
			
			Move A = getAMove(current);
			Move B = getBMove(current);
			Move C = getCMove(current);
			queue.add(A);
			queue.add(B);
			queue.add(C);
		}
		
		
		out.flush();
		out.close();
		System.exit(0);
	}



	private static int getValue(int[] src) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < src.length; i++) {
			sb.append(src[i]);
		}
		return Integer.parseInt(sb.toString());
	}
	
	private static Move getCMove(Move current) {
		int arr[] = current.arr;
		int len = arr.length;
		int res[] = new int[len];
		for(int i = 0; i < len; i++) {
			res[i] = arr[i];
		}
		int temp = res[1];
		res[1] = res[6];
		res[6] = res[5];
		res[5] = res[2];
		res[2] = temp;
		return new Move(current.count + 1, current.str + "C", res);
	}


	private static Move getBMove(Move current) {
		int arr[] = current.arr;
		int len = arr.length;
		int res[] = new int[len];
		res[0] = arr[3];
		for(int i = 1; i < 4; i++) {
			res[i] = arr[i - 1];
		}
		for(int i = 4; i < 7; i++) {
			res[i] = arr[i + 1];
		}
		res[7] = arr[4];
		return new Move(current.count + 1, current.str + "B", res);
	}


	private static Move getAMove(Move current) {
		int arr[] = current.arr;
		int len = arr.length;
		int res[] = new int[len];
		for(int i = 0; i < len; i++) {
			res[i] = arr[len - i - 1];
		}
		return new Move(current.count + 1, current.str + "A", res);
	}


	private static boolean isEqual(int[] arr, int[] dest) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != dest[i]) {
				return false;
			}
		}
		return true;
	}


	private static class Move{
		int count;
		String str;
		int[] arr;
		int val;
		Move(int count, String str, int[] arr){
			this.count = count;
			this.str = str;
			this.arr = arr;
			this.val = getValue(arr);
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