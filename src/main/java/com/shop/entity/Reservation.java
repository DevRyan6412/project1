package com.shop.entity;
// 엔티티 클래스가 위치하는 패키지.


import com.shop.constant.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
// @Entity: 이 클래스가 JPA 엔티티임을 나타낸다. 데이터베이스의 테이블과 매핑된다.

@Getter
// @Getter: 모든 필드에 대한 getter 메서드를 자동으로 생성한다.

@NoArgsConstructor
// @NoArgsConstructor: 파라미터가 없는 기본 생성자를 자동으로 생성한다.

public class Reservation {
    // Reservation: 예약 정보를 저장하는 엔티티 클래스.

    @Id
    // @Id: 이 필드가 테이블의 기본 키(Primary Key)임을 나타낸다.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue: 기본 키 값을 자동으로 생성하도록 설정.
    // GenerationType.IDENTITY: 데이터베이스의 AUTO_INCREMENT 기능을 사용한다.
    private Long id;
    // 예약의 고유 ID(식별자).

    private String name;
    // 예약자의 이름을 저장하는 필드.

    private String phone;
    // 예약자의 연락처를 저장하는 필드.

    private LocalDate date;
    // 예약 날짜를 저장하는 필드.

    private LocalTime time;
    // 예약 시간을 저장하는 필드.

    private boolean isDeleted;
    // 삭제 여부를 나타내는 필드. 기본값은 false(삭제되지 않음).

    @Enumerated(EnumType.STRING)
    // @Enumerated: 필드의 값이 Enum 타입임을 나타낸다.
    // EnumType.STRING: Enum의 이름을 문자열로 저장한다.
    private ReservationStatus status;
    // 예약 상태를 저장하는 필드. (예: CONFIRMED, PENDING, CANCELLED)

    @Builder
    // @Builder: 빌더 패턴을 사용해 객체를 생성할 수 있도록 한다.
    public Reservation(String name, String phone, LocalDate date, LocalTime time, boolean isDeleted, ReservationStatus status) {
        // Reservation 객체의 생성자. 빌더 패턴에 의해 호출된다.
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.isDeleted = isDeleted;
        this.status = status;
    }

    public void updateIsDeleted(boolean isDeleted) {
        // isDeleted 필드를 업데이트하는 메서드.
        this.isDeleted = isDeleted;
    }

    public void updateStatus(ReservationStatus status) {
        // 예약 상태(status)를 업데이트하는 메서드.
        this.status = status;
    }
}
