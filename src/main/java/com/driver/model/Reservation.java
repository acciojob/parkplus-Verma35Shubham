package com.driver.model;

import javax.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numberOfHours;

    public Reservation() {
    }

    public Reservation(int id, int numberOfHours) {
        this.id = id;
        this.numberOfHours = numberOfHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    @OneToOne
    @JoinColumn
    Payment payment;

    @ManyToOne
    @JoinColumn
    User user;

    @ManyToOne
    @JoinColumn
    Spot spot;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
}
