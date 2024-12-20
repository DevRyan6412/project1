package com.shop.dto;
// DTO(Data Transfer Object)를 관리하는 패키지에 위치함.


import com.shop.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
// @Getter: 모든 필드에 대한 getter 메서드를 자동으로 생성한다.

@NoArgsConstructor
// @NoArgsConstructor: 파라미터가 없는 기본 생성자를 자동으로 생성한다.

public class ReservationRequestDto {
    // ReservationRequestDto: 예약 생성 요청 데이터를 담는 DTO 클래스.

    private String name;        // 예약자 이름을 저장하는 필드.
    private String phone;       // 예약자 연락처를 저장하는 필드.
    private LocalDate date;     // 예약 날짜를 저장하는 필드.
    private LocalTime time;     // 예약 시간을 저장하는 필드.

    public Reservation toEntity() {
        // toEntity(): DTO를 Reservation 엔티티 객체로 변환하는 메서드.
        return Reservation.builder()
                .name(name)          // 엔티티의 name 필드에 DTO의 name 값을 설정.
                .phone(phone)        // 엔티티의 phone 필드에 DTO의 phone 값을 설정.
                .date(date)          // 엔티티의 date 필드에 DTO의 date 값을 설정.
                .time(time)          // 엔티티의 time 필드에 DTO의 time 값을 설정.
                .isDeleted(false)    // 예약 생성 시 삭제 여부를 false로 설정.
                .build();            // Reservation 객체를 빌더 패턴으로 생성.
    }
}
