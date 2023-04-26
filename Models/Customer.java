package Models;

public class Customer {
    int cusnumber;
    String cusname;
    String cellno;
    String EmailAdress;

    //CONSTRUCTORS
    public Customer(String cusname, int cusnumber, String emailAdress, String cellno){
        this.cusname=cusname;
        this.cellno=cellno;
        this.EmailAdress=emailAdress;
        this.cusnumber=cusnumber;
    }

    public Customer(String cusname, String cellno, String emailAdress) {
        this.cusname = cusname;
        this.cellno = cellno;
        EmailAdress = emailAdress;
    }

    public String getCusname(){ return cusname; }

    public String getCellno() {
        return cellno;
    }

    public int getCusnumber() {
        return cusnumber;
    }

    public String getEmailAdress() {
        return EmailAdress;
    }

    @Override
    public String toString() {
        return getCusnumber() + ", "+ getCusname()+", "+getCellno()+", "+getEmailAdress();
    }
}


