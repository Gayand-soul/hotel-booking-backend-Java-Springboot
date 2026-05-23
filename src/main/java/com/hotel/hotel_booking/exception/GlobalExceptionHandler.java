package com.hotel.hotel_booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    //Helper: builds the timestamp, status, messsage map...........
    private Map<String, Object> buildError(HttpStatus status, String message){
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", status.value());
        error.put("message", message);
        return error;
    }

    //409 Conflict................
    @ExceptionHandler(RoomFullyBookedException.class)
    public ResponseEntity<Map<String, Object>> handleRoomFully(RoomFullyBookedException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }
    //400 Bad Request..............
    @ExceptionHandler(GuestCapacityException.class)
    public ResponseEntity<Map<String, Object>> handleCapacity(GuestCapacityException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
    //404 NOT FOUND..................
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(BookingNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }
    //400 Validation Error..............
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex){

        //all error message in one string
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .reduce("", (a,b) -> a.isEmpty()? b: a +","+ b);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
                .body((buildError(HttpStatus.BAD_REQUEST, message)));


    }

}
