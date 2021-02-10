package zjavaPractice;

import java.util.ArrayList;
import java.util.HashSet;

public class ListSet {

	public static void main(String[] args) {
	
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(8);
		al.add(5);
		al.add(4);
		al.add(3);
		al.add(9);
		
		System.out.println(al);
		
		int n = -15; 
		int m = 6; 
		
		int q = n/m;
		System.out.println(q);
		
		  HashSet<String> set=new HashSet<String>();  
          set.add("Ravi");  
          set.add("Vijay");  
          set.add("Arun");  
          set.add("Sumit");  
          System.out.println("An initial list of elements: "+set); 
	}

}
