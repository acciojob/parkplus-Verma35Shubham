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

        try {
            if (!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()) {
                throw new Exception("Cannot make reservation");
            }
            User user = userRepository3.findById(userId).get();
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            List<Spot> spotList = parkingLot.getSpotList();
            boolean checkForSpots = false;
            for (Spot spot : spotList){
                if(!spot.getOccupied()){
                    checkForSpots = true;
                    break;
                }
            }
            if(!checkForSpots){
                throw new Exception("Cannot make reservation");
            }

            SpotType requestSpotType ;

            if(numberOfWheels > 4){
                requestSpotType = SpotType.OTHERS;
            } else if (numberOfWheels > 2) {
                requestSpotType = SpotType.FOUR_WHEELER;
            }
            else {
                requestSpotType = SpotType.TWO_WHEELER;
            }

            int minimumPrice = Integer.MAX_VALUE;
            checkForSpots = false;
            Spot spotchosen = null;

            for (Spot spot : spotList){
                if(requestSpotType.equals(spot.getSpotType())){
                    if(spot.getPricePerHour()*timeInHours < minimumPrice && !spot.getOccupied()){
                        minimumPrice = spot.getPricePerHour()*timeInHours;
                        checkForSpots = true;
                        spotchosen = spot;
                    }
                }
            }

            if (!checkForSpots){
                throw new Exception("Cannot make reservation");
            }
            assert spotchosen != null;
            spotchosen.setOccupied(true);

            Reservation reservation = new Reservation();
            reservation.setNumberOfHours(timeInHours);
            reservation.setSpot(spotchosen);
            reservation.setUser(user);

            spotchosen.getReservationList().add(reservation);
            user.getReservationList().add(reservation);

            userRepository3.save(user);
            spotRepository3.save(spotchosen);

            return reservation;
        } catch (Exception e) {
            return null;
        }

    }
}
