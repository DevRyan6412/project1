package com.shop.repository;


import com.shop.constant.QnaStatus;
import com.shop.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    // JpaRepository를 상속받아 Qna 엔티티를 Long 타입 ID로 관리
    // 기본적인 CRUD 메서드(저장, 조회, 수정, 삭제) 사용 가능

    @Query("SELECT q FROM Qna q WHERE q.isDeleted = false")
        // JPQL 쿼리를 직접 작성:
        // Qna 엔티티에서 isDeleted가 false인(삭제되지 않은) 문의(Qna)만 선택하는 쿼리
    List<Qna> findAllActive();

    // 메서드 이름으로 쿼리 생성:
    // QnA 상태(status)가 특정 상태이고 삭제되지 않은(isDeleted=false) 문의 목록을 가져옴
    List<Qna> findByStatusAndIsDeletedFalse(QnaStatus status);

    // 메서드 이름으로 쿼리 생성:
    // 특정 이름(name)을 가진 QnA 중에서 삭제되지 않은(isDeleted=false) 문의 목록을 반환
    List<Qna> findByNameAndIsDeletedFalse(String name);
}
