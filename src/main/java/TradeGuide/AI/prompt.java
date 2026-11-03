package TradeGuide.AI;

public class prompt{
    private String email;
    private String name;
    private int riskLevel;
    private int timeFrame;
    private Double value;
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public int getRiskLevel(){
        return riskLevel;
    }
    public int getTimeFrame(){
        return timeFrame;
    }
    public double  getValue(){
        return value;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setriskLevel(int riskLevel){
        this.riskLevel=riskLevel;
    }
    public void setimeFrame(int timeFrame){
        this.timeFrame=timeFrame;
    }
    public void setValue(Double value){
        this.value=value;
    }
}
