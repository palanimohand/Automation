package zjavaPractice;

public class Geeks {

	  static void interchange(int a[][],int r, int c){
	        
	    	int temp = 0;
	    	  for(int i = 0;i<r;i++){
	            for(int j = 0;j<c;j++){
	            	while(j==0){
	            		temp = a[i][j];
	            		a[i][j]=a[i][c-1];
	            		a[i][c-1]=temp;
	            		break;
	            	            	}
	                System.out.print(a[i][j] + " ");
	            }
	            System.out.println();
	        } 
	    }
	
}
