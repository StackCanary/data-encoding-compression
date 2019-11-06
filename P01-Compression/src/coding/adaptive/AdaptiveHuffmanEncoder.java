package coding.adaptive;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class AdaptiveHuffmanEncoder {
	
	public AdaptiveHuffmanUpdater huffmanState = new AdaptiveHuffmanUpdater();

	StringBuilder encoded = new StringBuilder();
	
	public void encode(List<Character> words)
	{
		for (Character c : words)
		{
			// First time seen this character
			if (!huffmanState.cMap.containsKey(c))
			{
				String nyt = huffmanState.encode(huffmanState.nytNode);
				String chr = getBinaryRepresentation(c);
				
				encoded.append(nyt);
				encoded.append(chr);
				
			} else
			{
				String chr = huffmanState.encode(c);
				
				encoded.append(chr);
			}
			
			huffmanState.Update(c);
			
		}
	}
	
	public String getEncoded()
	{
		return encoded.toString();
	}
	
	public static String getBinaryRepresentation(char element) 
	{
		BigInteger b = new BigDecimal((int) element).unscaledValue();
		StringBuilder sb = new StringBuilder(b.toString(2)).reverse();
		
		while (sb.length() < AdaptiveNode.Binary_Representation_Size) 
		{
			sb.append("0");
		}
		
		return sb.reverse().toString();
	}
	
}
