package com.hotel.hotel_booking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import com.hotel.hotel_booking.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookingRepository {

    private final Map<Long, Booking> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    //SAVE Reservations
    public Booking save(Booking booking){
        if(booking.getId() == null){
            booking.setId(idCounter.getAndIncrement());
        }
        store.put(booking.getId(), booking);
        return booking;
    }

    //Find all the Reservations
    public List<Booking> findAll(){
        return new ArrayList<>(store.values());
    }

    //Find one Reservation
    public Optional<Booking> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    //Delete a Reservation
    public boolean deleteById(Long id){
        return  store.remove(id) != null;
    }

//ConcurrentHashMap: Thread-safe map — handles concurrent requests without manual synchronized
    //AtomicLong idCounter: Thread-safe ID counter, starts at 1, auto-increments
    //save(): Assigns an ID if the booking is new, then stores it
    //findAll(): Returns a snapshot list (new ArrayList so callers can't mutate the map)
    //findById():Returns Optional<Booking> — safer than returning null
    //deletedById(): Returns boolean so the service layer can tell if the ID actually existed
    //Why Optional on findById?=When the service layer calls findById, it'll look like this (Day 4):
    //Booking booking = repository.findById(id)
    //    .orElseThrow(() -> new RuntimeException("Booking not found"));
    //This is the idiomatic Spring pattern — cleaner than a null check.

}
