package coding.huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HuffmanNode implements Comparable<HuffmanNode>{
	
	public HuffmanNode parent;
	public HuffmanNode l;
	public HuffmanNode r;
	
	public Character element;
	public Double probability;
	
	public HuffmanNode()
	{
		
	}

	private HuffmanNode(Character element, Double probability)
	{
		this.element = element;
		this.probability = probability;
	}
	
	@Override
	public int compareTo(HuffmanNode o) {
		return this.probability.compareTo(o.probability);
	}
	
	public Map<Character, String> encoding_table()
	{
		
		Map<Character, String> result = new HashMap<Character, String>();
		List<HuffmanNode> list = new ArrayList<HuffmanNode>(); 
		visit(this, list);
		
		for (HuffmanNode node : list)
		{
		
			StringBuilder sb = new StringBuilder();			
			
			for (HuffmanNode p = node; p != null; p = p.parent) {	
				
				if (p.parent != null) 
				{			
					if (p.parent.l == p) {
						sb.append("1");
					}
					
					if (p.parent.r == p) {
						sb.append("0");
					}
				
				} else {
					
				}
					
			}
			
			result.put(node.element, sb.reverse().toString());
			
		}
		
		return result;
	}
	
	/* Depth First Search */
	public void visit(HuffmanNode node, List<HuffmanNode> list) 
	{
		
		if (node.l != null) {
			visit(node.l, list);
		}
		
		if (node.r != null) {
			visit(node.r, list);
		}
		
		if (node.l == null && node.r == null) {
			list.add(node);
		}
		
	}
	
	
	
	/*
	 * https://brilliant.org/wiki/huffman-encoding/
	 * 
	 * Returns a Huffman Tree on success, returns a null on failure
	 */
	public static HuffmanNode CreateHuffmanNode(Map<Character, Double> element_probabilities)
	{
		
		ArrayList<HuffmanNode> open_list = new ArrayList<HuffmanNode>();
		
		for (Entry<Character, Double> pair : element_probabilities.entrySet()) {
			open_list.add(new HuffmanNode(pair.getKey(), pair.getValue()));
		}
		
		Collections.sort(open_list);
		
		
		for (int i = 0; open_list.size() > 1; ) {
			
			HuffmanNode a = open_list.get(i);
			HuffmanNode b = open_list.get(i + 1);
			
			HuffmanNode p = new HuffmanNode(null, a.probability + b.probability);
			p.l = a;
			p.r = b;
			
			a.parent = p;
			b.parent = p;
					
			open_list.remove(0);
			open_list.remove(0);
			
			open_list.add(p);
			
			Collections.sort(open_list);
		
		}
		
		if (open_list.size() > 0)
			return open_list.get(0);
		else
			return null;
	}
	


	
}
