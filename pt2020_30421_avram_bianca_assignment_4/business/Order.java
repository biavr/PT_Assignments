package business;


import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {
    private int id;
    private static int idinc = 0;
    private LocalDate date;

    public Order(int id,LocalDate date){
        this.id = id;
        this.date = date;
    }

    public Order(){
        idinc++;
        id = idinc;
        date=LocalDate.now();
    }

    public String getDate() {
        return date.toString();
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode(){
        int code = id;
        code+=date.getYear();
        code+=date.getMonthValue();
        code+=date.getDayOfYear();
        return code;
    }
    @Override
    public boolean equals(Object o){
        Order order = (Order)o;
        return Integer.compare(this.id,order.id) == 0 && this.date.equals(order.date);
    }
}
