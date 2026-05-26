package com.hotel.hotel_booking.model;

import org.springframework.stereotype.Component;


@Component
public class RoomInventory {

    private int singleRoom = 10;
    private int doubleRoom = 7;
    private int suite = 3;


    public int getAvailableCount(RoomType type){
        return getAvailable(type.name());
    }


    public int getAvailable(String roomType) {
        return switch (roomType) {
            case "SingleRoom" -> singleRoom;
            case "DoubleRoom" -> doubleRoom;
            case "Suite" -> suite;
            default -> 0;
        };
    }

    public boolean decreaseRoom(String roomType){
        return switch (roomType){
            case "SingleRoom" -> {
                if(singleRoom > 0){
                    singleRoom--;
                    yield true;
                }
                yield false;
            }
            case "DoubleRoom" -> {
                if(doubleRoom > 0){
                    doubleRoom--;
                    yield true;
                }
                yield false;
            }
            case "Suite" -> {
                if(suite > 0){
                    suite--;
                    yield true;
                }
                yield false;
            }
            default -> false;

        };
    }

    public void increaseRoom(String roomType){
        switch (roomType){
            case "SingleRoom" -> singleRoom++;
            case "DoubleRoom" -> doubleRoom++;
            case "Suite" -> suite++;
        }
    }

}
