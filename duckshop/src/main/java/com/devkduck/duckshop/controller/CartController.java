package com.devkduck.duckshop.controller;

import com.devkduck.duckshop.dto.CartDetailDto;
import com.devkduck.duckshop.dto.CartItemDto;
import com.devkduck.duckshop.service.CartService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity oreder (@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){

        //장바구니에 담을 상품 정보 객체에 데이터 바인딩시 에러 검사
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String> (sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartItemId;

        try{
            cartItemId = cartService.addCart(cartItemDto, email);
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist (Principal principal, Model model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }


    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem (@PathVariable("cartItemId") Long cartItemId, int count, Principal principal){
        //상품 개수 0 개 이하시 에러
        if(count <=0 ){
            return new ResponseEntity <String> ("최소 한개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        }
        else if (!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String> ("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        //장바구니 상품 개수 업데이트
        cartService.updateCartItemCount(cartItemId,count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

}
