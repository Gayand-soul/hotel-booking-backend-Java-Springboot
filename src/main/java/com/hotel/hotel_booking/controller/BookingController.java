package com.hotel.hotel_booking.controller;

import com.hotel.hotel_booking.model.Booking;
import com.hotel.hotel_booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    //instance variable
    private  final BookingService bookingService;

    //constructor
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //POST /api/bookings-USER and ADMIN
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking){

        Booking created = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //GET /api/bookings-ADMIN ONLY
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    //Delete /api/bookings/{id} - ADMIN ONLY
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){

        boolean deleted = bookingService.deleteBooking(id);

        //noContent gives 204 - success, notFound gives 404.
        return deleted ? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }


}
