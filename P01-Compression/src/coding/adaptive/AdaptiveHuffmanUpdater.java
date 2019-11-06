package coding.adaptive;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import coding.adaptive.AdaptiveNode.NodeType;

public class AdaptiveHuffmanUpdater {
	
	public AdaptiveNode root;
	AdaptiveNode nytNode;
	
	final int Alphabet_Size = (int) Math.pow(2, 8);
	final int Node_Count = Alphabet_Size * 2 - 1;
	
	HashMap<Character, AdaptiveNode> cMap = new HashMap<Character, AdaptiveNode>();
	/*
	 * Constructs an AdaptiveHuffmanNode with the initial state
	 */
	public AdaptiveHuffmanUpdater() 
	{
		root = nytNode = new AdaptiveNode(NodeType.NotYetTransmitted, Node_Count, 0);
	}
	
	void Update(Character c)
	{
		/*
		 * Not seen element before
		 */
		if (!cMap.containsKey(c)) {

				nytNode.l = new AdaptiveNode(NodeType.NotYetTransmitted, nytNode.number - 2, 0);
				nytNode.r = new AdaptiveNode(NodeType.Element, nytNode.number - 1, 1, c);
				cMap.put(c, nytNode.r);

				nytNode.l.parent = nytNode;
				nytNode.r.parent = nytNode;
				nytNode.type = NodeType.Element;

				nytNode = nytNode.l;
				
				IncrementAndSwapIfNoLongerSiblingProperty(nytNode.parent);
			
			return;
		} else {
			
			cMap.get(c).weight++;
			
			IncrementAndSwapIfNoLongerSiblingProperty(cMap.get(c).parent);
			
		}
			
	}
	
	/*
	 * Search tree for another node with the same weight but the greatest number, swap them
	 */
	void IncrementAndSwapIfNoLongerSiblingProperty(AdaptiveNode node)
	{
		// Iterate through parent nodes
		for (AdaptiveNode conductor = node; conductor != null; conductor = conductor.parent) {
			
			// Increase weight of parent node

			// If current conductor is not root
			if (conductor.parent != null) {
				
				PriorityQueue<AdaptiveNode> queue = new PriorityQueue<AdaptiveNode>(Comparator.comparing(AdaptiveNode::getNumber, Comparator.reverseOrder()));
				
				FindSwapCandidate(root, queue, conductor.weight, conductor.number);
				
				if (queue.size() > 0) {
					AdaptiveNode toBeSwapped = queue.peek();
					Swap(conductor, toBeSwapped);
					conductor = toBeSwapped;
				}
				
			}
			
			conductor.weight++;
		}
		
	}
	
	void Swap(AdaptiveNode l, AdaptiveNode r)
	{
		
		AdaptiveNode lCopy = new AdaptiveNode(l);
		AdaptiveNode rCopy = new AdaptiveNode(r);
		
		l.copy(rCopy);
		r.copy(lCopy);
		
		l.number = rCopy.number;
		r.number = lCopy.number;
		
		l.parent = lCopy.parent;
		r.parent = rCopy.parent;
		
		if (l.l != null)
			l.l.parent = l;
		
		if (l.r != null)
			l.r.parent = l;
		
		if (r.l != null)
			r.l.parent = r;
		
		if (r.r != null)
			r.r.parent = r;
		
		if (l.element != null)
			cMap.put(l.element, l);
		
		if (r.element != null)
			cMap.put(r.element, r);
		
	}
	
	void FindSwapCandidate(AdaptiveNode node, PriorityQueue<AdaptiveNode> list, int weight, int number)
	{
		if (node.l != null) {
			FindSwapCandidate(node.l, list, weight, number);
		}
	
		if (node.r != null) {
			FindSwapCandidate(node.r, list, weight, number);
		}
		
		if (node != root && node.weight == weight && node.number > number) {
			list.add(node);
		}
		
		
	}
	
	String decode(String words)
	{
		StringBuilder result = new StringBuilder();

		while(words.length() > 0)
		{
		
			AdaptiveNode node = root;

			while (!(node.l == null && node.r == null)) {
				
				String letter = words.substring(0, 1);
				words = words.substring(1);
				
				if (letter.equals("1") && node.l != null ) {
					node = node.l;
				}
				else if (letter.equals("0") && node.r != null) {
					node = node.r;	
				}
				else {
					throw new IllegalArgumentException("Argument is non binary String or String is non-decodable!");
				}
			}
			
			result.append(node.element);

		}

		return result.toString();
	}

	
	public String encode(AdaptiveNode node)
	{

		StringBuilder sb = new StringBuilder();			

		for (AdaptiveNode p = node; p != null; p = p.parent) {	

			if (p.parent != null) 
			{			
				if (p.parent.l == p) {
					sb.append("1");
				}

				else if (p.parent.r == p) {
					sb.append("0");
				}

			} 

		}

		return sb.reverse().toString();
			
	}
	
	
	public String encode(Character symbol)
	{

		AdaptiveNode node = cMap.get(symbol);

		return encode(node);
			
	}
	

}
