package com.driver.model;

public class TypeOfSpotType {

    public static SpotType typeOfSpot(Integer numberOfWheel){
        if(numberOfWheel == 2){
            return SpotType.TWO_WHEELER;
        }
        else if(numberOfWheel == 4){
            return SpotType.FOUR_WHEELER;
        }

        return SpotType.OTHERS;
    }
}
