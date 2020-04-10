/*
ID: shacse01
LANG: JAVA
TASK: heritage
*/

import java.io.*;
import java.math.*;
import java.util.*;


/**
 *
 * @author Saju
 *
 */

public class heritage {

	private static int dx[] = {2, 2, 1, -1, -2, -2, -1, 1};
	private static int dy[] = {1, -1, -2, -2, -1, 1, 2, 2};
	
	private static int qdx[] = {1, 1, 0, -1, -1, -1, 0, 1};
	private static int qdy[] = {0, -1, -1, -1, 0, 1, 1, 1};

	private static final long INF = Long.MAX_VALUE;
	private static final int INT_INF = 100000000;
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
		
		
		InputReader in = new InputReader(new FileInputStream("heritage.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("heritage.out")));

		
/*

ABEDFCHG
CBADEFGH

*/
		
		String inOrder = in.next();
		String preOrder = in.next();
		int len = inOrder.length();
		
		BinaryTree binaryTree = new BinaryTree();
		Node root = binaryTree.buildTree(inOrder, preOrder, 0, len - 1);
		binaryTree.printPostOrder(root);
		out.println(binaryTree.postOrderString);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	/**
	 * 
	 * https://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
	 *
	 */
	
	
	private static class BinaryTree{
		
		StringBuilder postOrderString = new StringBuilder();
		int preIndex = 0;
		
		Node buildTree(String inOrder, String preOrder, int start, int end) {
			if(start > end) {
				return null;
			}
			Node node = new Node(preOrder.charAt(preIndex++));
			if(start == end) {
				return node;
			}
			int rootIndex = search(inOrder, start, end, node.data);
			node.left = buildTree(inOrder, preOrder, start, rootIndex - 1);
			node.right = buildTree(inOrder, preOrder, rootIndex + 1,  end);
			return node;
		}

		private int search(String inOrder, int start, int end, char data) {
			for(int i = start; i <= end; i++) {
				if(inOrder.charAt(i) == data) {
					return i;
				}
			}
			return 0;
		}
		
		void printInOrder(Node node) {
			if(node == null) {
				return;
			}
			printInOrder(node.left);
			System.out.print(node.data + " ");
			printInOrder(node.right);
		}
		
		void printPostOrder(Node node) {
			if(node == null) {
				return;
			}
			
			printPostOrder(node.left);
			printPostOrder(node.right);
//			System.out.print(node.data + " ");
			postOrderString.append(node.data);
		}
	}
	
	private static class Node{
		char data;
		Node left;
		Node right;
		
		Node(char data){
			this.data = data;
			left = null;
			right = null;
		}
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