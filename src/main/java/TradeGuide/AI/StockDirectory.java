package TradeGuide.AI;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="stock_directory")
public class StockDirectory {
    @Id
    private String symbol;
    private String name;
    
    public String getSymbol(){
        return symbol;
    }
    public String getName(){
        return name;
    }

    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
    public void setName(String name){
        this.name=name;
    }
}
