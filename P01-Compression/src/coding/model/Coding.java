package coding.model;

import java.util.List;

import exceptions.NotDecodableException;

public interface Coding {
	
	public String encode(List<Character> words);
	public String decode(String words) throws NotDecodableException;

}
