/*
ID: shacse01
LANG: JAVA
TASK: cowtour
*/

import java.io.*;
import java.math.*;
import java.util.*;

/**
 *
 * @author Saju
 *
 */

public class cowtour {

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
		
		
		InputReader in = new InputReader(new FileInputStream("cowtour.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtour.out")));

		
/*


*/
		
//		long prev = System.currentTimeMillis();
		
		int n = in.nextInt();
		Point points[] = new Point[n];
		for(int i = 0; i < n; i++) {
			double x = in.nextDouble();
			double y = in.nextDouble();
			points[i] = new Point(x, y);
		}
		
		List<Integer> adj[] = new ArrayList[n];
		for(int i = 0; i < n; i++) {
			adj[i] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < n; i++) {
			char ch[] = in.next().toCharArray();
//			System.out.println(Arrays.toString(ch));
			for(int j = i + 1; j < n; j++) {
				if(ch[j] == '1') {
					adj[i].add(j);
					adj[j].add(i);
					
				}
			}
			
//			System.out.println(i + " -> " + Arrays.toString(adj[0].toArray()));
		}
		
		
//		for(int i = 0; i < n; i++) {
//			System.out.print(i + "("+ points[i] + ")" + " -> ");
//			for(int v : adj[i]) {
//				System.out.print(v + " ");
//			}
//			System.out.println();
//		}
		
		List<ConnectedComponent> connectedComponents = new ArrayList<>();
		boolean visited[] = new boolean[n];
		
		for(int i = 0; i < n; i++) {
			if(!visited[i]) {
				List<Integer> component = new ArrayList<>();
				dfs(i, n, adj, visited, component);
				connectedComponents.add(new ConnectedComponent(component, 0.0));
			}
		}
		
		int componentSize = connectedComponents.size();
//		System.out.println(componentSize);
		
		double allNodesShortedDistance[] = new double[n];
		
		for(ConnectedComponent comp : connectedComponents) {
//			System.out.print("Comp => ");
			double diameter = 0.0;
			for(int e : comp.nodes) {
				allNodesShortedDistance[e] = shortestDistance(e, n, adj, points);
				diameter = Math.max(diameter, allNodesShortedDistance[e]);
//				System.out.print(e + " ");
			}
//			System.out.println();
			comp.diameter = diameter;
		}
		
		double min = Double.MAX_VALUE;
		for(int i = 0; i < componentSize; i++) {
			ConnectedComponent comp1 = connectedComponents.get(i);
			for(int j = 0; j < componentSize; j++) {
				if(i != j) {
					ConnectedComponent comp2 = connectedComponents.get(j);
					for(int u : comp1.nodes) {
						for(int v: comp2.nodes) {
							double dist = allNodesShortedDistance[u] + allNodesShortedDistance[v];
							dist = dist + getDistance(points[u], points[v]);
							double max = Math.max(dist, Math.max(comp1.diameter, comp2.diameter));
							
							min = Math.min(min, max);
						}
					}
				}
			}
		}
		out.printf("%.6f\n", min);
		
		out.flush();
		out.close();
		System.exit(0);
	}
	
	private static double shortestDistance(int start, int n, List<Integer>[] adj, Point[] points) {
		boolean[] visited = new boolean[n];

		PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				return Double.compare(node1.dist, node2.dist);
			}
		});
//		Queue<Node> queue = new LinkedList<>();
		Node startNode = new Node(start, 0.0);
		queue.offer(startNode);
//		visited[start] = true;
		int maxNode = startNode.u;
		double maxLevel = startNode.dist;
		double distance[] = new double[n];
		for(int i = 0; i < n; i++) {
			distance[i] = Double.MAX_VALUE;
		}
		distance[start] = 0.0;
		while (!queue.isEmpty()) {
			Node unode = queue.remove();
			if(!visited[unode.u]) {
				visited[unode.u] = true;
				for(int vv : adj[unode.u]) {
					if(!visited[vv]) {
						double dist = getDistance(points[unode.u], points[vv]);
						if(distance[unode.u] + dist < distance[vv]) {
							distance[vv] = distance[unode.u] + dist;
							Node vnode = new Node(vv, distance[vv]);
							queue.offer(vnode);
						}
					}
				}
			}
				
		}
		for(int i = 0; i < n; i++) {
			if(visited[i]) {
				maxLevel = Math.max(maxLevel, distance[i]);
			}
		}
		return maxLevel;
	}

	private static double getDistance(Point p1, Point p2) {
		double dx = p1.a - p2.a;
		double dy = p1.b - p2.b;
		
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	private static class ConnectedComponent{
		List<Integer> nodes;
		double diameter;
		public ConnectedComponent(List<Integer> nodes, double diameter) {
			this.nodes = nodes;
			this.diameter = diameter;
		}
	}
	
	private static class Node{
		int u;
		double dist;
		int level = 0;
		Node(int u, double dist){
			this.u = u;
			this.dist = dist;
		}
	}
	
	private static void dfs(int u, int n, List<Integer>[] adj, boolean[] visited, List<Integer> component) {
		visited[u] = true;
		component.add(u);
		for(int v : adj[u]) {
			if(!visited[v]) {
				dfs(v, n, adj, visited, component);
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