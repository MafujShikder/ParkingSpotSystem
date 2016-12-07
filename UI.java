 /**
 * User Interface for Vehicle Parking System
 * 
 * @author Gurdev Singh
 * @version 1.0 25-08-2016
 */

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
public class UI
{
    
    private VehiclePark vehiclePark; 
    private DateTimeFormatter formatter; 
    
    /**
     * Constructor for UI
     */
    public UI(VehiclePark vehpar)throws Exception,IOException
    {
        vehiclePark = vehpar;
        formatter = DateTimeFormatter.ofPattern("hh:mm a");
    }
    
    
   
    /**
     * Takes descision on the basis of user's choice 
     */
    public void run()throws IOException
    {   
        int getOut=0;
        while(getOut!=-1)
        {
            switch(menu())
            {
                case "1":addSpot();break;
                case "2":deleteSpot();break;
                case "3":listOccupiedSpot();break;
                case "4":parkVehicle();break;
                case "5":collectVehicle();break;
                case "6":findVehicle();break;
                case "7":moveVehicle();break;
                case "8":getOut=-1;IOSupport.printIt("Bye :)");break;
                case "9":showAllSpots();break;
                default:IOSupport.printIt("\n***\nPlease enter a valid choice!\n***\n");
            }   
        }
    }
    
    /**
     * Displays menu and accepts user's choice
     */
    public String menu()
    {
        IOSupport.printIt("-----------------------");
        IOSupport.printIt("1)Add Spot");
        IOSupport.printIt("2)Delete Spot");
        IOSupport.printIt("3)List Occupied Spots");
        IOSupport.printIt("4)Park Vehicle");
        IOSupport.printIt("5)Collect Vehicle");
        IOSupport.printIt("6)Find Vehicle");
        IOSupport.printIt("7)Move Vehicle");
        IOSupport.printIt("8)Exit");
        IOSupport.printIt("-----------------------");
        return IOSupport.getString("What would you like to do?").trim();
    }
    
    /**
     * Adding a Parking Spot
     */
    public void addSpot()
    {
        String id = IOSupport.getString("Enter id of spot:");
        boolean isSpotDuplicate = false;
        if (id.equals("empty"))
        {
            IOSupport.printIt("Error: do not leave the id blank!");
        }
        else
        {
            
            for(Spot aSpot : vehiclePark.getAllSpots())
            {
                if(aSpot.getSpotId().equalsIgnoreCase(id))
                {
                    isSpotDuplicate = true;
                    IOSupport.printIt("Error: spot "+id+" exists!");
                    break;
                }
            }
            
            if(!isSpotDuplicate)
            {
                double hourlyRate = IOSupport.getDouble("Enter hourly rate: $");
                if(hourlyRate < 0)
                {
                    IOSupport.printIt("Error: please enter a valid amount!");
                }
                else
                {
                    Spot aSpot = new Spot(id,hourlyRate);
                    vehiclePark.addParkingSpot(aSpot);
                    showAllSpots();
                }
            }
            
        }
    }
    
    /**
     * Deleting a Parking Spot
     */
    public void deleteSpot()
    {
        String tempId = IOSupport.getString("Enter SpotId:");
        
        if(vehiclePark.removeParkingSpot(tempId) )
        {   
            IOSupport.printIt("Success: spot deleted!");
        }
        else
        {
            IOSupport.printIt("Error: spot not found or is occupied!");
        }
            
    }
        
    /**
     * Displays all occupied  spots
     */
    public void listOccupiedSpot()
    {
        if(vehiclePark.getOccupiedSpots().isEmpty())
        {
           IOSupport.printIt("Alert: No Spots Occupied!");
        }
        else
        {
            IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s","Spot Id","Arrival Time","Arrival Date","Time In","Registration#", "Fee/Hr"));
            
            for(Spot aSpot : vehiclePark.getOccupiedSpots())
            { Vehicle v = aSpot.getVehicle();
                if(aSpot.getVehicle()!=null && aSpot.getIsOccupied() && v instanceof Car)
                {   //checking if it is a car
                    
                        IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.",((Car)aSpot.getVehicle()).getRegistrationNumber(),"$"+aSpot.getParkingCharges()));
                }
                else if(aSpot.getVehicle()!=null && aSpot.getIsOccupied() && v instanceof Bicycle)
                {   //checking if it is a car
                    
                        IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","-Bicycle-","$"+aSpot.getParkingCharges()));
                }
            }
            
        }
        
    }
    
