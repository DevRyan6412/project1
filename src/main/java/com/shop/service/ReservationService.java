package com.shop.service;
// 패키지 선언: 이 클래스(ReservationService)가 위치한 패키지를 지정한다.


import com.shop.dto.ReservationRequestDto;
import com.shop.entity.Reservation;
import com.shop.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// 예약 목록을 반환할 때 List 컬렉션을 사용하기 위해 임포트한다.

@Service
// 스프링 빈으로 등록되는 서비스 클래스임을 명시.
// 스프링이 이 클래스를 관리하고, 다른 곳에서 주입받을 수 있게 한다.

@RequiredArgsConstructor
// Lombok 어노테이션: final 필드에 대해 생성자를 자동으로 생성한다.
// ReservationRepository는 final로 선언했으므로, 별도의 생성자 코드 없이도 DI가 가능해진다.

@Transactional(readOnly = true)
// 클래스 레벨에 선언된 트랜잭션 설정: 기본적으로 모든 메서드가 읽기 전용 트랜잭션을 사용한다.
// 데이터를 변경하는 메서드에는 별도로 @Transactional을 붙여 읽기 전용을 해제한다.

public class ReservationService {

    private final ReservationRepository reservationRepository;
    // ReservationRepository를 주입받아 DB 접근을 수행한다.
    // final로 선언했기 때문에 @RequiredArgsConstructor를 통해 생성자 자동 주입된다.

    @Transactional
    // 예약을 생성하는 메서드이므로 데이터 변경이 발생한다.
    // 읽기 전용 트랜잭션을 쓰기 가능 트랜잭션으로 오버라이드한다.
    public void createReservation(ReservationRequestDto reservationRequestDto) {
        // ReservationRequestDto를 통해 전달받은 예약 정보를 Reservation 엔티티로 변환한다.
        Reservation reservation = reservationRequestDto.toEntity();
        // 변환한 엔티티를 DB에 저장한다. 트랜잭션 종료 시점에 실제 DB에 반영된다.
        reservationRepository.save(reservation);
    }

    // 추가된 부분
    // 모든 활성화된(active) 예약 리스트를 가져오는 메서드
    public List<Reservation> getAllReservations() {
        // findAllActive()는 레포지토리에서 활성화된 예약만 조회하도록 구현된 메서드라고 가정.
        // DB로부터 활성 상태의 Reservation 리스트를 가져와 반환한다.
        return reservationRepository.findAllActive();
    }

    // 특정 ID에 해당하는 예약 정보를 가져오는 메서드
    public Reservation getReservation(Long id) {
        // 예약 아이디로 예약 정보를 검색한다.
        // 만약 해당 ID의 예약이 없으면 예외를 발생시킨다.
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("예약을 찾을 수 없습니다."));
    }
}
