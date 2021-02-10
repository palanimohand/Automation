package zjavaPractice;

import java.util.*;
import java.lang.*;
import java.io.*;

public class CloseNum {

	public static void main(String[] args) {
		int t=0;
		
		Scanner in = new Scanner(System.in);
		t=in.nextInt();
		
		for (int z=0; z<t; z++){
		
		int u=in.nextInt();
		int v=in.nextInt();
		
		closestNumber(u,v);
		
		}
		
	}
	
	public static void closestNumber(int m,int n) {

		int x =0,y=0;
		 
		 for (int i=0; i<1000; i++){
		        x=m+i;
		        y=m-i;
		        if((x%n==0)&&(y%n==0)){
		        
		        	if(Math.abs(x)>Math.abs(y)){
		                System.out.println(x);
		            }
		        	else
		            {
		                System.out.println(y);
		            }
		        	
		        	break;
		        	
		        }
		        else if ((x%n==0)){
		        
		            System.out.println(x);
		            break;				
		            
		        }
		        else if((y%n==0)){
		       
		            System.out.println(y);
		            break;
		            
		        }
		    }
	}
}
	
/*Dataset:
1)When 2 nearest divisble number is found at equal intervals
2)When zero is the nearest divisble number
3)When the divisor is negative*/


