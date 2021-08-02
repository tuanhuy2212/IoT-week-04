package com.huy.weekly04.controller;

import com.huy.weekly04.entities.Car;
import com.huy.weekly04.service.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/car")
public class Controller {

    @Autowired
    CarRepository carRepository;


    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    @GetMapping("")
    public ResponseEntity<?> getAllCarCame(){
        List<Car> cars = carRepository.findAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @PostMapping("/in")
    public ResponseEntity<?> addCar(@RequestBody Car car){
        String plate = car.getCarLicensePlate();
        Car _car = carRepository.findCarByCarLicensePlate(plate);
        LocalDateTime now = LocalDateTime.now();

        if(_car != null ){
            System.out.println("update-time");
            Car c = new Car();
            c.setCarLicensePlate(plate);
            c.setTimeIn(now.toString());
            c.setTimeOut(null);
            carRepository.saveAndFlush(c);
            return new ResponseEntity<>("Car exits! Update success!", HttpStatus.OK);
        }
        else {
            System.out.println("newCAR");
            carRepository.save(car);
            return new ResponseEntity<>("Created new car!", HttpStatus.OK);
        }
    }

    @PutMapping ("/out")
    public ResponseEntity<?> carOut(@RequestBody Car c){
        String plate = c.getCarLicensePlate();
        Car _car = carRepository.findCarByCarLicensePlate(plate);
        LocalDateTime now = LocalDateTime.now();
        _car.setTimeOut(now.toString());
        carRepository.saveAndFlush(_car);
        return new ResponseEntity<>("Update success!", HttpStatus.OK);
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<?> deleteVehicle (@PathVariable("plate")String plate){
        Car _car = carRepository.findCarByCarLicensePlate(plate);
        if(_car== null){
            return new ResponseEntity<>("Input id is invalid!", HttpStatus.BAD_REQUEST);
        }
        else {
            System.out.println("delete");
            System.out.println(_car.getId());
            carRepository.deleteCarByCarLicensePlate(plate);
            return new ResponseEntity<>("Delete success!", HttpStatus.OK);
        }
    }
}
