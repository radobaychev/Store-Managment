import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map.Entry;

public class Main{
    public static void main(String[] args) {
        Client Gosho = new Client(BigDecimal.valueOf(1500));
        Client Petur = new Client(BigDecimal.valueOf(6));
        Client Drago = new Client(BigDecimal.valueOf(4000));

        Cashier Ivan = new Cashier("Ivan", BigDecimal.valueOf(150));

        Product dinq = new Product("Dinq", BigDecimal.valueOf(8.45), true, LocalDate.now().plusDays(3));
        Product zahar = new Product("Zahar", BigDecimal.valueOf(2.33), true, LocalDate.now().plusDays(60));
        Product kroasan = new Product("Kroasan", BigDecimal.valueOf(1.80), true, LocalDate.now().plusDays(-5));
        Product sapun = new Product("Sapun",  BigDecimal.valueOf(1.80), false, LocalDate.now().plusDays(100));
        Product djapanki = new Product("Djapanki", BigDecimal.valueOf(7), false, LocalDate.now().plusDays(2000));

        Store billa = new Store("Billa", BigDecimal.valueOf(250), BigDecimal.valueOf(50), 7, BigDecimal.valueOf(10));
        billa.addCashRegister();
        billa.addCashRegister();

        for (int i = 0; i < 8; i++) {
            billa.addProduct(dinq);
        }

        for(int i = 0; i < 5; i++){
            billa.addProduct(zahar);
        }

        for(int i = 0; i < 2; i++){
            billa.addProduct(sapun);
        }

        billa.addProduct(djapanki);

        billa.hireCashier(Ivan);
        billa.getCashRegisters().get(1).setCashier(Ivan);

        billa.removeExpiredProducts();

        Gosho.goToStore(billa);
        Gosho.takeProduct(sapun);
        Gosho.takeProduct(zahar);
        Gosho.takeProduct(dinq);
        Gosho.takeProduct(kroasan);
        Gosho.takeProduct(zahar);
        Gosho.takeProduct(zahar);
        Gosho.takeProduct(sapun);
        Gosho.takeProduct(sapun);
        Gosho.checkout();


        Petur.goToStore(billa);
        Petur.takeProduct(djapanki);
        Petur.checkout();

        Drago.goToStore(billa);
        Drago.takeProduct(dinq);
        Drago.takeProduct(dinq);
        Drago.takeProduct(dinq);
        Drago.checkout();

        try { //Test na exeption
             billa.getCashRegisters().get(1).removeCashier();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
        billa.startHandling();
        System.out.println("Expenses: -" + billa.getExpenses());
        System.out.println("Profits: " + billa.getProfits());
        Iterator<Entry<Product, Integer>> sold = billa.getProducts().entrySet().iterator();
        while(sold.hasNext()){
            Entry<Product, Integer> crr = sold.next();
            System.out.println(crr.getKey().getName() + ":" + crr.getValue());
        }
        System.out.println(billa.getTotalReceiptsIssued());
    }

    
}