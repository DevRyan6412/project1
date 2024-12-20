package com.shop.controller;

import com.shop.dto.QnaRequestDto;
import com.shop.dto.QnaResponseDto;
import com.shop.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller // 템플릿 뷰 반환을 위해 @Controller 사용
@RequestMapping("/qna") // Q&A 관련 URL 매핑
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    /**
     * QnA 생성 (JSON 데이터를 통한 요청)
     * @param qnaRequestDto QnA 생성 요청 DTO
     * @return 성공 메시지
     */
    @PostMapping("/create")
    @ResponseBody // JSON 데이터를 반환할 경우 필요
    public String createQna(@RequestBody QnaRequestDto qnaRequestDto) {
        qnaService.createQna(qnaRequestDto);
        return "QnA가 성공적으로 생성되었습니다.";
    }

    /**
     * QnA 답변 등록 또는 수정
     * @param id QnA의 ID
     * @param request 답변 내용(JSON)
     * @return 성공 메시지
     */
    @PutMapping("/{id}/answer")
    @ResponseBody
    public String updateAnswer(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String answer = request.get("answer");

        if (answer == null || answer.trim().isEmpty()) {
            return "답변 내용은 필수입니다.";
        }

        qnaService.updateAnswer(id, answer);
        return "답변이 성공적으로 등록되었습니다.";
    }

    /**
     * 모든 QnA 조회 (JSON 데이터 반환)
     * @return QnA 목록 (JSON 형식)
     */
    @GetMapping("/all")
    @ResponseBody
    public List<QnaResponseDto> getAllQnas() {
        return qnaService.getAllQnas();
    }

    /**
     * QnA 페이지 렌더링 (관리자 페이지용)
     * @param model QnA 목록 데이터를 전달
     * @return qna.html 뷰 반환
     */
    @GetMapping("/admin")
    public String qnaList(Model model) {
        List<QnaResponseDto> qnaList = qnaService.getAllQnas();
        model.addAttribute("qnaList", qnaList); // 화면에 전달할 데이터
        return "qna"; // templates/qna.html 반환
    }
}
