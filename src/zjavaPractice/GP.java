package zjavaPractice;

import java.util.Scanner;

public class GP {

	
	private static void GeoPro(double x, double y, double n) {
	
		double r = y / x;
		double o = x * Math.pow(r, n-1);
		int l = (int) Math.floor(o);
		System.out.println(l);
	}
	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		double t = sc.nextDouble();
		
		for (double i = 0; i < t; i++) {
			
			double x = sc.nextDouble();
			double y = sc.nextDouble();
			double n = sc.nextDouble();
			
			GeoPro(x,y,n);	
				
			
		}
		
			
		
	}



}
