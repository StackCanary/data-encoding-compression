package coding.arithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import util.Pair;

public class ArithmeticOld {

	Map<String, Pair<BigDecimal, BigDecimal>> intervals = new HashMap<String, Pair<BigDecimal, BigDecimal>>();
	
	public ArithmeticOld(HashMap<String, Double> probabilities)
	{
		
		/*
		 * Sum the probabilities or frequency
		 */
		BigDecimal sum = new BigDecimal(probabilities.values().stream().collect(Collectors.summingDouble(Double::doubleValue)).toString());
		
		System.out.println(sum);
		
		/*
		 * Divide the individual probabilities or frequencies by the sum of probabilities or frequencies to 
		 * create the half open intervals.
		 */
		BigDecimal prev = new BigDecimal(0);
		for (Entry<String, Double> entry : probabilities.entrySet())
		{
			BigDecimal prob = new BigDecimal( entry.getValue().toString() );
			BigDecimal prop = prob.divide(sum);

			BigDecimal next = prev.add(prop);
			
			intervals.put(entry.getKey(), new Pair<BigDecimal, BigDecimal>(prev, next));
			prev = next;
		}
		
		System.out.println(intervals);
		
	}

	/*
	 * http://www.drdobbs.com/cpp/data-compression-with-arithmetic-encodin/240169251
	 * https://en.wikipedia.org/wiki/Arithmetic_coding
	 */
	public String encode(List<String> words)
	{
		
		BigDecimal lower = new BigDecimal(0);
		BigDecimal range = new BigDecimal(1);
		
		for (String word : words)
		{

			if (!intervals.containsKey(word))
				throw new IllegalArgumentException("Input not present in source's alphabet!");
			
			Pair<BigDecimal, BigDecimal> interval = intervals.get(word);
			
			BigDecimal l = new BigDecimal(interval.a.toString());
			BigDecimal r = new BigDecimal(interval.b.toString());
			
			lower = (lower.add(range.multiply(l)));
			range = range.multiply(r.subtract(l));
		}
		
		/*
		 * Halve the range and add it to the lower bound stripping of any trailing zeros
		 */
		lower = lower.add(range.divide(new BigDecimal(2)));
		lower = lower.setScale(lower.toString().length(), RoundingMode.HALF_UP).stripTrailingZeros();
		
		return lower.unscaledValue().toString(2);
		
	}
	
	public String decode(String word)
	{
		StringBuilder result = new StringBuilder();
		
		BigInteger b = new BigInteger(word, 2);
		BigDecimal d = new BigDecimal("0." + b.toString(10));
		
		BigDecimal range = new BigDecimal(1);
		BigDecimal lower = new BigDecimal(0);
	
		String symbol = "";
		
		while(!symbol.equals("epsilon"))
		{
			for (Entry<String, Pair<BigDecimal, BigDecimal>> entry : intervals.entrySet())
			{
				Pair<BigDecimal, BigDecimal> interval = entry.getValue();
				
				BigDecimal l = lower.add(interval.a.multiply(range));
				BigDecimal u = lower.add(interval.b.multiply(range));
				
				if (l.compareTo(d) <= 0 && u.compareTo(d) > 0) 
				{
					symbol = entry.getKey();
					
					if (!symbol.equals("epsilon"))
						result.append(symbol);
					
					lower = l;
					range = u.subtract(l);
					
					break;
				}
					
				
			}
		}
		
		return result.toString();
	}
	
	
}
