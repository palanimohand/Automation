package zjavaPractice;

import java.lang.*;
import java.math.BigInteger;
import java.util.Scanner;
import java.io.*;

public class KthDigit
 {
private static void FLastTwo(long n) {		
		
		BigInteger r = BigInteger.ZERO;
		BigInteger p = BigInteger.ONE;

		
		for (int i = 1; i <= n; i++) {
			
			BigInteger s = (i==2)?BigInteger.ONE:(r.add(p));
			r = p;
			p = s;
			
		}
		
		if (p.compareTo(BigInteger.valueOf(100))>=1) {
			BigInteger m= p.mod(BigInteger.valueOf(100));
			System.out.println(m);
		}
		else {
		System.out.println(p);

	}
		
	}

public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		
		
		for (int i = 0; i < t; i++) {
			long n = sc.nextLong();			
			FLastTwo(n);
			
		}
		

}

}