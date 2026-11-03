package TradeGuide.AI;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="evaluation_historys")
public class EvaluationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser user;
    private String symbol;
    private Double currentValue;
    private Double projectedValue;
    private LocalDate date;
    @Column(columnDefinition = "TEXT")
    private String information;

    public Long getId(){
        return id;
    }
    public AppUser getUser(){
        return user;
    }
    public String getSymbol(){
        return symbol;
    }
    public Double getcurrentValue(){
        return currentValue;
    }
    public Double getprojectedValue(){
        return projectedValue;
    }
    public LocalDate getDate(){
        return date;
    }
    public String getInformation(){
        return information;
    }
    public void setUser(AppUser user){
        this.user=user;
    }
    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
    public void setcurrentValue(Double currentValue){
        this.currentValue=currentValue;
    }
    public void setprojectedValue(Double projectedValue){
        this.projectedValue=projectedValue;
    }
    public void setDate(LocalDate date){
        this.date=date;
    }
    public void setInformation(String information){
        this.information=information;
    }
}
