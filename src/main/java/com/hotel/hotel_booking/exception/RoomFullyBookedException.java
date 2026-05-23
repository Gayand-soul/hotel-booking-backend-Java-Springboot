package com.hotel.hotel_booking.exception;



public class RoomFullyBookedException extends RuntimeException {

    public RoomFullyBookedException(String roomType){

        super("No available rooms of type "+ roomType + ".");
    }
}
