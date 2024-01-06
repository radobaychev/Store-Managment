import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;


public class CashRegister{
    private final long id;
    private Store store;
    private Optional<Cashier> cashier;
    private Queue<Client> clients;

    private static long nextId = 1;
    
    public CashRegister(Store store){
        this.store = store;
        this.id = nextId++;
        this.cashier = null;
        this.clients = new LinkedList<Client>();
    }

    public long getId(){ return this.id; }
    public Optional<Cashier> getCashier(){ return this.cashier; }
    public boolean isQueueEmpty(){ return clients.isEmpty(); }

    public void addClient(Client client){
        this.clients.add(client);
    }

    public void setCashier(Cashier cashier) throws RuntimeException{
        if(this.cashier != null){
            throw new RuntimeException("Register is occupied!");
        }
        if(cashier.isWorking()){
            throw new RuntimeException("Cashier is already working!");
        }
        this.cashier = Optional.of(cashier);
        cashier.startWorking();
    }

    public void removeCashier() throws RuntimeException{
        if(!this.cashier.isPresent()){
            throw new RuntimeException("No Cashier!");
        }
        if(!this.clients.isEmpty()){
            throw new RuntimeException("More clients in queue!");
        }
        this.cashier.get().stopWorking();
        this.cashier = null;
    }

    public void handleClients() throws RuntimeException{
        while(!this.clients.isEmpty()){
            Client client = this.clients.poll();
            Receipt r = new Receipt(this.cashier.get().getName(), this.cashier.get().getId());
            for (Product p : client.getProducts()) {
                BigDecimal price = p.getPrice();
                if(p.getIsFood()){
                    price = price.add(price.multiply(this.store.getFoodMarkup().divide(BigDecimal.valueOf(100))));
                }
                else{
                    price = price.add(price.multiply(this.store.getNonFoodMarkup().divide(BigDecimal.valueOf(100))));
                }
                if(p.getDaysToExpirationDate() <= store.getDaysOfDiscount()){
                    price = price.subtract(price.multiply(store.getDiscount().divide(BigDecimal.valueOf(100))));
                }
                price = price.setScale(2, RoundingMode.HALF_UP);
                r.addProduct(p.getName(), price);
            }
            if(r.getTotal().compareTo(client.getMoney()) == 1){
                for (Product product : client.getProducts()) {
                    store.returnProduct(product);
                }
                client.leaveStore();
                throw new RuntimeException("Not enough money!");
            }
            for (Product product : client.getProducts()) {
                store.addToSoldProducts(product);
            }
            store.addProfits(r.getTotal()); 
            store.addReceipt();
            issueReceipt(r.toString());
            client.setMoney(client.getMoney().subtract(r.getTotal()));
            client.leaveStore();
        }
    }

    private static void issueReceipt(String receipt){
        String serNum = receipt.split("\n")[0];
        File file = new File("./Receipts/" + serNum);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));       
            writer.write(receipt);
            writer.close();
        }catch (IOException ex) {           
            System.out.println(ex.getMessage());
        }
        System.out.println(receipt);
    }
}
