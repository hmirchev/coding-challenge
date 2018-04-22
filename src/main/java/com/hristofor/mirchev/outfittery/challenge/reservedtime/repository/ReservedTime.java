package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hristofor.mirchev.outfittery.challenge.stylists.repository.Stylist;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class ReservedTime implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Stylist stylist;

    @NotNull
    private OffsetDateTime start;

    @NotNull
    private OffsetDateTime end;

    protected ReservedTime() {
    }

    public Long getId() {
        return id;
    }

    public Stylist getStylist() {
        return stylist;
    }

    public void setStylist(Stylist stylist) {
        this.stylist = stylist;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public void setEnd(OffsetDateTime end) {
        this.end = end;
    }
}
