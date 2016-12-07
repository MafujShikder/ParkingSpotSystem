import java.io.*;
import java.util.ArrayList;
/**
 * Utility class contains methods for accepting String and double inputs form users and displaying statements
 * 
 * @author Gurdev Singh
 * @version 1.0 25-08-2016
 */
import java.util.Scanner;
public class IOSupport
{
    static boolean printHeader = true;
    // instance variables - replace the example below with your own
    static Scanner scan ;
    
    /**
     * Accepts string from user 
     * 
     * @param  display displays statement asking for string input
     * @return string accepted from user 
     */
    public static String getString(String display)
    {
        // put your code here
        System.out.println(display);
        scan = new Scanner(System.in);
        String temp = scan.nextLine().trim();
        
        if(temp.equals(""))
        {
            temp = "empty";
        }
        
        return temp;
    }
    
    /**
     * Accepts double from user 
     * 
     * @param  display displays statement asking for double input
     * @return double accepted from user 
     */
    
    public static double getDouble(String display)
    {
        // put your code here
        System.out.println(display);
        scan = new Scanner(System.in);
        double temp ;
        
        try
        {
            temp = scan.nextDouble();
        }
        catch(Exception e)
        {
            return -1;
        }
        return temp;
    }
    
    /**
     * Displays string to user
     * 
     * @param  text displays statement to user 
     *
     */
    
    public static void printIt(String text)
    {
        System.out.println(text);
    }
    
    /**
     * writes the spot data to a file
     * 
     * @param fileName name of the file to store data to 
     * @param data the actual spot data to write to the file
     * @throws IOException
     */
    public static void saveArrData(String fileName, ArrayList<String> data) throws IOException
    {
        
        String separator = "";
        String toPrint = "";
        String newLine = "";
        PrintWriter pw = new PrintWriter(new FileWriter(fileName,true));
        for(String str : data)
        {
            toPrint += (separator + str);
            separator = ",";
            
        }
        if(printHeader)
        {
            pw.println("SpotId,HourlyRate,Occupied,ArrivalTime,VehicleType,Owner,ArrivalTime,ChargedRate,RegNum");
            printHeader = false;
        }
        pw.println(toPrint);
        pw.close();
    }
    
    /**
     * reads the spot data from the file specified
     * 
     * @param fileName name of the file to retreive data from 
     * @returns an arraylist of type sting containing the data retrieved from the file 
     */
    public static ArrayList<String> readData(String fileName)
    {
        try
       {
        ArrayList<String> data = new ArrayList<String>();
        
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        
        String temp = in.readLine();
        temp = in.readLine();
        while (temp != null)
        {
            data.add(temp);
            temp = in.readLine();
        }
        in.close();
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
        return data;
       }
    
    catch(Exception e)
    {
        IOSupport.printIt("Error: No File Found!");
    }
    return new ArrayList<String>();
   }
}
