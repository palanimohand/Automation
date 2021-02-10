package zjavaPractice;

import java.util.Scanner;

public class GFG {
    
    // Driver code
	public static void main (String[] args) {
	    
	    // Taking input using scanner class
		Scanner sc = new Scanner(System.in);
		
		int testcase = sc.nextInt();
		
		while(testcase-- > 0){
		    int rows = sc.nextInt();
            int cols = sc.nextInt();
		    int a[][] = new int[rows][cols];
		    
		    for(int i = 0;i<rows;i++){
		        for(int j = 0;j<cols;j++){
		            a[i][j] = sc.nextInt();
		        }
		    }
		    
		    Geeks obj = new Geeks();
		    obj.interchange(a, rows, cols);
		}
	}
}
