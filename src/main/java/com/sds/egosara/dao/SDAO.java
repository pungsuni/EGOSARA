package com.sds.egosara.dao;

import com.sds.egosara.dto.SMEMBER;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SDAO {

    // 업체 회원가입
    int sJoin(SMEMBER smember);

    // 아이디 중복 검사
    String idOverlap(String sId);

    // 업체 로그인
    SMEMBER sLogin(SMEMBER smember);

    // 업체 목록
    List<SMEMBER> sList();

    // 업체 내정보 보기
    SMEMBER sView(String sId);

    // 업체 수정
    int sModify(SMEMBER smember);

    // 업체 삭제
    int sDelete(String sId);
}
