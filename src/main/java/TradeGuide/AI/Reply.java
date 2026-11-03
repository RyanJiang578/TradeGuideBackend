package TradeGuide.AI;

public class Reply{
    private double currentValue;
    private double projectedValue;
    private String information;
    private int responsecode;
    public double getCurrentValue(){
        return currentValue;
    }
    public double getProjectedValue(){
        return projectedValue;
    }
    public String getInformation(){
        return information;
    }
    public int getResponseCode(){
        return responsecode;
    }
    public void setcurrentValue(double value){
        this.currentValue=value;
    }
    public void setprojectedValue(double value){
        this.projectedValue=value;
    }
    public void setInformation(String info){
        this.information=info;
    }
    public void setResponseCode(int code){
        this.responsecode=code;
    }
}
