package com.hristofor.mirchev.outfittery.challenge.stylists.repository;

import com.hristofor.mirchev.outfittery.challenge.stylists.repository.StylistStatus;

/**
 * Wrapper class for the Enum is necessary so that Jackson can deserialize the request body (json object) as an enum.
 */
public class StylistStatusWrapper {
    private StylistStatus status;

    protected StylistStatusWrapper() {
    }

    public StylistStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = StylistStatus.valueOf(status);
    }
}
