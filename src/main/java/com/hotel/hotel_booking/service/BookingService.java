package com.hotel.hotel_booking.service;

import com.hotel.hotel_booking.exception.GuestCapacityException;
import com.hotel.hotel_booking.exception.RoomFullyBookedException;
import com.hotel.hotel_booking.model.Booking;
import com.hotel.hotel_booking.model.RoomInventory;
import com.hotel.hotel_booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomInventory roomInventory;

    public BookingService(BookingRepository bookingRepository, RoomInventory roomInventory){
        this.bookingRepository = bookingRepository;
        this.roomInventory = roomInventory;
    }

    private double calculatePricePerNight(String roomType) {
        return switch (roomType) {
            case "SingleRoom" -> 100.0;
            case "DoubleRoom" -> 200.0;
            case "Suite"      -> 300.0;
            default -> throw new IllegalArgumentException("Unknown room type: " + roomType);
        };
    }

    private long calculateNights(String checkIn, String checkOut) {
        LocalDate in  = LocalDate.parse(checkIn);
        LocalDate out = LocalDate.parse(checkOut);
        long nights = ChronoUnit.DAYS.between(in, out);
        if (nights <= 0) throw new IllegalArgumentException("Check-out must be after check-in");
        return nights;
    }

    private void validateCapacity(String roomType, int numberOfGuests) {
        int max = switch (roomType) {
            case "SingleRoom" -> 1;
            case "DoubleRoom" -> 2;
            case "Suite"      -> 3;
            default -> throw new IllegalArgumentException("Unknown room type: " + roomType);
        };
        if (numberOfGuests > max) {
            throw new GuestCapacityException(roomType, max, numberOfGuests);
        }
    }

    public Booking createBooking(Booking booking) {
        validateCapacity(booking.getRoomType(), booking.getNumberOfGuests());

        boolean reserved = roomInventory.decreaseRoom(booking.getRoomType());
        if (!reserved) {
            throw new RoomFullyBookedException(booking.getRoomType());
        }

        long nights     = calculateNights(booking.getCheckIn(), booking.getCheckOut());
        double perNight = calculatePricePerNight(booking.getRoomType());
        booking.setTotalPrice(perNight * nights);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }


    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id);
    }


    public boolean deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id);
        bookingRepository.deleteById(id);
        roomInventory.increaseRoom(booking.getRoomType());
        return true;
    }
}