package coding.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import exceptions.NotDecodableException;
import util.Pair;

public class ArithmeticEncoder {

	Map<Character, Pair<BigDecimal, BigDecimal>> intervals = new HashMap<Character, Pair<BigDecimal, BigDecimal>>();
	List<Pair<Character, Double>> probabilities;
	
	public ArithmeticEncoder(List<Pair<Character, Double>> probabilities)
	{
		this.probabilities = probabilities;
		
		
		BigDecimal sum = new BigDecimal(0);
		
		for (Pair<Character, Double> pair : probabilities)
		{
			sum = sum.add(new BigDecimal(pair.b));
		}
		
		/*
		 * Divide the individual probabilities or frequencies by the sum of probabilities or frequencies to 
		 * create the half open intervals.
		 */
		BigDecimal prev = new BigDecimal(0);
		for (Pair<Character, Double> entry : probabilities)
		{
			BigDecimal prob = new BigDecimal( entry.b.toString() );
			BigDecimal prop = prob.divide(sum, RoundingMode.HALF_EVEN);

			BigDecimal next = prev.add(prop);

			intervals.put(entry.a, new Pair<BigDecimal, BigDecimal>(prev, next));
			prev = next;
		}
		
		
	}

	final long bits = 6l;
	final long mask = ((1l << bits) - 1l);
	public long u = mask;
	public long l = 0l;
	public long s = 0l;
	public StringBuilder tag = new StringBuilder();

	public double entropy()
	{
		 double result = 0.;
		 for (Pair<Character, Double> pair : probabilities)
			 result -= pair.b * Math.log10(pair.b) / Math.log10(2.);

		 return result;
	}
	
	public String getEncoded()
	{
		return tag.toString();
	}
	
	private boolean isCaseA()
	{
		long lmsb = (l >> bits - 1l) & 1l;
		long umsb = (u >> bits - 1l) & 1l;
		
		return (lmsb == umsb);
	}
	
	private boolean isCaseB()
	{
		long lmsb  = (l >> bits - 1l) & 1l;
		long umsb  = (u >> bits - 1l) & 1l;
		
		long l2msb = (l >> bits - 2l) & 1l;
		long u2msb = (u >> bits - 2l) & 1l;

		return (lmsb != umsb) && (l2msb == 1 && u2msb == 0);
	}

	private void doRescale()
	{
		while(isCaseA() || isCaseB())
		{
			if (isCaseA())
			{
				doRescaleA();
			} else
			{
				if(isCaseB())
				{
					doRescaleB();
				}
			}
		}
	}
	
	
	private void doRescaleA()
	{
		
		long lmsb = (l >> bits - 1l) & 1l;

		tag.append(lmsb);
		
		while(s --> 0l)
		{
			if (lmsb == 1l)
				tag.append("0");
			else
				tag.append("1");
		}
		
		s = 0;

		l <<= 1l;
		u = (u << 1l) | 1l;

		l &= mask;
		u &= mask;
		
	}
	
	private void doRescaleB()
	{
		
		u = (u ^ (1l << bits - 2l));
		l = (l ^ (1l << bits - 2l));
		l <<= 1l;
		u = (u << 1l) | 1l;
		
		l &= mask;
		u &= mask;
		
		s++;
		
	}
	
	public void encode(List<Character> words)
	{
		if (words.size() < 1)
			return;
		
		for (Character c : words)
		{
			Pair<BigDecimal, BigDecimal> interval = intervals.get(c);
			
			double pl = interval.a.doubleValue();
			double pu = interval.b.doubleValue();
			
			long d = u - l + 1l;
			u = (l + (long) Math.floor(d * pu) - 1l);
			l = (l + (long) Math.floor(d * pl));
			
			doRescale();
		}
		
		long lmsb = (l >> bits - 1l) & 1l;

		tag.append(lmsb);
		
		while(s --> 0l)
		{
			if (lmsb == 1l)
				tag.append("0");
			else
				tag.append("1");
		}

		for (long i = 0l; i < bits - 1l; i++)
		{
			
			long bit = (l >> (bits - 2l - i)) & 1l;
			
			if (bit == 1l)
				tag.append("1");
			else
				tag.append("0");
		}
		
	}



}
