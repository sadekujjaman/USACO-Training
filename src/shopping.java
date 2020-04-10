/*
ID: shacse01
LANG: JAVA
TASK: shopping
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class shopping {

	private static int dx[] = {-1, 0, 1, 0 };
	private static int dy[] = {0, 1, 0, -1 };

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 1007;
	private static final int MOD = 9901;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;
	
	static int mx = 7;
	static int[][][][][] dp = new int[mx][mx][mx][mx][mx];
	
	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("shopping.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));

		
/*


*/
		
		int n = in.nextInt();
		Offer[] offers = new Offer[n];
		for(int i = 0; i < n; i++) {
			int sz = in.nextInt();
			Map<Integer, Product> map = new HashMap<>(); 
			for(int j = 0; j < sz; j++) {
				int c = in.nextInt();
				int k = in.nextInt();
				map.put(c, new Product(c, k, 0));
			}
			int p = in.nextInt();
			offers[i] = new Offer(sz, p, map);
		}
		
		Product[] products = new Product[mx];
		for(int i = 0; i < mx; i++) {
			products[i] = new Product(0, 0, 0);
		}
		int b = in.nextInt();
		for(int i = 0; i < b; i++) {
			int c = in.nextInt();
			int k = in.nextInt();
			int p = in.nextInt();
			products[i] = new Product(c, k, p);
		}
		
		for(int i = 0; i < mx; i++) {
			for(int j = 0; j < mx; j++) {
				for(int k = 0; k < mx; k++) {
					for(int l = 0; l < mx; l++) {
						Arrays.fill(dp[i][j][k][l], -1);
					}
				}
			}
		}
		
		int ans = call(products[0].item, products[1].item,
				products[2].item, products[3].item, products[4].item, products, offers);
		out.println(ans);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	
	static int call(int a, int b, int c, int d, int e, Product[] products, Offer[] offers) {
		if (a < 0 || b < 0 || c < 0 || d < 0 || e < 0)
			return INT_INF;
		if (a == 0 && b == 0 && c == 0 && d == 0 && e == 0)
			return 0;
		if (dp[a][b][c][d][e] != -1)
			return dp[a][b][c][d][e];

		int ret = INT_INF;

		for (int i = 0; i < offers.length; ++i) {
			int na = a;
			int nb = b;
			int nc = c;
			int nd = d;
			int ne = e;;
			if(offers[i].map.containsKey(products[0].code)) {
				na = a - offers[i].map.get(products[0].code).item;
			}
			if(offers[i].map.containsKey(products[1].code)) {
				nb = b - offers[i].map.get(products[1].code).item;
			}
			if(offers[i].map.containsKey(products[2].code)) {
				nc = c - offers[i].map.get(products[2].code).item;
			}
			if(offers[i].map.containsKey(products[3].code)) {
				nd = d - offers[i].map.get(products[3].code).item;
			}
			if(offers[i].map.containsKey(products[4].code)) {
				ne = e - offers[i].map.get(products[4].code).item;
			}
			
			ret = min(ret, offers[i].price + call(na, nb, nc, nd, ne, products, offers));
		}

		for (int i = 1; i <= a; i++) {
			ret = min(ret, i * products[0].price + call(a - i, b, c, d, e, products, offers));
		}
		for (int i = 1; i <= b; i++) {
			ret = min(ret, i * products[1].price + call(a, b - i, c, d, e, products, offers));
		}
		for (int i = 1; i <= c; i++) {
			ret = min(ret, i * products[2].price + call(a, b, c - i, d, e, products, offers));
		}
		for (int i = 1; i <= d; i++) {
			ret = min(ret, i * products[3].price + call(a, b, c, d - i, e, products, offers));
		}
		for (int i = 1; i <= e; i++) {
			ret = min(ret, i * products[4].price + call(a, b, c, d, e - i, products, offers));
		}

		return dp[a][b][c][d][e] = ret;
	}

	
	private static class Offer{
		int item;
		int price;
		Map<Integer, Product> map;
		public Offer(int item, int price, Map<Integer, Product> map) {
			this.item = item;
			this.price = price;
			this.map = map;
		}
		
	}

	private static class Product{
		int code;
		int item;
		int price;
		Product(int code, int item, int price){
			this.code = code;
			this.item = item;
			this.price = price;
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