package com.shop.controller;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.dto.CartOrderDto;
import com.shop.entity.Member;
import com.shop.service.CartService;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;



    // 장바구니에 상품 추가
    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity<?> addCart(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }


        String email = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 조회
    @GetMapping(value = "/cart")
    public String cartHist(Principal principal, Model model) {

        // 회원 정보 추가
        if (principal != null) {
            String email = principal.getName();
            Member member = memberService.findByEmail(email);
            model.addAttribute("member", member); // 회원 정보를 모델에 추가
        }

        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }

    // 장바구니 아이템 수량 업데이트
    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> updateCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestParam int count, Principal principal) {
        if (count <= 0) {
            return new ResponseEntity<>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            cartService.updateCartItemCount(cartItemId, count);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 아이템 삭제
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        try {
            cartService.deleteCartItem(cartItemId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cartItemId, HttpStatus.OK);
    }

    // 장바구니에서 주문 진행
//    @PostMapping(value = "/cart/orders")
//    public @ResponseBody ResponseEntity<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
//        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
//
//        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
//            return new ResponseEntity<>("주문할 상품을 선택 해주세요", HttpStatus.FORBIDDEN);
//        }
//        for (CartOrderDto cartOrder : cartOrderDtoList) {
//            if (!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
//                return new ResponseEntity<>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
//            }
//        }
//
//        try {
//            Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
//            return new ResponseEntity<>(orderId, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity<?> orderCartItem(Principal principal) {
        try {
            // 장바구니 전체 아이템을 주문 처리하도록 null을 전달
            Long orderId = cartService.orderCartItem(null, principal.getName());
            return new ResponseEntity<>(orderId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    // 마일리지 적용 후 장바구니 금액 계산
    @PutMapping(value = "/cart/apply-mileage")
    public @ResponseBody ResponseEntity<?> applyMileageToCart(@RequestParam Long memberId, @RequestParam int mileageToUse, Principal principal) {
        try {
            cartService.applyMileageToCart(memberId, mileageToUse);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
