package screener.db;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity // Marks this class as a JPA entity (a database table)
@Table(name = "companies") // Sets the table name
public class Company {
    
    @Id // Marks this as the primary key
    private String ticker; //varying(10)

    private String name; //varying(50)
    private Double ev; // ev stands for Enterprise Value (double)
    private String metricType;
    private Metrics metric; // matches "metric" object

    public Metrics getMetric() {
        return metric;
    }
    public void setMetric(Metrics metric) {
        this.metric = metric;
    }

    public class Metrics {
        @JsonProperty("10DayAverageTradingVolume")
        private double _10DayAverageTradingVolume; // field names must match JSON

        // getter/setter
        public double get_10DayAverageTradingVolume() { return _10DayAverageTradingVolume; }
        public void set_10DayAverageTradingVolume(double value) { this._10DayAverageTradingVolume = value; }
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public Company() {}

    public Company(String ticker, String name, Double ev) {
        this.ticker = ticker;
        this.name = name;
        this.ev = ev;
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
    public double getEv() {
        return ev;
    }
    public void setEv(double ev) {
        this.ev = ev;
    }
    @Override
    public String toString() {
        return "Company [ticker=" + ticker + ", name=" + name + ", ev=" + ev + ", metricType=" + metricType + ", metrics" + metric.get(0) + "]";
    }
}