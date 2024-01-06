import java.math.BigDecimal;

public class Cashier {
    private String name;
    private final long id;
    private BigDecimal salary;
    private boolean working;
    
    static long nextId = 1;

    public Cashier(String name, BigDecimal salary){
        this.name = name;
        this.salary = salary.compareTo(BigDecimal.ZERO) == 1 ? salary : BigDecimal.ONE; //zaplatata ne moje da e po-malko ot 1
        this.id = nextId++;
        this.working = false;
    }

    public String getName(){ return this.name; }
    public BigDecimal getSalary() { return this.salary; }
    public long getId() { return this.id; }
    public boolean isWorking() { return this.working; }

    public void startWorking(){ this.working = true; }
    public void stopWorking(){ this.working = false; }
}
