package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        User user;
        try {
            user = userRepository3.findById(userId).get();
        } catch (Exception e) {
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        } catch (Exception e) {
            throw new Exception("Cannot make reservation");
        }
        int totalPrice = Integer.MAX_VALUE;
        SpotType spotType = TypeOfSpotType.typeOfSpot(numberOfWheels);

        Spot updateSpot = null;
        List<Spot> spotList = parkingLot.getSpotList();
        for (Spot spot : spotList){
            int spotPrice = timeInHours*spot.getPricaPerHour();
            if (spotPrice < totalPrice && spotType.equals(spot.getSpotType()) && spot.getOccupied() == false){
                totalPrice = spotPrice;
                updateSpot = spot;
            }
        }
        if(totalPrice == Integer.MAX_VALUE){
            throw new Exception("Cannot make reservation");
        }

        updateSpot.setOccupied(true);
        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);


        parkingLot.getSpotList().add(updateSpot);
        user.getReservationList().add(reservation);

        parkingLotRepository3.save(parkingLot);
        userRepository3.save(user);
        return reservation;
    }
}
