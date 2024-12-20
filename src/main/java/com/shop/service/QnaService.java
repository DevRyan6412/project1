package com.shop.service;
// 패키지 선언부: 이 클래스(QnaService)가 속한 패키지를 지정한다.
// 여기서는 com.example.demo.service 패키지 내에 포함됨.


import com.shop.constant.QnaStatus;
import com.shop.dto.QnaRequestDto;
import com.shop.dto.QnaResponseDto;
import com.shop.entity.Qna;
import com.shop.exception.CustomException;
import com.shop.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
// Java 유틸 패키지 및 스트림 API를 사용하기 위해 임포트한다.
// List는 목록, Collectors는 스트림 결과를 특정 컬렉션으로 변환하기 위함이다.

@Service
// 스프링이 해당 클래스를 서비스 빈으로 등록하도록 하는 어노테이션.
// 비즈니스 로직을 처리하는 계층을 나타낸다.

@RequiredArgsConstructor
// Lombok 어노테이션: final 필드를 파라미터로 하는 생성자를 자동 생성한다.
// QnaRepository를 final 필드로 선언했으므로, 해당 필드에 대한 생성자를 자동 생성한다.

@Transactional(readOnly = true)
// 클래스 단위로 읽기 전용 트랜잭션을 적용한다.
// 메서드 내부에서 별도로 @Transactional을 달지 않으면 기본적으로 읽기 전용 트랜잭션이 적용된다.

public class QnaService {
    private final QnaRepository qnaRepository;
    // QnaRepository 주입: DB에 접근하여 QnA 데이터를 관리할 수 있는 레포지토리.
    // final로 선언했으므로 @RequiredArgsConstructor로 생성자 주입이 자동으로 이루어진다.

    @Transactional
    // 해당 메서드는 쓰기 작업(데이터 생성)을 하므로, 기본 readOnly = true를 오버라이드하여 일반 트랜잭션 적용.
    public void createQna(QnaRequestDto dto) {
        validateQnaRequest(dto);
        // 요청으로 들어온 QnA 데이터의 유효성 검사(이름, 이메일, 제목, 내용 모두 필수).

        Qna qna = new Qna();
        // Qna 엔티티 객체를 새로 생성한다.

        qna.setName(dto.getName());
        // Qna 엔티티에 요청 DTO로부터 받은 이름을 설정한다.

        qna.setEmail(dto.getEmail());
        // Qna 엔티티에 요청 DTO로부터 받은 이메일을 설정한다.

        qna.setTitle(dto.getTitle());
        // Qna 엔티티에 요청 DTO로부터 받은 제목을 설정한다.

        qna.setContent(dto.getContent());
        // Qna 엔티티에 요청 DTO로부터 받은 문의 내용을 설정한다.

        qna.setStatus(QnaStatus.PENDING);
        // 새로 생성되는 QnA는 기본적으로 답변 대기(PENDING) 상태로 설정한다.

        qnaRepository.save(qna);
        // 레포지토리를 통해 DB에 QnA 엔티티를 저장한다.
        // 트랜잭션이 끝날 때 실제 DB에 반영된다.
    }

    @Transactional
    // 해당 메서드는 QnA의 상태를 변경하고 답변을 업데이트 하는 쓰기 작업이므로 트랜잭션 적용.
    public void updateAnswer(Long id, String answer) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new CustomException("존재하지 않는 QnA입니다."));
        // 전달받은 id로 QnA를 찾는다. 없으면 커스텀 예외를 발생시킨다.

        qna.setAnswer(answer);
        // QnA 엔티티에 새로운 답변을 설정한다.

        qna.setStatus(QnaStatus.ANSWERED);
        // QnA 상태를 "답변 완료(ANSWERED)"로 변경한다.

        qnaRepository.save(qna);
        // 변경된 QnA 엔티티를 DB에 반영한다.
        // 실제로는 JPA 영속성 컨텍스트에 의해 dirty checking이 일어나면서
        // 트랜잭션 종료 시점에 DB에 업데이트된다.
    }

    public List<QnaResponseDto> getAllQnas() {
        return qnaRepository.findAll().stream()
                .filter(qna -> !qna.isDeleted())
                // 모든 QnA를 가져온 뒤, 삭제 상태가 아닌 QnA만 필터링한다.
                .map(this::convertToDto)
                // 필터링된 QnA 엔티티를 QnaResponseDto로 변환한다.
                .collect(Collectors.toList());
        // 변환된 DTO 객체들을 리스트로 수집하여 반환한다.
    }

    private void validateQnaRequest(QnaRequestDto dto) {
        // QnA 요청 DTO가 유효한지 검사하는 메서드.

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new CustomException("이름은 필수입니다.");
            // 이름이 비어있거나 공백이라면 커스텀 예외 발생.
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new CustomException("이메일은 필수입니다.");
            // 이메일이 비어있거나 공백이라면 커스텀 예외 발생.
        }
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new CustomException("제목은 필수입니다.");
            // 제목이 비어있거나 공백이라면 커스텀 예외 발생.
        }
        if (dto.getContent() == null || dto.getContent().trim().isEmpty()) {
            throw new CustomException("내용은 필수입니다.");
            // 내용이 비어있거나 공백이라면 커스텀 예외 발생.
        }
    }

    private QnaResponseDto convertToDto(Qna qna) {
        // QnA 엔티티를 QnaResponseDto로 변환하는 메서드.

        return new QnaResponseDto(
                qna.getId(),
                qna.getTitle(),
                qna.getName(),
                qna.getContent(),
                qna.getAnswer()
        );
        // 엔티티의 각 필드를 DTO 생성자에 전달하여 DTO를 생성하고 반환한다.
    }
}
