package com.hristofor.mirchev.outfittery.challenge.stylists.repository;


import com.hristofor.mirchev.outfittery.challenge.reservedtime.repository.ReservedTime;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Stylist implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "stylist")
    private Set<ReservedTime> reservedTimes = new HashSet<>();

    private String displayName;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private StylistStatus status;

    @NotNull
    @Convert(converter = Jsr310JpaConverters.ZoneIdConverter.class)
    private ZoneId timeZone;

    protected Stylist() {
    }

    public Long getId() {
        return id;
    }

    public Set<ReservedTime> getReservedTimes() {
        return reservedTimes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public StylistStatus getStatus() {
        return status;
    }

    public void setStatus(StylistStatus status) {
        this.status = status;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }
}
