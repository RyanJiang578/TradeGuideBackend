package TradeGuide.AI;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String username;
    @Column(name = "\"riskLevel\"")
    private int riskLevel;
    @Column(name = "\"timeFrame\"")
    private int timeFrame;
    private int usagecount;
    private LocalDate lastusedday;
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getUsername(){
        return username;
    }
    public int getriskLevel(){
        return riskLevel;
    }
    public int gettimeFrame(){
        return timeFrame;
    }
    public int getUsage(){
        return usagecount;
    }
    public LocalDate getDate(){
        return lastusedday;
    }
    public void setEmail(String email) {
        this.email=email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setRiskLevel(int riskLevel){
        this.riskLevel=riskLevel;
    }
    public void settimeFrame(int timeFrame){
        this.timeFrame=timeFrame;
    }

    public void setUsage(int usagecount){
        this.usagecount=usagecount;
    }
    public void setDate(LocalDate date){
        this.lastusedday=date;
    }
}

