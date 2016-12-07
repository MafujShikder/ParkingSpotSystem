
/**
 * Write a description of class Bicycle here.
 * 
 * @author Gurdev Singh 
 * @version 1.4
 */
public class Bicycle extends Vehicle
{
    

    /**
     * Constructor for objects of class Bicycle
     * @param ownerName name of the bicycle owner
     */
    public Bicycle(String ownerName)
    {
        // initialise instance variables
        super(ownerName);
    }
    
    public Bicycle(String myData,String readnCreate)
    {
        
        super((myData.split(","))[5].trim(),(myData.split(","))[6].trim(),Double.parseDouble((myData.split(","))[7].trim()));
        
    }

    /**
     * Gives the total fee to be charged for parking the Vehicle      * 
     * @return     rate to be charged
     */
    public double getFee()
    {
       return 0.00; 
    }

    /**
     * Gives all the data about the vehicle
     * 
     * @returns comma separated string containing the vehicle data
     */
    public String saveData()
    {
        return "B"+","+getOwner()+","+getArrivalTime()+","+getChargedRate();
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
     public String toString()
    {
     return super.toString();   
    }
}
