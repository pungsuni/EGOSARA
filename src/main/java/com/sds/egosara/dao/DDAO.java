package com.sds.egosara.dao;

import com.sds.egosara.dto.DIB;
import com.sds.egosara.dto.GOODS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DDAO {
    // 상품 번호에 해당하는 아이디 리스트를 찜 테이블에서 가져오기
    List<DIB> checkDIB(int dNum);

    // ooDIB : 찜 실행
    // DId는 로그인한 아이디로, DNum은 상품번호로 insert
    int ooDIB(DIB dib);

    // xxDIB : 찜 취소
    // DId는 로그인한 아이디로, DNum은 상품번호로 delete
    int xxDIB(DIB dib);

    // getDibList : 찜 목록 불러오기
    List<GOODS> getDibList(String dId);

    // 내 찜 리스트에서 찜한 것 삭제
    int dDelete(DIB dib);
}