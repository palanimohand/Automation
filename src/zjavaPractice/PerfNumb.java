package zjavaPractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PerfNumb {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 long t = Long.parseLong(br.readLine()); 
		
						
		for (int z=0; z<t; z++){
			long x =Long.parseLong(br.readLine());
			perfnum(x);
		}
	}
	
	
		public static void perfnum(long n){
			

			
			long c = 0;
			
		
			
			for (long i=1; i<Math.sqrt(n); i++){
				
				if (n%i==0){
					
					c+=i;
					
				}
				
			}
			
			if(c==n){
			
				System.out.println("1");
				
			}else{
				System.out.println("0");
			}
				
			
			
		}
		

	}


