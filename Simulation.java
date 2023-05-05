import Observer.Logger;
import Observer.Publisher;
import Observer.Tracker;
import buyer.Customer;

import java.util.ArrayList;
import java.util.Random;

public class Simulation implements Publisher {

    ArrayList<PGATourSuperstore> pga;

    static int day = 0;
    static String days[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    int numPga;

    public Simulation()
    {
        numPga = 0;
        pga = new ArrayList<PGATourSuperstore>();

        this.pga.add(new PGATourSuperstore("FNCD"+String.valueOf(numPga)));
        numPga++;


        run();//start simulation
    }
    public static String getDayOfTheWeek()
    {
        return days[day %7];
    }
    public void run() {


        Tracker track = Singleton.getInstanceTracker();
        for(int i = 0; i < 1; i++) {
            pga.get(i).registerSubscriber(track);
        }
        while (day < 5) {
            Logger log = Singleton.getInstanceLogger(day);
            for(int i = 0; i < pga.size(); i++)
            {
                pga.get(i).registerSubscriber(log);//adding the subcriber into the list
            }

            ArrayList<Customer> buyers = new ArrayList<Customer>();

            System.out.println("===== Day: " + (day + 1) + "," + getDayOfTheWeek() + "=====");
//
            for(int i = 0; i < pga.size(); i++)//opening for all pga stores
            {
                pga.get(i).opening();
            }
            Random rand = new Random();
            int numService = rand.nextInt(30);
            ArrayList<Customer> customers = new ArrayList<Customer>();
            for(int i = 0; i< numService;i++)
            {
                customers.add(new Customer());
            }
            for(int i = 0; i < pga.size(); i++)
            {
                pga.get(i).Service(customers);
            }
            for(int i = 0; i < pga.size(); i++)
            {
                pga.get(i).fitting(customers);
            }
            for(int i = 0; i <  pga.size(); i++)
            {
                pga.get(i).selling(customers.get(i));
            }
            for(int i = 0; i <  pga.size(); i++)
            {
                pga.get(i).ending();
            }
//

            track.showReport();//should match and part of observer pattern tracking side
//
            for(int i = 0; i < pga.size(); i++)//opening for all fncd
            {
                pga.get(i).unregisterSubscriber(log);
            }
            day++;
        }
//
        for(int i = 0; i < 1; i++)//opening for all fncd
        {
            pga.get(i).unregisterSubscriber(track);
        }
    }


}
