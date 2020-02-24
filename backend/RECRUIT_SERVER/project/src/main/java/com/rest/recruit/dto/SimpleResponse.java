package com.rest.recruit.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SimpleResponse {

    public static ResponseEntity ok() { return SimpleResponse.msg(HttpStatus.OK); }

    public static ResponseEntity ok(Object o) {
        return SimpleResponse.msg(HttpStatus.OK, o);
    }

    public static ResponseEntity msg(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }

    public static ResponseEntity msg(HttpStatus httpStatus, Object o) {
        return ResponseEntity.status(httpStatus).body(o);
    }

}

