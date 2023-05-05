package Activity;

import Observer.Publisher;
import buyer.Customer;
import goods.Item;
import staff.SoftGood;
import staff.Staff;

import java.util.ArrayList;
import java.util.Random;

public class Selling implements Publisher {
    protected String pgaStorenumber;

    public Selling(String store)
    {
        pgaStorenumber = store;
    }
    public double selling(Customer customer, ArrayList<Staff>[] employees, ArrayList<Item> inventory, ArrayList<Item> soldInventory, double netSales, double staffEarnigns)
    {
        System.out.println("Starting selling...");
        Random rand = new Random();
        int numItemsOffered= rand.nextInt(20);
        int randEmployee = rand.nextInt(employees[4].size());
        SoftGood currEmployee= (SoftGood) employees[4].get(randEmployee);
        for(int i = 0; i < numItemsOffered; i++)
        {
            Item currItem = inventory.get(rand.nextInt(inventory.size()));//gets random item from bound
            double chance = rand.nextDouble(); //number 0-1
            if(chance <=0.45)//buy product
            {
                customer.addToCart(currItem);
                System.out.println("Customer: "+ customer.name +" choose to buy "+ currItem.getBrand() + " "+currItem.getModel()+" :" + currItem.getPrice());
                netSales += currItem.getPrice();
                double bonus = currItem.getPrice() * 0.1;
                System.out.println("Associate: "+ currEmployee.getName()+ "recived bonus- "+ bonus);
                netSales-= bonus;
                currEmployee.setBonus(bonus);
                notifySubscriber("staff",String.valueOf(bonus));
                soldInventory.add(currItem);//add to sold inventory
                inventory.remove(currItem);//remove from inventory
                //recountinventory


            }
            else// not buy
            {
                System.out.println("Customer: "+ customer.name +" choose not to buy "+ currItem.getBrand() + " "+currItem.getModel()+" :" + currItem.getPrice());
            }
            //show receipt
        }
        System.out.println("done selling");
        return netSales;
    }
}
