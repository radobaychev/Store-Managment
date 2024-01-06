import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class Client {
    private BigDecimal money;
    private ArrayList<Product> products;
    private Optional<Store> currentStore;

    static int nextNum = 0;

    public Client(BigDecimal money){
        this.money = money;
        this.products = new ArrayList<Product>();
    }

    public BigDecimal getMoney() { return this.money;  }
    public void setMoney(BigDecimal money) { this.money = money; }
    public ArrayList<Product> getProducts(){ return this.products; }

    public void goToStore(Store store){
        this.currentStore = Optional.of(store);
    }

    public void leaveStore(){
        if(this.currentStore.isPresent()){
            this.currentStore = null;
        }
    }

    public void takeProduct(Product product){
        if(this.currentStore.isPresent()){
            if(this.currentStore.get().hasProduct(product)){
                products.add(product);
                currentStore.get().removeProduct(product);
            }        
        }
    }

    public void checkout(){
        if(!this.products.isEmpty() && currentStore.isPresent()){
            ArrayList<CashRegister> registers = this.currentStore.get().getCashRegisters();
            for(int i = 0; i < registers.size(); ++i){
                if(registers.get(i).getCashier() != null){
                    registers.get(i).addClient(this);
                    break;
                }
            }
        }   
    }
}
