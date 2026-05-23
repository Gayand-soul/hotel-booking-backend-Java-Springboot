package com.hotel.hotel_booking.exception;




public class GuestCapacityException extends RuntimeException {

    public GuestCapacityException(String roomType, int max,int requested){
        super("Room type "+ roomType + " allows max "+ max + " guests, but " +
                requested + " were requested. ");
    }
}
