import AnalyticalTool.AnalyticalTool;

import java.util.Scanner;

public class Main {
    private static String inputString = "7 C 1.1 8.15.1 P 15.10.2012 89 C 1 10.1 P 01.12.2012 65 C 1.1 5.5.1  " +
            "P 01.11.2012 117 D 1.1 8 P 01.01.2012-01.12.2012 C 3 10.2 N 02.10.2012 100 " +
            "D 1 * P 8.10.2012-20.11.2012 D 3 10 P 01.12.2012";
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (inputString.length() != 0){

            System.out.println("Input: " + inputString);

            try{
                System.out.println("Result: " + AnalyticalTool.makeAnalyse(inputString) );
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            inputString = sc.nextLine();
        }
    }
}
