import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ericyao
 * on 7/25/16.
 */

/*
    Instruction:
    As you can see in the main function, the input file will be first argument when you run the program
    eg: If your input.txt file is in the same directory with this java file, please use
        "javac AddTwoRomanNumerals.java" to compile this file then use
        "java AddTwoRomanNumerals input.txt" to run the file



    The algorithm has just five steps:
    1. Substitute for any subtractives in both values; that is; "uncompact" the Roman values.
    2.Put the two values together—catenate them.
    3.Sort the symbols in order from left-to-right with the "largest" symbols on the left.
    4.Starting with the right end, combine groups of the same symbols that can make a "larger" one and substitute the single larger one.
    5.Compact the result by substituting subtractives where possible.


    reference: http://turner.faculty.swau.edu/mathematics/materialslibrary/roman/
*/



public class AddTwoRomanNumerals{

    public AddTwoRomanNumerals(){

    }

    public List<String> readFile(String pathName) throws IOException{
        List<String> list = new ArrayList<>();
        try{
            File file = new File(pathName);
            if(file.isFile()&&file.exists()){
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(reader);
                String lineTXT = "";
                while((lineTXT = br.readLine())!=null){
                    list.add(lineTXT.toString().trim());
                }
                reader.close();
            }else{
                System.out.println("Can not find the file");
            }
        }catch (Exception e){
            System.out.println("Read File exception");
            e.printStackTrace();
        }
        return list;
    }

    /*
    This method is used to validate the input roman numeral
    Here I used Regular expression to validate the input
     */
    public static boolean validate(String input){
        Matcher m = Pattern.compile("\\bM{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\b").matcher(input);
        return m.matches();
    }

    /*
    * This method is used to substitute the subtractive
    */
    public static String removeSubtractive(String input){
        //This part is used to correspond to remove subtractives
        Map<String,String> map = new HashMap<String,String>();
        map.put("IV","IIII");
        map.put("IX","VIIII");
        map.put("XL","XXXX");
        map.put("XC","LXXXX");
        map.put("CD","CCCC");
        map.put("CM","DCCCC");
        int len = input.length();
        StringBuffer sb = new StringBuffer();
        StringBuffer tmp = new StringBuffer();
        char[] c1 = input.toCharArray();
        int index;                                   // used to index the last element
        for(index = 0; index<len-1;index++){

            sb.append(c1[index]);
            sb.append(c1[index+1]);
            if(map.containsKey(sb.toString())){
                tmp.append(map.get(sb.toString()));
                index++;
            }else{
                tmp.append(c1[index]);
            }

            sb.delete(0,2);
        }

        //if the last element has not been substractive, append to the StringBuff
        if(index==input.length()-1){
            tmp.append(c1[index]);
        }

        return tmp.toString();
    }

    //This method is to concat and sort the two Strings
    public static String ConcatString(String s1, String s2){
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int i = 0;
        int j = 0;
        StringBuffer sb = new StringBuffer();


        while(i<s1.length()&&j<s2.length()){
            if(CompareChar(c1[i],c2[j])==1){
                sb.append(c1[i]);
                i++;
            }else{
                sb.append(c2[j]);
                j++;
            }
        }

        if(i==s1.length()){
            while(j<s2.length()) {
                sb.append(c2[j]);
                j++;
            }
        }else{
            while(i<s1.length()) {
                sb.append(c1[i]);
                i++;
            }
        }

        return sb.toString();
    }

    //This method is used to compare two character,use to sort the string
    public static int CompareChar(char c1, char c2){
        if(c1=='M'){
            return 1;
        }

        if(c1=='D'){
            if(c2=='M') {
                return -1;
            }
            else return 1;
        }

        if(c1=='C'){
            if(c2=='M'||c2=='D') {
                return -1;
            }
            else return 1;
        }

        if(c1=='L'){
            if(c2=='M'||c2=='D'||c2=='C') {
                return -1;
            }
            else return 1;
        }
        if(c1=='X'){
            if(c2=='L'||c2=='C'||c2=='D'||c2=='M') {
                return -1;
            }
            else return 1;
        }
        if(c1=='V'){
            if(c2=='L'||c2=='C'||c2=='D'||c2=='M'||c2=='X') {
                return -1;
            }
            else return 1;
        }
        if(c1=='I'){
            if(c2=='L'||c2=='C'||c2=='D'||c2=='M'||c2=='X'||c2=='V') {
                return -1;
            }
            else return 1;
        }
        return 0;
    }

