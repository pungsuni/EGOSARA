package com.sds.egosara.dao;

import com.sds.egosara.dto.QCOMMENT;
import com.sds.egosara.dto.QNA;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QDAO {

    // 문의 작성
    int qWrite(QNA qna);

    // 문의 목록
    List<QNA> qList();

    // 문의 조회수
    void qCount(int qNum);

    // 문의 상세보기
    QNA qView(int qNum);

    // 문의 수정
    int qModify(QNA qna);

    // 문의 삭제
    int qDelete(int qNum);

    // 댓글 목록
    List<QCOMMENT> qcList(int qcbNum);

    // 댓글 작성
    int qcWrite(QCOMMENT qcomment);

    // 댓글 삭제
    int qcDelete(QCOMMENT qcomment);


    List<QNA> qList1();
}
