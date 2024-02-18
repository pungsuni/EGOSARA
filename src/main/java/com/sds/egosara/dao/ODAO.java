package com.sds.egosara.dao;

import com.sds.egosara.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ODAO {

    // 상품 번호랑 아이디를 가지고 장바구니에 BSelect가 y인 데이터 리스트로 받아오기
    List<BASKET> getBasketInfo(String bId);
    // ordering 정보를 가지고 DB 테이블에 INSERT하기
    int orderingEx(ORDERING ordering);


    // 로그인 아이디로 장바구니에서 BSelect 가 'y'인거 리스트로 받아오기
    List<BASKET> bSelectList(String bId);
    // ordering에 insert완료 했으면 basket테이블에서 해당 제품 자동삭제
    int oBDelete(BASKET basket);
    // basket정보를 가져가서 BStock = BStock - BuyNo 으로 업데이트 하기
    int oStockUpdate(BASKET basket);

    // 주문내역 불러오기
    // 1. 로그인한 아이디로 주문번호 가져오기
    List<DIB> getOrderList(String oId);
    // 2. 주문번호로 상품 정보 가져오기
    List<OrderingList> getBNum(String olNum);

}
