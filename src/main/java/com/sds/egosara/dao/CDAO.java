package com.sds.egosara.dao;

import com.sds.egosara.dto.CMEMBER;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CDAO {

    // 회원가입
    int cJoin(CMEMBER cmember);

    // 아이디 중복 검사
    String idOverlap(String cId);

    // 닉네임 중복 검사
    String nickOverlap(String cNickname);

    // 회원 로그인
    CMEMBER cLogin(CMEMBER cmember);

    // 회원 목록
    List<CMEMBER> cList();

    // 회원 내정보 보기
    CMEMBER cView(String cId);

    // 회원 수정
    int cModify(CMEMBER cmember);

    // 회원 삭제
    int cDelete(String cId);


}
