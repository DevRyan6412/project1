package com.shop.dto;
// DTO(Data Transfer Object)를 관리하는 패키지에 위치함.

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
// @Getter: 모든 필드에 대한 getter 메서드를 자동으로 생성한다.

@AllArgsConstructor
// @AllArgsConstructor: 모든 필드를 초기화하는 생성자를 자동으로 생성한다.

public class QnaResponseDto {
    // QnaResponseDto: QnA 조회 결과를 클라이언트에 반환하기 위한 DTO 클래스.

    private Long id;
    // QnA의 고유 ID (식별자)를 저장하는 필드.

    private String question;
    // 질문 내용을 저장하는 필드 (실제로는 'title'이 사용될 예정).

    private String user;
    // 작성자 이름을 저장하는 필드 (실제로는 'name'이 사용될 예정).

    private String content;
    // QnA의 본문 내용을 저장하는 필드.

    private String answer;
    // QnA에 대한 답변을 저장하는 필드.
}