    public static String combineString(String input){
        StringBuffer sb = new StringBuffer();

        while(input.contains("IIIII")){
            int tmp = input.indexOf("IIIII");
            sb.append(input.substring(0,tmp));
            sb.append('V');
            sb.append(input.substring(tmp+5));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        while(input.contains("VV")){
            int tmp = input.indexOf("VV");
            sb.append(input.substring(0,tmp));
            sb.append('X');
            sb.append(input.substring(tmp+2));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        while(input.contains("XXXXX")){
            int tmp = input.indexOf("XXXXX");
            sb.append(input.substring(0,tmp));
            sb.append('L');
            sb.append(input.substring(tmp+5));
            input = sb.toString();
            sb.delete(0,input.length());

        }

        while(input.contains("LL")){
            int tmp = input.indexOf("LL");
            sb.append(input.substring(0,tmp));
            sb.append('C');
            sb.append(input.substring(tmp+2));
            input = sb.toString();
            sb.delete(0,input.length());
        }


        while(input.contains("CCCCC")){
            int tmp = input.indexOf("CCCCC");
            sb.append(input.substring(0,tmp));
            sb.append('D');
            sb.append(input.substring(tmp+5));
            input = new String(sb.toString());
            sb.delete(0,input.length());

        }

        while(input.contains("DD")){
            int tmp = input.indexOf("DD");
            sb.append(input.substring(0,tmp));
            sb.append('M');
            sb.append(input.substring(tmp+2));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        return input;
    }


    public static String subtractive(String input){
        StringBuffer sb = new StringBuffer();

        while(input.contains("IIII")){
            int tmp = input.indexOf("IIII");
            sb.append(input.substring(0,tmp));
            sb.append("IV");
            sb.append(input.substring(tmp+4));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        while(input.contains("VIIII")){
            int tmp = input.indexOf("VIIII");
            sb.append(input.substring(0,tmp));
            sb.append("IX");
            sb.append(input.substring(tmp+5));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        while(input.contains("XXXX")){
            int tmp = input.indexOf("XXXX");
            sb.append(input.substring(0,tmp));
            sb.append("XL");
            sb.append(input.substring(tmp+4));
            input = sb.toString();
            sb.delete(0,input.length());

        }

        while(input.contains("LXXXX")){
            int tmp = input.indexOf("LXXXX");
            sb.append(input.substring(0,tmp));
            sb.append("XC");
            sb.append(input.substring(tmp+5));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        while(input.contains("CCCC")){
            int tmp = input.indexOf("CCCC");
            sb.append(input.substring(0,tmp));
            sb.append("CD");
            sb.append(input.substring(tmp+4));
            input = new String(sb.toString());
            sb.delete(0,input.length());
        }

        while(input.contains("DCCCC")){
            int tmp = input.indexOf("DCCCC");
            sb.append(input.substring(0,tmp));
            sb.append("CM");
            sb.append(input.substring(tmp+5));
            input = sb.toString();
            sb.delete(0,input.length());
        }

        return input;
    }

    public static void addNumerals(String firstNum, String secondNum){
        if(!validate(firstNum)){
            System.out.println("Sorry, your first input is invalid");
            return;
        }

        if(!validate(secondNum)){
            System.out.println("Sorry, your second input is invalid");
        }

        String s1 = removeSubtractive(firstNum);
        String s2 = removeSubtractive(secondNum);
        String concatResult = ConcatString(s1,s2);
        String combineResult = combineString(concatResult);
        System.out.println(firstNum+" + "+secondNum+" = "+subtractive(combineResult));

    }



    public  static void main(String[] args) {
        AddTwoRomanNumerals a = new AddTwoRomanNumerals();
        List<String> list = new ArrayList<>();

        if(args.length!=0) {
            try {
                list = a.readFile(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (String tmp : list) {
                String[] s = tmp.split("\\s+");
                a.addNumerals(s[0], s[1]);
            }
        }else{
            System.out.println("Please input your file name");
            return;
        }



    }
}


