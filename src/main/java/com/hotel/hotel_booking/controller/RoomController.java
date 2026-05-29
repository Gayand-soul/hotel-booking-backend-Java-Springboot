package com.hotel.hotel_booking.controller;

import com.hotel.hotel_booking.model.RoomType;
import com.hotel.hotel_booking.model.RoomInventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {


    private final RoomInventory roomInventory;

    //constructor
    public RoomController (RoomInventory roomInventory){
        this.roomInventory = roomInventory;
    }


    @GetMapping
    public ResponseEntity<Map <String, Integer>> getAvailableRooms(){

        Map<String, Integer> availability = new LinkedHashMap<>();
        for(RoomType type: RoomType.values()){
            availability.put(type.name(), roomInventory.getAvailableCount(type));
        }
        return ResponseEntity.ok(availability);
    }
}
