package TradeGuide.AI;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int stockID;
    private int userID;
    private String symbol;
    private double shares;
    private double currentValue;
    private double initialValue;
    public int getstockID(){
        return stockID;
    }
    public int getuserID(){
        return userID;
    }
    public String getsymbol(){
        return symbol;
    }
    public double getShares(){
        return shares;
    }
    public double getcurrentValue(){
        return currentValue;
    }
    public double getinitialValue(){
        return initialValue;
    }
    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
    public void setShares(double shares){
        this.shares=shares;
    }
    public void setcurrentValue(double currentValue){
        this.currentValue=currentValue;
    }
    public void setinitialValue(double initialValue){
        this.initialValue=initialValue;
    }
    public void setuserID(int ID){
        this.userID=ID;
    }
}
