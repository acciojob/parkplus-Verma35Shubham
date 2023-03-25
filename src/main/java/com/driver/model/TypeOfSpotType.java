package com.driver.model;

public class TypeOfSpotType {

    public static SpotType typeOfSpot(Integer numberOfWheel){
        if(numberOfWheel > 4){
            return SpotType.OTHERS;
        }
        else if(numberOfWheel > 2){
            return SpotType.FOUR_WHEELER;
        }

        return SpotType.TWO_WHEELER;
    }
}
