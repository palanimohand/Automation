package zjavaPractice;

import java.util.ArrayList;
import java.util.Collections;

public class GeeksQ
{
    // Function to insert element
    public static void insert(ArrayList<Character> clist, char c)
    {
    	
    	clist.add(c);

    }
    
    // Function to count frequency of element
    public static void freq(ArrayList<Character> clist, char c)
    {
    	boolean b = clist.contains(c);
    	
        if(b==true){
        	System.out.println(Collections.frequency(clist, c));	
        }
        else{
        	System.out.println("Not Present");	
        }
        
    }
}

