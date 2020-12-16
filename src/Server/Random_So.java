package Server;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;



public class Random_So{
	
	
	public Random_So() {
	
	}
	
	public Integer[] so() {

		
		Integer[] arr = new Integer[100];
		Integer[] arr_num = new Integer[99];
	    for (int i = 0; i <arr.length-1; i++) {
	        arr_num[i] = i+1;
	        
	       
	      


	    }
	   
	    Collections.shuffle(Arrays.asList(arr_num));
		return arr_num;
	}
	
	
}
