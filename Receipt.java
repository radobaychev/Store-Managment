import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Receipt {
    private final long serialNum;
    private long cashierId;
    private String cashierName;
    private LocalDateTime timeOfCreation;
    private ArrayList<String> productNames;
    private ArrayList<BigDecimal> productPrices;
    private BigDecimal total;

    private static long nextNum = 1;

    public Receipt(String cashierName, long cashierId){
        this.cashierName = cashierName;
        this.cashierId = cashierId;
        this.timeOfCreation = LocalDateTime.now();
        this.productNames = new ArrayList<String>();
        this.productPrices = new ArrayList<BigDecimal>();
        this.serialNum = nextNum++;
        this.total = BigDecimal.ZERO;
    }
    
    public BigDecimal getTotal() { return total; }
    public static long getNextNum() { return nextNum; }
    public long getSerialNum() { return serialNum; }
    
    public void addProduct(String name, BigDecimal price){
        this.productNames.add(name);
        this.productPrices.add(price);
        this.total = total.add(price);
    }
    

    public String toString(){
        String result = "";
       
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = timeOfCreation.format(formatDate);
       
        result += String.valueOf(this.serialNum) + "\n";
        result += "Cashier's name: " + cashierName + "\n" + "His ID: " + String.valueOf(cashierId) + "\n";
        result += "-----------------------------------\nProducts:\n";

        Iterator<String> nameIterator = this.productNames.iterator();
        Iterator<BigDecimal> pricesIterator = this.productPrices.iterator();
        while(nameIterator.hasNext()){
            result += nameIterator.next() + " " + pricesIterator.next().toString() + "$\n";
        }

        result += "Total price: " + this.total.toString() + "$\n";
        result += "-----------------------------------\n" +"Date: " + formattedDate.toString() + "\n";

        return result;
    }


    
}
