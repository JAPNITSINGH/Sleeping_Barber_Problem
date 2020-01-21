/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sleeping_barber_problem;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mahe
 */
class Person implements Runnable{
  private String name;
  private static Barber b;
  static int curr_no = 0;
  public boolean wokeHimUp = false;
  Person(String name , Barber b)
  {
      this.name = name;
      this.b = b;
  }
  public String getName()
  {
      return this.name;
  }
  synchronized public void goToBarber()
  {
      System.out.println(this.name + " arrives.");
      curr_no++;
      if(this.b.getState() == 0)
      {
      wakeUpBarber();
      }
  }
 synchronized public void wakeUpBarber()
  {    
         
          System.out.println("Barber Woken up by " + this.name);
          this.b.setState(1);
          this.wokeHimUp = true;
          hairCut();
      
  }
 synchronized public void hairCut()
  {
      // this message should be in sync only one person at a time.
      // person leaves after getting haircut
      // call leaveBarber() at the end
      System.out.println(this.name + " gets a hair cut");
  }
   synchronized public void leaveBarber()
  {
        System.out.println(this.name + " leaves");
        curr_no--;
        if(curr_no <= 0)
        {
            b.goToSleep();
            b.setState(0);
        }
            
  }
    
    @Override
    public void run() {
        goToBarber();
        //wakeUpBarber();
        if(this.wokeHimUp == false)
            hairCut();
        leaveBarber();
        
        
             
    }
}

class Barber implements Runnable{
    private static int state = 0;
    Barber(){     
    }
    public void cutPersonHair(Person p)
    {
        // This has to be in sync mostly
        // this process should have a delay of 3sec.
    }
    public void goToSleep()
    {   
       System.out.println("Barber Sleeping");
      
    }
    public int getState()
    {
        return this.state;
    }
    public void setState(int x)
    {
        this.state = x;
    }

    @Override
    public void run() {
        if(this.state == 0)
        {goToSleep();
         
        }
    }
}
public class Sleeping_Barber_Problem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Using 10 persons");
        int n = 10;
        ArrayList<Person> listOfPersons = new ArrayList<Person>();
        Barber b = new Barber();
        for(int i = 0 ; i < n ; i++ )
        {
            Person p = new Person("Person " + Integer.toString(i) , b);
            listOfPersons.add(p);
        }
        //Run Barber
        
        Thread barberThread = new Thread(b);
        barberThread.start();
        
        //Run Persons
        for(int i  = 0 ; i < listOfPersons.size() ; i++)
        {
            //System.out.println(listOfPersons.get(i).getName());
            Thread t = new Thread(listOfPersons.get(i));
            t.start();
        }
        
        
    }
    
}
