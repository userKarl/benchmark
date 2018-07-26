package com.zd.service;

public class BenchMark {

	public static void main(String[] args) {
		int n=10000;
        long t1 = System.nanoTime();
          
        
        for(int i=0; i<n; i++){
            aaa((float)i);
        }  
          
        long t2 = System.nanoTime();
          
        System.out.println("java time: " + (t2-t1)/1e6 + "ms");
    }  
      
    static void aaa(float i) {
        float a = i + 1;
        float b = 2.3f;
        String s = "abcdefkkbghisdfdfdsfds";
          
        if(a > b){
            ++a;
        }else{
            b = b + 1;
        }  
  
        if(a == b){
            b = b + 1;
        }  
          
        float c = (float)(a * b  + a / b - Math.pow(a, 2));
          
        String d = s.substring(0, s.indexOf("kkb")) + String.valueOf(c);
    }
}
