package screener.db;

import jakarta.persistence.*;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "companies") // Sets the table name
public class Company {
    
    @Id // Marks this as the primary key
    private String ticker; //varying(10)

    private String name; //varying(50)
    private Double enterpriseValue; // ev stands for Enterprise Value (double)
    


    public Company() {}

    public Company(String ticker, String name, Double enterpriseValue) {
        this.ticker = ticker;
        this.name = name;
        this.enterpriseValue = enterpriseValue;
    }
    
    // Getters and Setters

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getEnterpriseValue() {
        return enterpriseValue;
    }
    public void setEnterpriseValue(double enterpriseValue) {
        this.enterpriseValue = enterpriseValue;
    }
    @Override
    public String toString() {
        return "Company [ticker=" + ticker + ", name=" + name + ", ev=" + enterpriseValue + "]";
    }
}