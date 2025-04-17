package com.example.gatewayservice.controller;

import com.example.gatewayservice.entity.Reservation;
import com.example.gatewayservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReservationController.class);

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        log.info("예약 요청 수신: {}", reservation);
        Reservation savedReservation = reservationService.createReservation(reservation);
        log.info("예약 저장 완료: {}", savedReservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable String userId) {
        log.info("사용자 ID로 예약 조회: {}", userId);
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        log.info("조회된 예약 수: {}", reservations.size());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("예약 취소 요청: {}", id);
        reservationService.deleteReservation(id);
        log.info("예약 취소 완료: {}", id);
        return ResponseEntity.ok().build();
    }
} 