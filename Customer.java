import java.io.*;
import java.util.*;
/**Author: Masilakhe Xakayi
 * Customer
 */
 class Customer implements Serializable{
    int cusno;
    String cusname;
    int cellno;

    Customer(int cusno, String cusname, int cellno){
        this.cusno = cusno;
        this.cusname = cusname;
        this.cellno = cellno;
    }
    public String toString(){
        return cusno+" "+cusname+" "+cellno;
    }
    
}

class CustomerDemo{
    public static void main(String[] args) throws Exception{
        int choice = -1;
        Scanner s = new Scanner(System.in);
        Scanner s1 = new Scanner(System.in);
        File file = new File("customer.txt");
        ArrayList<Customer> al = new ArrayList<>();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ListIterator li = null;

    //If files exist you can load data in to the file
        if(file.isFile()){
    //then read into the file//
            ois=new ObjectInputStream(new FileInputStream(file));
    //data will be loaded to this arraylist//
            al = (ArrayList<Customer>)ois.readObject();
            ois.close();
        }

        do{
            System.out.println("1.INSERT CUSTOMERS");
            System.out.println("2.DISPLAY CUSTOMERS");
            System.out.println("3.SEARCH FOR A CUSTOMER");
            System.out.println("4.SORT LIST ASC");
            System.out.println("0.EXIT");
            System.out.print("Enter Your Choice : ");
            choice = s.nextInt();

            switch(choice){
                case 1:
                System.out.println("Enter how many Customers you want: ");
                int n = s.nextInt();
                for(int i=0;i<n;i++){
                System.out.print("Enter Customer Number: ");
                int cusno = s.nextInt();
        
                System.out.print("Enter Customer Name: ");
                String cusname = s1.nextLine();

                System.out.print("Enter Customer Cell Number: ");
                int cellno = s.nextInt();

                //writing data into the arraylist//
                al.add(new Customer(cusno, cusname, cellno));

                }

                //Writing data into the file//
                oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(al);
                oos.close();  
                break;
                case 2:
                if(file.isFile()){
                    ois=new ObjectInputStream(new FileInputStream(file));
                    //data will be loaded to this arraylist//
                    al = (ArrayList<Customer>)ois.readObject();
                    ois.close();

                    System.out.println("---------------------");
                li = al.listIterator();
                while(li.hasNext())
                System.out.println(li.next());
                System.out.println("---------------------");

            }else{
                System.out.println("File does not exist...!");
            }

            break;
                case 3:
                if(file.isFile()){
                    ois=new ObjectInputStream(new FileInputStream(file));
                    //data will be loaded to this arraylist//
                    al = (ArrayList<Customer>)ois.readObject();
                    ois.close();
                    ///searching for empno

                    Boolean found = false;
                System.out.println("Enter cusno to Search : ");
                int cusno = s.nextInt();
                System.out.println("---------------------");
                li = al.listIterator();
                while(li.hasNext()){
                   Customer e = (Customer)li.next();
                   if(e.cusno == cusno){
                System.out.println(e);
                found = true;
                }
            }
            if(!found)
            System.out.println("Record not found---!");
                System.out.println("---------------------");
        }else{
            System.out.println("File does not exist....");
        }

        break;

                case 4:
                if(file.isFile()){
                    ois=new ObjectInputStream(new FileInputStream(file));
                    //data will be loaded to this arraylist//
                    al = (ArrayList<Customer>)ois.readObject();
                    ois.close();
                    Collections.sort(al, new Comparator<Customer>() {
                        public int compare(Customer e1, Customer e2){
                            return e1.cusname.compareTo(e2.cusname);
                        }
                    });
                    oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(al);
                    oos.close();

                System.out.println("---------------------");

                li = al.listIterator();
                while(li.hasNext())
                System.out.println(li.next());
                System.out.println("---------------------");

            }else{
                System.out.println("File does not exist...!");
            }
                break;
            }

        }while(choice!=0);
    }
}