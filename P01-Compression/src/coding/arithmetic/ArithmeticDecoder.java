package coding.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.NotDecodableException;
import util.Pair;

public class ArithmeticDecoder {

	Map<Character, Pair<BigDecimal, BigDecimal>> intervals = new HashMap<Character, Pair<BigDecimal, BigDecimal>>();

	Character epsilon;
	
	public ArithmeticDecoder(List<Pair<Character, Double>> probabilities, List<String> tag)
	{
		BigDecimal sum = new BigDecimal(0);
		
		epsilon = probabilities.get(probabilities.size() - 1).a;
		
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
		
		this.tag = tag;
	}

	
	final long bits = 6l;
	final long mask = ((1l << bits) - 1l);
	public long u = mask;
	public long l = 0l;
	public long v = 0l;
	public List<String> tag;
	public List<Character> decoded = new ArrayList<Character>();

	
	public List<Character> getDecoded()
	{
		return decoded;
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

	private void doRescale() throws NotDecodableException
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
	
	
	private void doRescaleA() throws NotDecodableException
	{
		shiftv();
		updatelu();
	}
	
	public void updatelu()
	{
		l <<= 1l;
		u = (u << 1l) | 1l;
		
		l &= mask;
		u &= mask;
	}
	
	private void doRescaleB() throws NotDecodableException
	{
		u = (u ^ (1l << bits - 2l));
		l = (l ^ (1l << bits - 2l));
		v = (v ^ (1l << bits - 2l));
		
		updatelu();

		shiftv();
		
	}
	
	public long strtob(String bit)
	{
		if (bit.equals("0"))
			return 0l;
		
		return 1l;
	}
	
	public void shiftv() throws NotDecodableException
	{
		if (tag.isEmpty())
			throw new NotDecodableException();
		
		String bit = tag.remove(0);
		
		v = mask & ((v << 1) | strtob(bit)); 
	}
	
	public void decode() throws NotDecodableException
	{
		
		if (tag.size() < bits)
			throw new NotDecodableException("Not enough bits received!");
		
		for (int i = 0; i < bits; i++)
		{
			shiftv();
		}
		
		boolean finished = false;
		
		while(true)
		{
			
			for (Entry<Character, Pair<BigDecimal, BigDecimal>> entry : intervals.entrySet())
			{
				Pair<BigDecimal, BigDecimal> interval = entry.getValue();
				
				long d = u - l + 1l;
				
				double pl = interval.a.doubleValue();
				double pu = interval.b.doubleValue();
				
				long _l = (l + (long) Math.floor(d * pl));
				long _u = (l + (long) Math.floor(d * pu) - 1l);
				
				if (_l <= v && v <= _u) 
				{
					decoded.add(entry.getKey());
					l = _l;
					u = _u;
					finished = entry.getKey().equals(epsilon);
					
					break;
				}
					
			}
			
			if (finished)
				break;
			
			doRescale();
		}
		
	}



	
}
