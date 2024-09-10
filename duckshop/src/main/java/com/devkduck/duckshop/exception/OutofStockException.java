package com.devkduck.duckshop.exception;

public class OutofStockException extends RuntimeException{

    //상품의 주문 수량보다 재고의 수가 적을 때 발생
    public OutofStockException(String message){
        super(message);
    }
}
