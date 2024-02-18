
package com.sds.egosara.dao;

import com.sds.egosara.dto.BASKET;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BDAO {
    // 장바구니에 상품 등록
    int bGetGoods(BASKET basket);
    // 내가 담은 물건을 B_View 페이지에서 확인하기
    List<BASKET> bView(String bId);
    // B_View 페이지에서 체크박스에 체크한 데이터의
    // BNum과 로그인한 아이디인 BId를 Basket bas에 담아서 BSelete를 'y'로 변경
    void bSelectIn(BASKET bas);
    // Basket에서 로그인 아이디가 BId이고, BSelete가 'y'인 것만 리스트로 받아오기
    List<BASKET> bSelectList(String bId);
    // 장바구니에서 항목 삭제
    int bDelete(BASKET basket);
}

