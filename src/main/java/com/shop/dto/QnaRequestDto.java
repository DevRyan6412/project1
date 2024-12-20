package com.shop.dto;
// 이 클래스가 속한 패키지: 데이터 전송 객체(DTO)를 관리하는 "dto" 패키지에 위치함.

import lombok.Getter;
import lombok.Setter;
// Lombok 라이브러리를 사용해 getter와 setter 메서드를 자동으로 생성한다.

@Getter
@Setter
// @Getter: 모든 필드에 대해 getter 메서드를 자동으로 생성.
// @Setter: 모든 필드에 대해 setter 메서드를 자동으로 생성.

public class QnaRequestDto {
    // QnaRequestDto: QnA 작성 요청 시 사용되는 데이터 전송 객체(DTO) 클래스.

    private String name;
    // 작성자의 이름을 저장하는 필드.

    private String email;
    // 작성자의 이메일 주소를 저장하는 필드.

    private String title;
    // QnA 제목을 저장하는 필드.

    private String content; // 추가
    // QnA 내용(질문)을 저장하는 필드.
}
