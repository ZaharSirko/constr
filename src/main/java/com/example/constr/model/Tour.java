package com.example.constr.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @SequenceGenerator(
        name = "tour_sequence",
        sequenceName = "tour_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "tour_sequence"
    )
    @Column(name = "tour_id")
    private int id;

    @NotNull
    @Column(name = "tour_name")
    private String tourName;

    @NotNull
    @Column(name = "tour_end_time")
    private Date endTime;

    @NotNull
    @Column(name = "tour_start_time")
    private Date startTime;

    @NotNull
    @Column(name = "tour_type")
    private String type;

    @NotNull
    @Column(name = "tour_max_number_of_people")
    private int maxNumberOfPeople;
    
    @NotNull
    @Column(name = "tour_min_number_of_people")
    private int minNumberOfPeople;

    @Column(name = "tour_current_number_of_people")
    private Integer  currentNumberOfPeople = 0;

    @NotNull
    @Column(name = "tour_description", columnDefinition = "TEXT")
    private String description;
    
    @NotNull
    @Column(name = "tour_pirce")
    private int price;

    @ManyToMany(mappedBy = "tours")
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

 

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(int maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinNumberOfPeople() {
        return minNumberOfPeople;
    }

    public void setMinNumberOfPeople(int minNumberOfPeople) {
        this.minNumberOfPeople = minNumberOfPeople;
    }

    public int getCurrentNumberOfPeople() {
        return currentNumberOfPeople;
    }

    public void setCurrentNumberOfPeople(int currentNumberOfPeople) {
        this.currentNumberOfPeople = currentNumberOfPeople;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}