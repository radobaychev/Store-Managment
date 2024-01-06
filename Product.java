import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Product {
    private String name;
    private final long id;
    private BigDecimal price;
    private boolean isFood;
    private LocalDate expirationDate;

    static long nextId = 0;

    public Product(String name, BigDecimal price, boolean isFood, LocalDate expirationDate){
        this.name = name;
        this.price = price.compareTo(BigDecimal.ZERO) == 1 ? price : BigDecimal.ZERO;
        this.isFood = isFood;
        this.expirationDate = expirationDate;
        this.id = nextId++;
    }

    public String getName(){ return this.name; }
    public long getId(){ return this.id; }
    public BigDecimal getPrice(){ return this.price; }
    public boolean getIsFood(){ return this.isFood; }
    public long getDaysToExpirationDate() { return LocalDate.now().until(this.expirationDate, ChronoUnit.DAYS); }

    public void setName(String name){ this.name = name; }

}
