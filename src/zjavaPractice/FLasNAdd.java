package zjavaPractice;

import java.math.BigInteger;
import java.util.Scanner;

public class FLasNAdd {

    static void factorial(int n) 
    { 
        int res[] = new int[214748364]; 
        int xes[] = new int[214748364];

        res[0] = 1; 
        xes[0] = 0; 
        int res_size = 1; 
        int xes_size = 1;

        for (int x = 1; x <= n; x++) 
            res_size = multiply(xes, res, res_size, xes_size, x); 
   
        for (int i = 1; i >= 0; i--) 
        	if(res[1]==0){
        		System.out.print(res[0]);
        		break;
        	}
        	else {
        		System.out.print(res[i]);	
        	} 
    } 
      
    static int multiply(int xes[], int res[], int res_size, int xes_size, int x) 
    { 
        int carry = 0; 
        int carryt =0;
  

        for (int i = 0; i < res_size; i++) 
        { 
            int prod =  (x==2)?1:(res[i] + xes[i] + carry);
            xes[i] = res[i]%10;
            carryt = res[i]/10;
            
            res[i] = prod % 10; 
            carry = prod/10; 
        } 

        while (carry!=0) 
        { 
            res[res_size] = carry % 10; 
            carry = carry / 10; 
            res_size++; 
            xes_size++;
        } 
        return res_size; 
    } 

    public static void main(String args[]) 
    { 
    	Scanner sc = new Scanner(System.in);
    	int t = sc.nextInt();
    	for (int i = 0; i < t; i++) {
    		int n = sc.nextInt();
    		factorial(n); 
		}
        
    }
}
