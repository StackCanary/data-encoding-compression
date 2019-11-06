package coding.adaptive;

import java.util.List;

import coding.adaptive.AdaptiveNode.NodeType;
import exceptions.NotDecodableException;

public class AdaptiveHuffmanDecoder {
	
	public AdaptiveHuffmanUpdater huffmanState = new AdaptiveHuffmanUpdater();
	StringBuilder result = new StringBuilder();
	
	public void decode(List<String> words) throws NotDecodableException 
	{
		
		while(words.size() > 0)
		{
			AdaptiveNode node = huffmanState.root;

			while (!(node.l == null && node.r == null)) {
				
				String letter = words.remove(0);
				
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
			
			if (node.type == NodeType.NotYetTransmitted) {

				StringBuilder bdigits = new StringBuilder();
				
				if (words.size() < AdaptiveNode.Binary_Representation_Size)
					throw new NotDecodableException("Not enough bits");
				
				for (int i = 0; i < AdaptiveNode.Binary_Representation_Size; i++)
					bdigits.append(words.remove(0));
				
				Character chr = getCharacterFromBinary(bdigits.toString());
				
				result.append(chr);
				huffmanState.Update(chr);
			}
			else {
				result.append(node.element);
				huffmanState.Update(node.element);
			}
			
		}
		
	}
	
	public String getDecoded()
	{
		return result.toString();
	}
	
	public static Character getCharacterFromBinary(String number) 
	{
		int result = 0;
		
		for (String c : number.split(""))
		{
			int i = 0;
			if (c.equals("0"))
				i = 0;
			else 
				i = 1;
			result = (result << 1) | i;
		}
		
		return (char) result;
	}
	
	
}
