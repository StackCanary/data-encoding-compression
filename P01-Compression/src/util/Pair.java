package util;

public class Pair<K, V> {

	public K a;
	public V b;
	
	public Pair(K a, V b)
	{
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return "(" + a.toString() + ", " + b.toString() + ")";
	}
}