    /**
     * Creates a vehicle object as per user input and
     * parks it at existing parking spot
     */
    public void parkVehicle()
    {
        ArrayList<Spot> receivedAvailSpots = new ArrayList<Spot>();
        
        String choice = IOSupport.getString("Press B for Bicycle and C for Car");
        switch (choice.toUpperCase())
        { case "C":
            {
                String regNum = IOSupport.getString("Enter registration number of the vehicle:");
                if(!regNum.equals("empty"))
                {
                    String ownerName =  IOSupport.getString("Enter name of owner:");
                    if(!ownerName.equals("empty"))
                    {
                        Vehicle vehicle = new Car(regNum , ownerName);
                        receivedAvailSpots = vehiclePark.getAvailableSpots();
        
                        if(receivedAvailSpots.isEmpty())
                        {
                            IOSupport.printIt("Alert: No spots available!");
                        }
                        else
                        {
                            String strSpots = "";
                            IOSupport.printIt("List of available spots:\n");
                            for(Spot aSpot : receivedAvailSpots)
                            {
               
                                strSpots +=  aSpot.getSpotId()+": $"+aSpot.getParkingCharges()+" ;\n";
                            }
                            IOSupport.printIt(strSpots.substring(0,strSpots.length()-1));
                    
                    
                            //choosing parking spot
                            String spotId = IOSupport.getString("choose a parking spot:\n");
                            if(vehiclePark.findSpot(spotId).getSpotId().equals(spotId) && !vehiclePark.findSpot(spotId).getIsOccupied())
                            {
                        
                                if(vehicle.getChargedRate()<0)
                                {
                                    vehicle.setChargedRate(vehiclePark.findSpot(spotId).getParkingCharges());
                                }
                                vehiclePark.findSpot(spotId).addVehicle(vehicle); 
                            
                                IOSupport.printIt("**********\nSuccess: Vehicle Parked Successfully \n**********\n");
                                showAllSpots();
                            }  
                            else if (vehiclePark.findSpot(spotId).getSpotId().equals(spotId) && vehiclePark.findSpot(spotId).getIsOccupied())
                            {
                                IOSupport.printIt("Error: This spot is occupied!");
                            }
                            else
                            {
                                IOSupport.printIt("Error: Enter a valid spot!");
                            }
                        }
                    }
                    else
                    {
                        IOSupport.printIt("Error: Owner's Name cannot be left Empty! ");
                    }   
                }    
             
        
                else
                {
                    IOSupport.printIt("Error: Registration Number cannot be left Empty! ");
                }
    
           }break;
           
           case "B":
           {
                {
                    String ownerName =  IOSupport.getString("Enter name of owner:");
                    if(!ownerName.equals("empty"))
                    {
                        Vehicle vehicle = new Bicycle(ownerName);
                        receivedAvailSpots = vehiclePark.getAvailableSpots();
        
                        if(receivedAvailSpots.isEmpty())
                        {
                            IOSupport.printIt("Alert: No spots available!");
                        }
                        else
                        {
                            String strSpots = "";
                            IOSupport.printIt("List of available spots:\n");
                            for(Spot aSpot : receivedAvailSpots)
                            {
               
                                strSpots +=  aSpot.getSpotId()+": $"+0.00+" ;\n";
                            }
                            IOSupport.printIt(strSpots.substring(0,strSpots.length()-1));
                    
                    
                            //choosing parking spot
                            String spotId = IOSupport.getString("choose a parking spot:\n");
                            if(vehiclePark.findSpot(spotId).getSpotId().equals(spotId) && !vehiclePark.findSpot(spotId).getIsOccupied())
                            {
                        
                                vehicle.setChargedRate(0.00);
                                vehiclePark.findSpot(spotId).addVehicle(vehicle); 
                            
                                IOSupport.printIt("**********\nSuccess: Vehicle Parked Successfully \n**********\n");
                                showAllSpots();
                            }  
                            else if (vehiclePark.findSpot(spotId).getSpotId().equals(spotId) && vehiclePark.findSpot(spotId).getIsOccupied())
                            {
                                IOSupport.printIt("Error: This spot is occupied!");
                            }
                            else
                            {
                                IOSupport.printIt("Error: Enter a valid spot!");
                            }
                        }
                    }
                    else
                    {
                        IOSupport.printIt("Error: Owner's Name cannot be left Empty! ");
                    }   
                }    
             
        
                
    
           }break;
           default:
           {
               IOSupport.printIt("Alert: Please make a valid choice!");
           }
        }   
    }
    
