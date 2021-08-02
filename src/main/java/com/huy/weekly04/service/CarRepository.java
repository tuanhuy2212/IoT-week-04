package com.huy.weekly04.service;

import com.huy.weekly04.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("select r from Car r WHERE license_plate = :carLicensePlate")
    Car findCarByCarLicensePlate(@Param("carLicensePlate")String  carLicensePlate);

    @Transactional
    @Modifying
    @Query("delete from Car r WHERE license_plate = :carLicensePlate")
    void deleteCarByCarLicensePlate(@Param("carLicensePlate")String  carLicensePlate);
}
