package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import com.hristofor.mirchev.outfittery.challenge.users.repository.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "CallOrder")
public class CallOrder extends ReservedTime {

    @OneToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
