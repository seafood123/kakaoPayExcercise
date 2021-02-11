package org.kakaoPay.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kakaoPay.constants.Codes;
import org.kakaoPay.constants.Headers;
import org.kakaoPay.dto.PickUpDto;
import org.kakaoPay.dto.SeedingDto;
import org.kakaoPay.entity.ApiResponse;
import org.kakaoPay.entity.SprinkleRequest;
import org.kakaoPay.jpa.entity.PickUp;
import org.kakaoPay.jpa.entity.Seeding;
import org.kakaoPay.service.SeedingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/v1", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SeedingController {
    private final SeedingService seedingService;

    @PostMapping
    ResponseEntity<ApiResponse> sprinkle(
            @RequestHeader(Headers.ROOM_ID) String roomId,
            @RequestHeader(Headers.USER_ID) int userId,
            @RequestBody SprinkleRequest request,
            UriComponentsBuilder builder
            ){
        log.debug("roomId={} , userId={}, body={}",roomId, userId, request);
        Seeding seeding = seedingService.sprinkle(roomId, userId, request.getAmount(), request.getPeoples());
        HttpHeaders header = new HttpHeaders();
        header.setLocation(builder.path("/{token}").buildAndExpand(seeding.getToken()).toUri());

        ApiResponse response = ApiResponse.of(Codes.S0000, seeding.getToken());
        return new ResponseEntity<>(response,header, HttpStatus.CREATED);
    }

    @PutMapping("/{token:[a-zA-Z]{3}}")
    ApiResponse pickUp(
            @RequestHeader(Headers.ROOM_ID) String roomId,
            @RequestHeader(Headers.USER_ID) long userId,
            @PathVariable("token") String token
    ) throws Exception {
        log.debug("roomId={}, userId={}, token={},", roomId, userId, token);
        long amount = seedingService.pickUp(token, roomId, userId);

        log.debug("amount={}", amount);

        return ApiResponse.of(Codes.S0000,amount);


    }

    @GetMapping(value="/{token:[a-zA-Z]{3}}")
    ApiResponse search(
            @RequestHeader(Headers.ROOM_ID) String roomId,
            @RequestHeader(Headers.USER_ID) long userId,
            @PathVariable("token") String token
    ) throws Exception {
        Seeding seeding = seedingService.search(token, userId);

        SeedingDto dto = new SeedingDto(
                seeding.getCreateDate(),
                seeding.getAmount(),
                seeding.getPickups().stream().filter(PickUp::isReceived).mapToLong(PickUp::getAmount).sum(),
                seeding.getPickups().stream()
                        .filter(PickUp::isReceived)
                        .map(it -> new PickUpDto(it.getUserId(), it.getAmount()))
                        .collect(Collectors.toList())
        );
        return ApiResponse.of(Codes.S0000,dto);
    }



}
