package com.shop.entity;
// 엔티티 클래스가 위치하는 패키지.


import com.shop.constant.QnaStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
// @Entity: 이 클래스가 JPA 엔티티임을 나타낸다. 데이터베이스의 테이블과 매핑된다.

@Getter
// @Getter: 모든 필드에 대한 getter 메서드를 자동으로 생성한다.

@Setter
// @Setter: 모든 필드에 대한 setter 메서드를 자동으로 생성한다.

public class Qna {
    // Qna: 질문과 답변 데이터를 저장하는 엔티티 클래스.

    @Id
    // @Id: 이 필드가 테이블의 기본 키(Primary Key)임을 나타낸다.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue: 기본 키 값을 자동으로 생성하도록 설정.
    // GenerationType.IDENTITY: 데이터베이스의 AUTO_INCREMENT 기능을 사용한다.
    private Long id;
    // QnA 항목의 고유 ID(식별자).

    @Column(nullable = false)
    // @Column: 테이블의 컬럼과 매핑된다.
    // nullable = false: 이 필드는 반드시 값이 있어야 한다.
    private String name;
    // 질문 작성자의 이름을 저장하는 필드.

    @Column(nullable = false)
    private String email;
    // 질문 작성자의 이메일을 저장하는 필드.

    @Column(nullable = false)
    private String title;
    // 질문의 제목을 저장하는 필드.

    @Column(nullable = false)
    private String content;
    // 질문의 내용을 저장하는 필드.

    private String answer;
    // 답변 내용을 저장하는 필드.

    @Enumerated(EnumType.STRING)
    // @Enumerated: 필드의 값이 Enum 타입임을 나타낸다.
    // EnumType.STRING: Enum의 이름을 문자열로 저장한다.
    private QnaStatus status = QnaStatus.PENDING;
    // 질문 상태를 저장하는 필드. 기본값은 PENDING(답변 대기 상태).

    @CreatedDate
    // @CreatedDate: 엔티티가 생성될 때 자동으로 현재 시간을 설정한다.
    private LocalDateTime createdAt;
    // 질문이 생성된 날짜와 시간을 저장하는 필드.

    @LastModifiedDate
    // @LastModifiedDate: 엔티티가 수정될 때 자동으로 현재 시간을 설정한다.
    private LocalDateTime updatedAt;
    // 질문이 마지막으로 수정된 날짜와 시간을 저장하는 필드.

    private boolean isDeleted = false;
    // 삭제 여부를 나타내는 필드. 기본값은 false(삭제되지 않음).

//    private Member member;
}