    /**
     * Removes vehicle from parking spot and displays the fee
     * @returns vehicle object removed from the parking spot
     */
    public Vehicle collectVehicle()
    { 
       Vehicle c = new Car("-1","-1"); 
       String ownerName = IOSupport.getString("Enter your Name:");
       if(!ownerName.equals("empty"))
       {
           if(!findVehicle(ownerName)==false)
           {
               String spotId = IOSupport.getString("Select a parking spot:");
               if(vehiclePark.findSpot(spotId)!=null && vehiclePark.findSpot(spotId).getIsOccupied() && vehiclePark.findSpot(spotId).getVehicle().getOwner().equals(ownerName))
               {    //checking if it is a car
                   if(vehiclePark.findSpot(spotId).getVehicle() instanceof Car)
                   { 
                       
                           //IOSupport.printIt(aSpot.getVehicle().getOwner()+" : "+aSpot.getVehicle().getTimeIn()+"minutes and charge: "+aSpot.getVehicle().getFee());
                           IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-30s%-20s","Spot Id","Registration #","Owner","Time In","Arrival Date n Time","Total Charge"));
                           IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-30s%-20s",vehiclePark.findSpot(spotId).getSpotId(),((Car)vehiclePark.findSpot(spotId).getVehicle()).getRegistrationNumber(),vehiclePark.findSpot(spotId).getVehicle().getOwner(),vehiclePark.findSpot(spotId).getVehicle().getTimeIn()+" min.",vehiclePark.findSpot(spotId).getArrivalTime(),"$ "+vehiclePark.findSpot(spotId).getVehicle().getFee()));
                           c = vehiclePark.findSpot(spotId).removeVehicle();
                   
                           IOSupport.printIt("Success: Vehicle Removed!");
                           return c;
                        
                       
                    }
                    else if(vehiclePark.findSpot(spotId).getVehicle() instanceof Bicycle)
                   { 
                       
                           //IOSupport.printIt(aSpot.getVehicle().getOwner()+" : "+aSpot.getVehicle().getTimeIn()+"minutes and charge: "+aSpot.getVehicle().getFee());
                           IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-30s%-20s","Spot Id","Registration #","Owner","Time In","Arrival Date n Time","Total Charge"));
                           IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-30s%-20s",vehiclePark.findSpot(spotId).getSpotId(),"-Bicycle-",vehiclePark.findSpot(spotId).getVehicle().getOwner(),vehiclePark.findSpot(spotId).getVehicle().getTimeIn()+" min.",vehiclePark.findSpot(spotId).getArrivalTime(),"$ "+vehiclePark.findSpot(spotId).getVehicle().getFee()));
                           c = vehiclePark.findSpot(spotId).removeVehicle();
                   
                           IOSupport.printIt("Success: Vehicle Removed!");
                           return c;
                        
                       
                    }
                }
               else
               {
                    IOSupport.printIt("Error: No such parking spot for vehicle owner "+ownerName+" found!");
               }
           }
           
       }
       else
       {
           IOSupport.printIt("Error: Owner's name cannot be left empty!");
       }
       return null;
    }
    
    /**
     * Removes vehicle from spot for a specific spot and displays fee 
     * @param id spot id of the vehicle to be removed
     * @param ownerName name of the owner of the vehicle to be removed
     * @returns the vehicle object removed from the parking spot 
     */
    public Vehicle collectVehicleById(String Id,String ownerName)
    { 
       
       String spotId = Id;
       for(Spot aSpot: vehiclePark.getOccupiedSpots())
       {
            //checking if it is a car
          {
           if(aSpot.getVehicle() instanceof Car)   
           {
               if(((Car)aSpot.getVehicle()).getOwner().equals(ownerName) && (aSpot.getSpotId()).equals(spotId))
               {
                   Vehicle c = new Car("-1","-1"); 
                   IOSupport.printIt(aSpot.getVehicle().getOwner()+" : "+aSpot.getVehicle().getTimeIn()+"minutes and charge: "+aSpot.getVehicle().getFee());
                   c = aSpot.removeVehicle();
                   IOSupport.printIt("Success: Vehicle removed!");
                   IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s","Spot ID","Arrival Time","Arrival Date","Time IN","Total Fee"));
                
                   IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","$"+aSpot.getVehicle().getFee()));
                   return c;
                }
                else
                {
                    IOSupport.printIt("Error: No vehicle belonging to"+ownerName+" found!");
                }
            }
            else if(aSpot.getVehicle() instanceof Bicycle)
            {
                 if(((Bicycle)aSpot.getVehicle()).getOwner().equals(ownerName) && (aSpot.getSpotId()).equals(spotId))
                 {
                     Vehicle b = new Bicycle("-1"); 
                     IOSupport.printIt(aSpot.getVehicle().getOwner()+" : "+aSpot.getVehicle().getTimeIn()+"minutes and charge: "+aSpot.getVehicle().getFee());
                     b = aSpot.removeVehicle();
                     IOSupport.printIt("Success: Vehicle removed!");
                     IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s","Spot ID","Arrival Time","Arrival Date","Time IN","Total Fee"));
                
                     IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","$"+aSpot.getVehicle().getFee()));
                     return b;
                 }
                 else
                 {
                      IOSupport.printIt("Error: No vehicle belonging to"+ownerName+" found!");
                 }
            }
          }
       }
       
       return null;
    }
    
