package coding.huffman;

import java.util.Map;
import java.util.Map.Entry;

import exceptions.NotDecodableException;

import java.util.HashMap;
import java.util.List;

public class Huffman {

	HuffmanNode hNode =  null;
	HashMap<Character, Double> probabilities;

	public Huffman(HashMap<Character, Double> probabilities)
	{
		this.probabilities = probabilities;
		hNode = HuffmanNode.CreateHuffmanNode(probabilities);
	}

	public String encode(List<Character> words)
	{
		return words.stream().map(x -> hNode.encoding_table().get(x)).reduce("", String::concat);
	}
	
	public double averageLength()
	{
		double result = 0.;
		
		Map<Character, String> map = hNode.encoding_table();
		
		for (Entry<Character, String> entry : map.entrySet())
		{
			double p = probabilities.get(entry.getKey());
			
			result += p * entry.getValue().length();
		}
		
		return result;
	}
	
	public double entropy()
	{
		 double result = 0.;
		 for (Entry<Character, Double> pair : probabilities.entrySet())
			 result -= pair.getValue() * Math.log10(pair.getValue()) / Math.log10(2.);

		 return result;
	}
	

	public String decode(String words) throws NotDecodableException
	{
		StringBuilder result = new StringBuilder();
		
		while(words.length() > 0)
		{
		
			HuffmanNode node = hNode;

			while (!(node.l == null && node.r == null)) {
				
				String letter = words.substring(0, 1);
				
				if (words.length() < 1)
					throw new NotDecodableException("Could not reach leaf node");
				
				words = words.substring(1);
				
				if (letter.equals("1") && node.l != null ) {
					node = node.l;
				}
				else if (letter.equals("0") && node.r != null) {
					node = node.r;	
				}
				else {
					throw new NotDecodableException("Argument is non binary String!");
				}
				
			}
			
			result.append(node.element);

		}

		return result.toString();
	}

}