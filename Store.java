import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Store{
    private String name;
    private ArrayList<CashRegister> cashRegisters;
    private ArrayList<Cashier> cashiers;
    private HashMap<Product, Integer> products;
    private HashMap<Product, Integer> soldProducts;
    private BigDecimal foodMarkup;
    private BigDecimal nonFoodMarkup;
    private int daysOfDiscount;
    private BigDecimal discount;
    private BigDecimal expenses;
    private BigDecimal profits;
    private long totalReceiptsIssued;
    
    public Store(String name, BigDecimal foodMarkup, BigDecimal nonFoodMarkup, int daysOfDiscount, BigDecimal discount){
        this.name=name;
        this.foodMarkup = foodMarkup;
        this.nonFoodMarkup = nonFoodMarkup;
        this.daysOfDiscount = daysOfDiscount;
        this.discount = discount;

        this.cashiers = new ArrayList<Cashier>();
        this.cashRegisters = new ArrayList<CashRegister>();
        this.products = new HashMap<Product, Integer>();
        this.soldProducts = new HashMap<Product, Integer>();
        this.expenses = BigDecimal.ZERO;
        this.profits = BigDecimal.ZERO;
        this.totalReceiptsIssued = 0;
    }
    
    public String getName() { return name; }
    public BigDecimal getExpenses(){ return expenses; }
    public BigDecimal getProfits(){ return profits; }
    public long getTotalReceiptsIssued() { return totalReceiptsIssued; }
    public BigDecimal getFoodMarkup() { return foodMarkup; }
    public BigDecimal getNonFoodMarkup() { return nonFoodMarkup; }
    public int getDaysOfDiscount() { return daysOfDiscount; }
    public BigDecimal getDiscount() { return discount; }
    public HashMap<Product, Integer> getSoldProducts() { return soldProducts; }
    public HashMap<Product, Integer> getProducts() { return products; }
    public ArrayList<CashRegister> getCashRegisters(){ return this.cashRegisters; }

    public boolean hasProduct(Product product){ return this.products.containsKey(product); }
    
    public void setName(String name) { this.name = name; }

    public void addCashRegister(){ this.cashRegisters.add(new CashRegister(this)); }
    public void addReceipt(){ this.totalReceiptsIssued++; }
    public void addProfits(BigDecimal profit){ this.profits = this.profits.add(profit); }
    
    public void addProduct(Product product){
        if(!this.products.containsKey(product)){
            this.products.put(product, 0);
        }
        this.products.put(product, products.get(product)+1);
        this.expenses = expenses.add(product.getPrice());
    }
    
    public void returnProduct(Product product){
        if(!this.products.containsKey(product)){
            this.products.put(product, 0);
        }
        this.products.put(product, products.get(product)+1);
    }

    public void removeProduct(Product product){
        if(this.products.containsKey(product)){
            this.products.put(product, products.get(product)-1);
        }
        if (this.products.get(product) <= 0){
            this.products.remove(product);
        }
    }

    public void addToSoldProducts(Product product){
        if(!this.soldProducts.containsKey(product)){
            this.soldProducts.put(product, 0);
        }
        this.soldProducts.put(product, this.soldProducts.get(product)+1);
    }
    
    public void removeExpiredProducts(){
        Iterator<Product> it = this.products.keySet().iterator();
        while(it.hasNext()){
            Product p = it.next();
            if(p.getDaysToExpirationDate() <= 0){
                it.remove();
            }
        }
    }

    public void hireCashier(Cashier cashier){
       this.cashiers.add(cashier);
       this.expenses = expenses.add(cashier.getSalary());
    }

    public void startHandling(){
        for(int i = 0; i < this.cashRegisters.size(); ++i){
            if(!this.cashRegisters.get(i).isQueueEmpty()){
                try{
                    this.cashRegisters.get(i).handleClients();
                }
                catch(RuntimeException e){
                    System.out.println(e);
                    --i; // ako ima exception vrushtame indexa nazad za da obslujim ostanalite klienti na opashkata 
                }
            }
        }
    }
}