    /**
     * Removes vehicle from parking spot and displays the fee (for movevehicle())
     * @param aRegNum registration number of the vehicle to be removed
     * @returns the vehicle object removed from the parking spot 
     */
    public Vehicle collectVehicle(String aOwnerName)
    { 
       
       String ownerName = aOwnerName;
       for(Spot aSpot: vehiclePark.getOccupiedSpots())
       {
            //checking if it is a car
          {
           if(aSpot.getVehicle() instanceof Car)   
           {
               if(((Car)aSpot.getVehicle()).getOwner().equals(ownerName))
               {
                   Vehicle c = new Car("-1","-1"); 
                   IOSupport.printIt(aSpot.getVehicle().getOwner()+" : "+aSpot.getVehicle().getTimeIn()+"minutes and charge: "+aSpot.getVehicle().getFee());
                   c = aSpot.removeVehicle();
                   IOSupport.printIt("Success: Vehicle removed!");
                   IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s","Spot ID","Arrival Time","Arrival Date","Time IN","Total Fee"));
                
                   IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","$"+aSpot.getVehicle().getFee()));
                   return c;
                }
                else
                {
                    IOSupport.printIt("Error: No vehicle belonging to"+ownerName+" found!");
                }
            }
            else if(aSpot.getVehicle() instanceof Bicycle)
            {
                 if(((Bicycle)aSpot.getVehicle()).getOwner().equals(ownerName))
                 {
                     Vehicle b = new Bicycle("-1"); 
                     IOSupport.printIt(aSpot.getVehicle().getOwner()+" : "+aSpot.getVehicle().getTimeIn()+"minutes and charge: "+aSpot.getVehicle().getFee());
                     b = aSpot.removeVehicle();
                     IOSupport.printIt("Success: Vehicle removed!");
                     IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s","Spot ID","Arrival Time","Arrival Date","Time IN","Total Fee"));
                
                     IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","$"+aSpot.getVehicle().getFee()));
                     return b;
                 }
                 else
                 {
                      IOSupport.printIt("Error: No vehicle belonging to"+ownerName+" found!");
                 }
            }
          }
       }
       
       return null;
    }
    
    
    
    /**
     * Accepts registration number of a vehicle and displays if found in parking spot
     * 
     */
    public void findVehicle()
    {
        ArrayList<Spot> foundVehicles = new ArrayList<Spot>();
        {    String ownerName = IOSupport.getString("Enter Owner's Name:");
            if(ownerName.equals("empty"))
            {
                IOSupport.printIt("Error: No owner's name provided");
            }
            else
            {
                foundVehicles = vehiclePark.findVehicle(ownerName);
                //System.out.println(foundVehicles+"");
                if(foundVehicles.isEmpty())
                {
                    IOSupport.printIt("Alert: No vehicle with owner's name "+ownerName+" found!");
                }
                else
                {
                    IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s","Spot ID","Arrival Time","Arrival Date","Time IN","Fee"));
                
                    for(Spot aSpot:foundVehicles)
                    {
                        IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","$"+aSpot.getVehicle().getFee()));
                    }
                }
            }
        }    
    }
    
