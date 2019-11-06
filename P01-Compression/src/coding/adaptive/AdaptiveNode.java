package coding.adaptive;

import java.math.BigDecimal;
import java.math.BigInteger;

public class AdaptiveNode {
	
	static final int Binary_Representation_Size = 8;
	
	enum NodeType {
		Element,
		NotYetTransmitted
	};
	

	int number;
	int weight;
	
	AdaptiveNode parent;
	AdaptiveNode l;
	AdaptiveNode r;
	NodeType type;
	
	Character element;
	
	AdaptiveNode()
	{
		
	}
	
	public AdaptiveNode(AdaptiveNode toBeCopied) {
		this.number = toBeCopied.number;
		this.weight = toBeCopied.weight;
		this.parent = toBeCopied.parent;
		this.l = toBeCopied.l;
		this.r = toBeCopied.r;
		this.type = toBeCopied.type;
		this.element = toBeCopied.element;
	}
	
	public void copy(AdaptiveNode toBeCopied) {
		this.number = toBeCopied.number;
		this.weight = toBeCopied.weight;
		this.parent = toBeCopied.parent;
		this.l = toBeCopied.l;
		this.r = toBeCopied.r;
		this.type = toBeCopied.type;
		this.element = toBeCopied.element;
	}
	
	
	AdaptiveNode(NodeType type, int number, int weight)
	{
		this.type = type;
		this.number = number;
		this.weight = weight;
	}
	
	AdaptiveNode(NodeType type, int number, int weight, Character e)
	{
		this.type = type;
		this.number = number;
		this.weight = weight;
		this.element = e;
	}
	
	
	int getWeight()
	{
		return weight;
	}
	
	int getNumber()
	{
		return number;
	}
	
	/*
	 *  Copied some stackoverflow 
	 */
	public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
	    if(l!=null) {
	        r.toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
	    }
	    sb.append(prefix).append(isTail ? "└─── " : "┌─── ").append(valueA()).append("\n");
	    if(l!=null) {
	        l.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
	    }
	    return sb;
	}

	/*
	 * Copied some stackoverflow 
	 */
	@Override
	public String toString() {
	    return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
	}
	
	public String valueA() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		sb.append("W:" + weight + ",");
		sb.append("I:" + number);
		
		if (element != null)
		{
			sb.append(",");
			sb.append(element);
		} else
		{
			if (type == NodeType.NotYetTransmitted)
			{
				sb.append(",NYT");
			}
			
		}
		
			
		sb.append(")");

		return sb.toString();
	}
	
	
	
	
}
