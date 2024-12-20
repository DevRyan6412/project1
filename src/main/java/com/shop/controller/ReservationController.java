package com.shop.controller;


import com.shop.dto.ReservationRequestDto;
import com.shop.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")  // URL 경로 수정
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")  // CORS 설정 추가
public class ReservationController {

    private final ReservationService reservationService;
    // ReservationService를 주입받아 예약 관련 비즈니스 로직을 처리한다.
    // final 키워드로 선언되었으므로 생성자를 통해 객체가 주입된다.

    /**
     * 예약 생성
     * @param reservationRequestDto 요청 DTO
     * @return 성공 메시지
     */
    @PostMapping
    // HTTP POST 요청을 처리하며, URL 경로는 기본 경로 (/reservation)를 사용한다.
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        // @RequestBody: 클라이언트가 보낸 JSON 데이터를 ReservationRequestDto 객체로 변환한다.
        reservationService.createReservation(reservationRequestDto);
        // ReservationService의 createReservation() 메서드를 호출해 예약을 생성한다.

        return ResponseEntity.ok("예약이 성공적으로 생성되었습니다.");
        // HTTP 200 OK 상태와 함께 성공 메시지를 반환한다.
    }

    /**
     * 모든 예약 조회
     * @return 예약 목록
     */
    @GetMapping
    // HTTP GET 요청을 처리하며, URL 경로는 기본 경로 (/reservation)를 사용한다.
    public ResponseEntity<?> getAllReservations() {
        // ReservationService를 호출해 모든 활성화된 예약 목록을 가져온다.
        return ResponseEntity.ok(reservationService.getAllReservations());
        // HTTP 200 OK 상태와 함께 예약 목록을 반환한다.
    }

    /**
     * 특정 예약 조회
     * @param id 예약의 ID
     * @return 해당 예약 정보
     */
    @GetMapping("/{id}")
    // HTTP GET 요청을 처리하며, URL 경로에 예약 ID를 포함한다. 예: /reservation/{id}
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        // @PathVariable: URL 경로에서 전달된 {id} 값을 메서드의 인자로 받는다.
        return ResponseEntity.ok(reservationService.getReservation(id));
        // ReservationService를 통해 특정 ID의 예약 정보를 가져와 반환한다.
        // HTTP 200 OK 상태와 함께 예약 정보를 반환한다.
    }
}
