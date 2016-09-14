import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    private static int first = 0;
    private static int second = 0;
    private static int third = 0;
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int arr[] = new int[n];
       
        for(int arr_i=0; arr_i < n; arr_i++){
            arr[arr_i] = in.nextInt();
        }
        
        int arr2[] = Arrays.copyOf(arr,arr.length);
        
        
        if(isSorted(arr)){
            System.out.println("yes");
            return;
        }else{
            helper(arr);
            swap(arr,first,second);
            if(isSorted(arr)){
                System.out.println("yes");
                System.out.println("swap"+" "+(first+1)+" "+(second+1));
                return;
            }
            
            reverse(arr2,first,third);
            if(isSorted(arr2)){
                System.out.println("yes");
                System.out.println("reverse"+" "+(first+1)+" "+(third+1));
                return;
            }
            
        }
        
        System.out.println("no");
    }
    
    
    public static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false; 
            }
        }

        return true; 
    }
    
    
    
    public static void helper(int[] a) {
       int i = 0;
       int j = 0;
       
       for (i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                first = i;
                break;
            }
       }
       
      for(j = a.length-1;j>i;j--){
        if(a[j]<a[j-1]){
           second = j;
           third = j;
           break;
        }  
      }
      
   }
    
    public static void swap(int [] a, int index1, int index2){
        int tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }
    
    public static void reverse(int[] a, int index1, int index2){
        while(index2>index1){
            int tmp = a[index1];
            a[index1] = a[index2];
            a[index2] = tmp;
            index1++;
            index2--;
        }
    }
    
    
}