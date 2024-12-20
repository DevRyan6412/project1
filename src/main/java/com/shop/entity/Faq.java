package com.shop.entity;
// 엔티티 클래스가 위치하는 패키지.

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
// @Entity: 이 클래스가 JPA 엔티티임을 나타낸다.
// 데이터베이스의 테이블과 매핑되며, 테이블명은 클래스명(Faq)과 동일하게 설정된다.

@Getter
// @Getter: 모든 필드에 대한 getter 메서드를 자동으로 생성한다.

@Setter
// @Setter: 모든 필드에 대한 setter 메서드를 자동으로 생성한다.

public class Faq {
    // Faq: 자주 묻는 질문(FAQ) 데이터를 저장하는 엔티티 클래스.

    @Id
    // @Id: 이 필드가 테이블의 기본 키(Primary Key)임을 나타낸다.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue: 기본 키 값을 자동으로 생성하도록 설정.
    // GenerationType.IDENTITY: 데이터베이스의 AUTO_INCREMENT를 사용한다.
    private Long id;
    // FAQ의 고유 ID(식별자)를 저장하는 필드.

    @Column(nullable = false)
    // @Column: 테이블의 컬럼과 매핑된다.
    // nullable = false: 이 필드는 반드시 값이 있어야 한다.
    private String question;
    // FAQ의 질문 내용을 저장하는 필드.

    @Column(nullable = false)
    // nullable = false: 이 필드는 반드시 값이 있어야 한다.
    private String answer;
    // FAQ의 답변 내용을 저장하는 필드.

    @CreatedDate
    // @CreatedDate: 엔티티가 생성될 때 자동으로 현재 시간이 설정된다.
    private LocalDateTime createdAt;
    // FAQ가 생성된 날짜와 시간을 저장하는 필드.

    @LastModifiedDate
    // @LastModifiedDate: 엔티티가 수정될 때 자동으로 현재 시간이 설정된다.
    private LocalDateTime updatedAt;
    // FAQ가 마지막으로 수정된 날짜와 시간을 저장하는 필드.

    @Column(nullable = false)
    private boolean isDeleted = false;
    // 삭제 여부를 나타내는 필드. 기본값은 false(삭제되지 않음).
}
