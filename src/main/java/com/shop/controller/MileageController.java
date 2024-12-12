package com.shop.controller;

import com.shop.dto.MileageHistoryDTO;
import com.shop.dto.MileageSummaryDTO;
import com.shop.dto.OrderRequestDTO;
import com.shop.service.MemberService;
import com.shop.service.MileageService;
import com.shop.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/mileage")
public class MileageController {

    private final MileageService mileageService;
    private final MemberService memberService;

    public MileageController(MileageService mileageService, MemberService memberService) {
        this.mileageService = mileageService;
        this.memberService = memberService;
    }

    // 마일리지 요약 조회 (현재 로그인한 사용자)
    @GetMapping("/summary")
    public ResponseEntity<MileageSummaryDTO> getMileageSummary() {
        Member currentMember = memberService.getCurrentLoggedInMember();

        if (currentMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        MileageSummaryDTO summary = mileageService.getMileageSummary(currentMember.getId());
        return ResponseEntity.ok(summary);
    }

    // 마일리지 내역 조회 (현재 로그인한 사용자)
    @GetMapping("/history")
    public ResponseEntity<List<MileageHistoryDTO>> getMileageHistory() {
        Member currentMember = memberService.getCurrentLoggedInMember();

        if (currentMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<MileageHistoryDTO> history = mileageService.getMileageHistory(currentMember.getId());
        return ResponseEntity.ok(history);
    }

    // 결제 완료 시 마일리지 사용 및 적립 처리
//    @PostMapping("/process-order")
//    public ResponseEntity<String> processOrder(@RequestBody OrderRequestDTO orderRequest) {
//        Member currentMember = memberService.getCurrentLoggedInMember();
//
//        if (currentMember == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
//        }
//
//        try {
//            // DTO에서 값 추출
//            int purchaseAmount = orderRequest.getPurchaseAmount();
//            int mileageUsed = orderRequest.getMileageUsed();
//
//            // 서비스 호출
//            mileageService.processOrder(currentMember.getId(), purchaseAmount, mileageUsed);
//
//            return ResponseEntity.ok("Order processed successfully.");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body("Error processing order: " + e.getMessage());
//        }
//    }
    @PostMapping("/process-order")
    public ResponseEntity<String> processOrder(@RequestBody OrderRequestDTO orderRequest,
                                               @SessionAttribute(name = "couponApplied", required = false) Boolean couponApplied,
                                               HttpSession session) {
        Member currentMember = memberService.getCurrentLoggedInMember();


        if (currentMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        // 1. 쿠폰 적용 여부 확인
        if (!Boolean.TRUE.equals(couponApplied)) {
            return ResponseEntity.badRequest().body("쿠폰을 먼저 적용해주세요.");
        }

        try {
            // DTO에서 값 추출
            int purchaseAmount = orderRequest.getPurchaseAmount();
            int mileageUsed = orderRequest.getMileageUsed();

            // 서비스 호출
            mileageService.processOrder(currentMember.getId(), purchaseAmount, mileageUsed);

            // 마일리지 적용 상태를 세션에 저장
            session.setAttribute("mileageApplied", true);

            return ResponseEntity.ok("Order processed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error processing order: " + e.getMessage());
        }
    }


}
