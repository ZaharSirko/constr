package com.example.constr.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "stats")
public class Stats {
    @Id
    @SequenceGenerator(
        name = "stats_sequence",
        sequenceName = "stats_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "stats_sequence"
    )
    @Column(name = "stats_id")
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @NotNull
    @Column(name = "stats_sales")
    private int sales;

    @NotNull
    @Column(name = "stats_losses")
    private float losses;
    
    @NotNull
    @Column(name = "stats_profit")
    @Transient
    private float profit; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public float getLosses() {
        return losses;
    }

    public void setLosses(float losses) {
        this.losses = losses;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }


}