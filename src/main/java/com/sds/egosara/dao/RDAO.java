package com.sds.egosara.dao;

import com.sds.egosara.dto.RCOMMENT;
import com.sds.egosara.dto.RECIPE;
import com.sds.egosara.dto.RECOMMENT;
import com.sds.egosara.dto.RLIKE;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RDAO {

    // 레시피 작성
    int rWrite(RECIPE recipe);

    // 레시피 목록
    List<RECIPE> rList();

    // 레시피 조회수 증가
    void rHit(int rNum);

    // 레시피 상세보기
    RECIPE rView(int rNum);

    // 레시피 수정
    int rModify(RECIPE recipe);

    // 레시피 삭제
    int rDelete(int rNum);

    // 나의 좋아요 클릭 여부
    String sLike(RLIKE like);

    // 게시글 좋아요 결과 가져오기
    String getLike(int lrNum);

    // 처음 좋아요 활성화 (0)
    void setLike1(RLIKE like);

    // 좋아요 on (1)
    void onLike(RLIKE like);

    // 게시글 전체 좋아요 +1
    void plus1(RLIKE like);

    // 저장된 좋아요가 무엇인지 확인
    RLIKE result(RLIKE like);

    // 좋아요 off(2)
    void offLike(RLIKE like);

    // 게시글 전체 좋아요 -1
    void minus1(RLIKE like);

    // 댓글리스트 불러오기
    List<RCOMMENT> rcList(int rNo);

    // 중복 댓글 아이디 처리
    String rIdCheck(RCOMMENT rcomment);

    // 댓글 작성
    int rcWrite(RCOMMENT rcomment);

    // 별점 총점 업데이트
    void rAvg(RCOMMENT rcomment);

    // 만약 리스트가 없다면!
    void zero(int rNo);

    // 댓글 삭제
    int rcDelete(RCOMMENT rcomment);

    // 대댓글 작성
    int reWrite(RECOMMENT recomment);

    // 대댓글 리스트 불러오기
    List<RECOMMENT> reList(RECOMMENT recomment);


    // 요리이름으로 검색
    List<RECIPE> rSearchName(String selectVal, String keyword);

    // 아이디로 검색
    List<RECIPE> rSearchId(String selectVal, String keyword);

    // 해시태그 검색
    List<RECIPE> rSearchTag(String selectVal, String keyword);

    //
    // 모두 상세검색
    List<RECIPE> all();

    // 시간으로만 검색
    List<RECIPE> rTime(String rTime);

    // 메인메뉴로만 검색
    List<RECIPE> rFood(String rFood);

    // 메인메뉴, 시간으로 검색
    List<RECIPE> rFT(String rFood, String rTime);

    // 종류로만 검색
    List<RECIPE> rType(String rType);

    // 종류, 시간으로 검색
    List<RECIPE> rTT(String rType, String rTime);

    // 종류, 메인으로 검색
    List<RECIPE> rTF(String rType, String rFood);

    // 모두 상세검색
    List<RECIPE> rSelect(String rType, String rFood, String rTime);



}
