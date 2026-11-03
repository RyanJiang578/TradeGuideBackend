package TradeGuide.AI;

public class Loginreturn {
    private Boolean loginSucess;
    private String email;
    private String username;
    private int riskLevel;
    private int timeFrame;
    private String token;
    public Boolean getLoginSucess(){
        return loginSucess;
    }
    public String getEmail(){
        return email;
    }
    public String getUsername(){
        return username;
    }
    public int getRiskLevel(){
        return riskLevel;
    }
    public int getTimeFrame(){
        return timeFrame;
    }
    public String getToken(){
        return token;
    }
    public void setLoginSucess(Boolean loginSucess){
        this.loginSucess=loginSucess;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setRiskLevel(int riskLevel){
        this.riskLevel=riskLevel;
    }
    public void setTimeFrame(int timeFrame){
        this.timeFrame=timeFrame;
    }
    public void setToken(String token){
        this.token=token;
    }
}
