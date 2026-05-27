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


    public Booking save(Booking booking){
        if(booking.getId() == null){
            booking.setId(idCounter.getAndIncrement());
        }
        store.put(booking.getId(), booking);
        return booking;
    }

    public List<Booking> findAll(){

        return new ArrayList<>(store.values());
    }

    public Booking findById(Long id){

       Booking booking = store.get(id);
       if(booking == null){
           throw new RuntimeException("Booking not found with id: " + id);
       }
       return booking;
    }

    public boolean deleteById(Long id){

        return  store.remove(id) != null;
    }



}
