/*
ID: shacse01
LANG: JAVA
TASK: lgame
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class lgame {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000000;
	private static final long NEG_INF = Long.MIN_VALUE;
	private static final int NEG_INT_INF = Integer.MIN_VALUE;
	private static final double EPSILON = 1e-10;

	private static final int MAX = 57;
	private static final int MOD = 9901;

	private static final int MAXN = 100007;
	private static final int MAXA = 10000009;
	private static final int MAXLOG = 22;
	
	
	public static void main(String[] args) throws IOException {

//		InputReader in = new InputReader(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		
//		InputReader in = new InputReader(new FileInputStream("src/test.in"));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/test.out")));
		
		
		InputReader in = new InputReader(new FileInputStream("lgame.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lgame.out")));

		
/*



*/

		Map<Character, Integer> map = new HashMap<>();
		map.put('q', 7);
		map.put('w', 6);
		map.put('e', 1);
		map.put('r', 2);
		map.put('t', 2);
		map.put('y', 5);
		map.put('u', 4);
		map.put('i', 1);
		map.put('o', 3);
		map.put('p', 5);
		map.put('l', 3);
		map.put('k', 6);
		map.put('j', 7);
		map.put('h', 5);
		map.put('g', 5);
		map.put('f', 6);
		map.put('d', 4);
		map.put('s', 1);
		map.put('a', 2);
		map.put('z', 7);
		map.put('x', 7);
		map.put('c', 4);
		map.put('v', 6);
		map.put('b', 5);
		map.put('n', 2);
		map.put('m', 5);
		
		InputReader br = new InputReader(new FileInputStream("lgame.dict"));
//		InputReader br = new InputReader(new FileInputStream("src/dict.txt"));
		
		char[] letters = in.next().toCharArray();
		int[] visited = new int[30];
		for(int i = 0; i < letters.length; i++) {
			int ch = letters[i] - 'a';
			visited[ch]++;
		}
		List<String> wordList = new ArrayList<String>();
		while(true) {
			String str = br.next();
			if(str.equals(".")) {
				break;
			}
			if(isOk(str, visited)) {
				wordList.add(str);
			}
			
		}
		
		
		Map<Integer, List<String>> map1 = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
		int max = 0;
		int wordSz = wordList.size();
		for(int i = 0; i < wordSz; i++) {
			String str1 = wordList.get(i);
			int val = getValue(str1, map);
			
			max = max(max, val);
			for(int j = i + 1; j < wordSz; j++) {
				String str2 = wordList.get(j);
				String s = str1 + str2;
				if(isOk(s, visited)) {
					 val = getValue(s, map);
					 max = max(val, max);
				}
			}
		}
		
		for (String word : wordList) {
			int sum = getValue(word, map);
			if (map1.containsKey(sum)) {
				map1.get(sum).add(word);
			} else {
				List<String> list = new ArrayList<>();
				list.add(word);
				map1.put(sum, list);
			}
		}
//		System.out.println(max);
		out.println(max);
		
		
		Set<String> set = new TreeSet<String>();
		
		for(Integer val : map1.keySet()) {
			if(val == max) {
				for(String s : map1.get(val)) {
//					out.println(s);
					set.add(s);
				}
			}
			else {
				int diff = max - val;
				if(map1.containsKey(diff)) {
					List<String> list1 = map1.get(val);
					List<String> list2 = map1.get(diff);
					int sz1 = list1.size();
					int sz2 = list2.size();
					for(int i = 0; i < sz1; i++) {
						String str1 = list1.get(i);
						for(int j = 0; j < sz2; j++) {
							String str2 = list2.get(j);
							String s = str1 + "" + str2;
							if(isOk(s, visited)) {
								if(str1.compareTo(str2) > 0) {
//									out.println(str2 + " " + str1);
									set.add(str2 + " " + str1);
								}
								else {
//									out.println(str1 + " " + str2);
									set.add(str1 + " " + str2);
								}
							}
						}
					}
					List<String> list = new ArrayList<>();
					map1.put(val, list);
					map1.put(diff, list);
				}
			}
		}
		
		for(String str : set) {
			out.println(str);
		}
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static int getValue(String word, Map<Character, Integer> map) {
		int sum = 0;
		for(int i = 0; i < word.length(); i++) {
			sum += map.get(word.charAt(i));
		}
		return sum;
	}

	private static boolean isOk(String str, int[] visited) {
		int count[] = new int[30];
		for(int i = 0; i < str.length(); i++) {
			int ch = str.charAt(i) - 'a';
			count[ch]++;
		}
		for(int i = 0; i < str.length(); i++) {
			int ch = str.charAt(i) - 'a';
			if(count[ch] > visited[ch]) {
				return false;
			}
		}
		return true;
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