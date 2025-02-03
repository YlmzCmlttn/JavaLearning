package com.flybetter.flightscheduling.repository;

import com.flybetter.flightscheduling.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
} 