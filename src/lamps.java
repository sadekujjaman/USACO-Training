/*
ID: shacse01
LANG: JAVA
TASK: lamps
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class lamps {

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
		
		
		InputReader in = new InputReader(new FileInputStream("lamps.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));

		
/*



*/

		
		int n = in.nextInt();
		int c = in.nextInt();
		List<Integer> onLamp = new ArrayList<Integer>();
		List<Integer> offLamp = new ArrayList<Integer>();
		while(true) {
			int a = in.nextInt();
			if(a == -1) {
				break;
			}
			onLamp.add(a - 1);
		}
		while(true) {
			int a = in.nextInt();
			if(a == -1) {
				break;
			}
			offLamp.add(a - 1);
		}
		
		c = min(c, 4);
		Set<String> list = new HashSet<String>();
		String initialState = "";
		switch(c) {
		case 0:
			initialState = getInitialState(n);
			if(isValidState(initialState, onLamp, offLamp)) {
				list.add(initialState);
			}
			break;
		case 1:
			singleButtonChange(n, onLamp, offLamp, list);
			break;
		case 2:
			doubleButtonChange(n, onLamp, offLamp, list);
			break;
		case 3:
			tripleButtonChange(n, onLamp, offLamp, list);
			break;
		case 4:
			allButtonChange(n, onLamp, offLamp, list);
			break;
		}
		if(list.size() == 0) {
			out.println("IMPOSSIBLE");
		}
		else {
			List<String> ans = new ArrayList<String>(list);
			Collections.sort(ans);
			for(String str : ans) {
				out.println(str);
			}
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static void allButtonChange(int n, List<Integer> onLamp, List<Integer> offLamp, Set<String> list) {
		
		
		
		for(int i = 1; i <= 4; i++) {
			String initialState = getInitialState(n);
			String afterState = changeState(i, n, initialState);
			for(int j = i; j <= 4; j++) {
				afterState = changeState(j, n, afterState);
				for(int k = j; k <= 4; k++) {
					afterState = changeState(k, n, afterState);
					for(int l = k; l <= 4; l++) {
						afterState = changeState(l, n, afterState);
						if(isValidState(afterState, onLamp, offLamp)) {
							list.add(afterState);
						}
					}
					
				}
			}
			
		}
	}

	private static void tripleButtonChange(int n, List<Integer> onLamp, List<Integer> offLamp, Set<String> list) {
		for(int i = 1; i <= 4; i++) {
			String initialState = getInitialState(n);
			String afterState = changeState(i, n, initialState);
			
			for(int j = i; j <= 4; j++) {
				afterState = changeState(j, n, afterState);
				for(int k = j; k <= 4; k++) {
					afterState = changeState(k, n, afterState);
					if(isValidState(afterState, onLamp, offLamp)) {
						list.add(afterState);
					}
				}
			}
			
		}
	}

	private static void doubleButtonChange(int n, List<Integer> onLamp, List<Integer> offLamp, Set<String> list) {
		for(int i = 1; i <= 4; i++) {
			String initialState = getInitialState(n);
			String afterState = changeState(i, n, initialState);
			for(int j = i; j <= 4; j++) {
				afterState = changeState(j, n, afterState);
				if(isValidState(afterState, onLamp, offLamp)) {
					list.add(afterState);
				}
			}
			
		}
	}
	private static void singleButtonChange(int n, List<Integer> onLamp, List<Integer> offLamp, Set<String> list) {
		for(int i = 1; i <= 4; i++) {
			String initialState = getInitialState(n);
			String afterState = changeState(i, n, initialState);
			if(isValidState(afterState, onLamp, offLamp)) {
				list.add(afterState);
			}
		}
	}
	
	private static String changeState(int button, int n, String initialState) {
		StringBuilder sb = new StringBuilder();
		switch(button) {
		case 1:
			for(int i = 0; i < n; i++) {
				if(initialState.charAt(i) == '1') {
					sb.append("0");
				}
				else {
					sb.append("1");
				}
			}
			break;
		case 2:
			for(int i = 0; i < n; i++) {
				if(i % 2 == 0 && initialState.charAt(i) == '1') {
					sb.append("0");
				}
				else if(i % 2 == 0) {
					sb.append("1");
				}
				else {
					sb.append(initialState.charAt(i));
				}
			}
			break;
		case 3:
			for(int i = 0; i < n; i++) {
				if(i % 2 == 1 && initialState.charAt(i) == '1') {
					sb.append("0");
				}
				else if(i % 2 == 1){
					sb.append("1");
				}
				else {
					sb.append(initialState.charAt(i));
				}
			}
			break;
		case 4:
			for(int i = 0; i < n; i++) {
				if(i % 3 == 0 && initialState.charAt(i) == '1') {
					sb.append("0");
				}
				else if(i % 3 == 0) {
					sb.append("1");
				}
				else {
					sb.append(initialState.charAt(i));
				}
			}
			break;
		}
		return sb.toString();
	}

	private static boolean isValidState(String initialState, List<Integer> onLamp, List<Integer> offLamp) {
		for(int i = 0; i < onLamp.size(); i++) {
			if(initialState.charAt(onLamp.get(i)) == '0') {
				return false;
			}
		}
		for(int i = 0; i < offLamp.size(); i++) {
			if(initialState.charAt(offLamp.get(i)) == '1') {
				return false;
			}
		}
		
		return true;
	}

	private static String getInitialState(int n) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n; i++) {
			sb.append("1");
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