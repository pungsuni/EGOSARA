package com.sds.egosara.dao;

import com.sds.egosara.dto.NOTICE;
import com.sds.egosara.dto.SMEMBER;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NDAO {

    // 공지 작성
    int nWrite(NOTICE notice);

    // 공지 목록
    List<NOTICE> nList();

    // 공지 상세보기
    NOTICE nView(int nNum);

    // 공지 수정
    int nModify(NOTICE notice);

    // 공지 삭제
    int nDelete(int nNum);

    List<SMEMBER> sMem();
}
