package util;

import java.util.List;


public class Random<T> {
	
	public Random(){
		
	}

	public T getRandomElement(List<T> list) {
		int index =  new java.util.Random().nextInt(list.size());
		return list.get(index); 
	}

}
