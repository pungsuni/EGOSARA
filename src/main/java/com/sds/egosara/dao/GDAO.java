package com.sds.egosara.dao;

import com.sds.egosara.dto.GCCOMENT;
import com.sds.egosara.dto.GOODS;
import com.sds.egosara.dto.ORDERING;
import com.sds.egosara.dto.RECIPE;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GDAO {
    // 추가!!!!!!!!!!
    // 인덱스에 보여질 인기상품 목록 가져오기
    List<GOODS> hotGoodsList();
    // 추가!!!!!
    // 인덱스에 보여질 인기레시피 목록 가져오기
    List<RECIPE> topRecipeList();

    // 상품 목록
    List<GOODS> gList();
    // 상품 목록 카테고리
    List<GOODS> gList1(String gCate);
    // 상품 글 쓰기
    int gWrite(GOODS goods);
    // 상품 글 조회수
    void gCount(int gNum);
    // 상품 글 자세히 보기
    GOODS gView(int gNum);
    // 상품 글 수정
    int gModify(GOODS goods);
    // 상품 삭제 전 장바구니 우선 삭제
    int gbDelete(int gNum);
    // 상품 삭제 전 상품후기 우선 삭제
    int gccDelete(int gNum);
    // 상품 삭제 전 찜 내역 우선 삭제
    int gdDelete(int gNum);
    // 상품 글 삭제
    int gDelete(GOODS goods);
    // 리뷰 목록
    List<GCCOMENT> gcList(int gcNum);
    // 리뷰 작성
    int gcWrite(GCCOMENT gccoment);
    // 리뷰 삭제
    int gcDelete(GCCOMENT gccoment);
    // 리뷰 작성자 특정
    List<ORDERING> example(GCCOMENT gccoment);
    // 상품검색
    List<GOODS> gSearch(String keyword);
    // 상품가격 검색
    List<GOODS> gPriceSearch(GOODS goods);
    // 상품의 후기 작성 시 해당 상품 별점 평균을 업데이트 하기
    void gcStarUpdate(GCCOMENT gccoment);

}
