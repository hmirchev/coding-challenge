package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedTimeRepository extends JpaRepository<ReservedTime, Long> {
}
