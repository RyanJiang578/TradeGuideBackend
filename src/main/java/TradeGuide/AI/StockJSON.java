package TradeGuide.AI;

public class StockJSON {
    private int userID;
    private String stockName;
    private int shares;
    public int getuserID(){
        return userID;
    }
    public String getStockName(){
        return stockName;
    }
    public int getShares(){
        return shares;
    }
    public void setuserID(int ID){
        this.userID=ID;
    }
    public void setStockName(String name){
        this.stockName=name;
    }
    public void setShares(int shares){
        this.shares=shares;
    }
}