    /*
     * checks if vehicle with specified owner exists or not
     * @param ownerName name of the vehicle owner
     * @return true if vehicle exists, else false
     */
    public boolean findVehicle(String ownerName)
    {
        ArrayList<Spot> foundVehicles = null;
        {    
            if(ownerName.equals("empty"))
            {
                IOSupport.printIt("Error: No owner's name provided");
                return false;
            }
            else
            {
                foundVehicles = vehiclePark.findVehicle(ownerName);
                //System.out.println(foundVehicles+"");
                if(foundVehicles.isEmpty())
                {
                    IOSupport.printIt("Alert: No vehicle with owner's name "+ownerName+" found!");
                    return false;
                }
                else
                {
                    IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s","Spot ID","Arrival Time","Arrival Date","Time IN","Fee"));
                
                    for(Spot aSpot:foundVehicles)
                    {
                        IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","$"+aSpot.getVehicle().getFee()));
                    }
                    return true;
                }
            }
        }    
    }
    
    /**
     * Removes a vehicle object for a specified registration number from a parking spot and parks it to another specified parking spot 
     * 
     */
    public void moveVehicle()
    {
        String ownerName = IOSupport.getString("Enter the vehicle's Owner's name:");
        if(!ownerName.equals("empty") && !findVehicle(ownerName)==false)
       {
            String spotId = IOSupport.getString("Select a spot to move from:");
            
            if(vehiclePark.findSpot(spotId).getVehicle().getOwner().equals(ownerName) && vehiclePark.findSpot(spotId).getSpotId()!="-1" && vehiclePark.findSpot(spotId).getIsOccupied())
            {
                ArrayList<Spot> receivedAvailSpots = vehiclePark.getAvailableSpots();
                String strSpots="";
                for(Spot aSpot : receivedAvailSpots)
                    {
                        strSpots +=  aSpot.getSpotId()+": $"+aSpot.getParkingCharges()+" ;\n";
                    }
                String moveTo = IOSupport.getString("Select a spot to move to\n"+strSpots);
                if(vehiclePark.findSpot(moveTo).getSpotId()!="-1" && !vehiclePark.findSpot(moveTo).getIsOccupied())    
                {
                    Vehicle foundVehicle = collectVehicleById(spotId,ownerName);
            
                    if(foundVehicle instanceof Car && foundVehicle.getChargedRate()>vehiclePark.findSpot(spotId).getParkingCharges())
                    {
                        foundVehicle.setChargedRate(vehiclePark.findSpot(spotId).getParkingCharges());
                    }
                    vehiclePark.findSpot(moveTo).addVehicle(foundVehicle); 
                }
            }
            else if(vehiclePark.findSpot(spotId).getSpotId().equals(spotId) && vehiclePark.findSpot(spotId).getIsOccupied())
            {
                IOSupport.printIt("Error: "+spotId + " is pre-occupied!");
            }
            else if(spotId.equals("empty"))
            {
                IOSupport.printIt("Error: Spot Id cannot be left empty!");
            }
            else
            {
                IOSupport.printIt("Error: "+spotId + " doesn't exist!");
            }
            
        }
        
        else
        {
            IOSupport.printIt("Error: Owner's name cannot be left empty!");
        }
    }
    
    /**
     * Displays all the parking spots in detail
     * 
     */
    public void showAllSpots()
    {
        IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s","Spot Id","Arrival Time","Arrival Date","Time In","Registration#", "Fee/Hr"));
        for(Spot aSpot : vehiclePark.getAllSpots())
        {
          //checking if it is a car
          
          { 
            if(aSpot.getVehicle()!=null && aSpot.getIsOccupied() && aSpot.getVehicle() instanceof Car )
                IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.",((Car)aSpot.getVehicle()).getRegistrationNumber(),"$"+aSpot.getVehicle().getChargedRate()));
            else if(aSpot.getVehicle()!=null && aSpot.getIsOccupied() && aSpot.getVehicle() instanceof Bicycle )
                 IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),aSpot.getArrivalTime().format(formatter),aSpot.getArrivalTime().getMonth().toString().substring(0, 1).toUpperCase() + aSpot.getArrivalTime().getMonth().toString().substring(1).toLowerCase()+" "+aSpot.getArrivalTime().getDayOfMonth()+", "+aSpot.getArrivalTime().getYear(),aSpot.getVehicle().getTimeIn()+" min.","-Bicycle-","$"+aSpot.getVehicle().getChargedRate()));
            else
                IOSupport.printIt(String.format("%-20s%-20s%-20s%-20s%-20s%-20s",aSpot.getSpotId(),"--","--","--","--","$"+aSpot.getParkingCharges()));
            }
        }
    }
    
}
