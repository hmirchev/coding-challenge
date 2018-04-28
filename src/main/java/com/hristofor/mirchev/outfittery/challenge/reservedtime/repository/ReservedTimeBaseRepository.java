package com.hristofor.mirchev.outfittery.challenge.reservedtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ReservedTimeBaseRepository<T extends ReservedTime> extends JpaRepository<T, Long> {

}
