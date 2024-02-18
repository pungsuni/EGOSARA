package com.sds.egosara.service;

import com.sds.egosara.dao.RDAO;
import com.sds.egosara.dto.RCOMMENT;
import com.sds.egosara.dto.RECIPE;
import com.sds.egosara.dto.RECOMMENT;
import com.sds.egosara.dto.RLIKE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RService {

    @Autowired
    private RDAO dao;

    private ModelAndView mav = new ModelAndView();

    // 레시피 작성하기
    public ModelAndView rWrite(RECIPE recipe) throws IOException {



        //(1) 파일 불러오기
        MultipartFile rPhoto1 = recipe.getRPhoto1();
        MultipartFile rPhoto2 = recipe.getRPhoto2();
        MultipartFile rPhoto3 = recipe.getRPhoto3();

        // (2) 파일이름 설정하기
        String originalFileName1 = rPhoto1.getOriginalFilename();
        String originalFileName2 = rPhoto2.getOriginalFilename();
        String originalFileName3 = rPhoto3.getOriginalFilename();

        // (3) 난수 생성하기                              0 ~ 8번째 전 글자까지(8자리)
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기 : asdfgh11_사진이름.png
        String rPhotoName1 = uuid + "_" + originalFileName1;
        String rPhotoName2 = uuid + "_" + originalFileName2;
        String rPhotoName3 = uuid + "_" + originalFileName3;

        // (5) 파일 저장위치
        String savaPath1 = "C:/Users/rlamy/IdeaProjects/EGOSARA/src/main/resources/static/img/recipe/" + rPhotoName1;
        String savaPath2 = "C:/Users/rlamy/IdeaProjects/EGOSARA/src/main/resources/static/img/recipe/" + rPhotoName2;
        String savaPath3 = "C:/Users/rlamy/IdeaProjects/EGOSARA/src/main/resources/static/img/recipe/" + rPhotoName3;

        // (6) 파일 선택여부 / 저장

        recipe.setRPhotoName1(rPhotoName1);
        rPhoto1.transferTo(new File(savaPath1));

        recipe.setRPhotoName2(rPhotoName2);
        rPhoto2.transferTo(new File(savaPath2));

        recipe.setRPhotoName3(rPhotoName3);
        rPhoto3.transferTo(new File(savaPath3));


        int result = dao.rWrite(recipe);

        if(result > 0){
            // 성공
            mav.setViewName("redirect:/rView?rNum=" + recipe.getRNum());
        } else{
            // 실패
            mav.setViewName("redirect:/rWrite?rNum=" + recipe.getRNum());
        }

        return mav;
    }


    // 레시피 목록
    public ModelAndView rList() {

        List<RECIPE> recipeList = dao.rList();



        mav.addObject("recipeList", recipeList);

        mav.setViewName("R_List");

        return mav;
    }

    // 레시피 자세히 보기
    public ModelAndView rView(int rNum) {

        // 조회수 증가
        dao.rHit(rNum);

        RECIPE recipe = dao.rView(rNum);

        if(recipe != null){
            mav.addObject("recipe", recipe);
            mav.setViewName("R_View");
        } else{
            mav.setViewName("redirect:/rList");
        }

        return mav;
    }


    // 레시피 수정 페이지로 이동
    public ModelAndView rModiForm(int rNum) {

        RECIPE recipe = dao.rView(rNum);

        if(recipe != null){
            mav.addObject("recipe",recipe);
            mav.setViewName("R_Modify");
        } else{
            mav.setViewName("redirect:/rList");
        }

        return mav;
    }

    // 레시피 수정
    public ModelAndView rModify(RECIPE recipe) throws IOException {

        //(1) 파일 불러오기
        MultipartFile rPhoto1 = recipe.getRPhoto1();
        MultipartFile rPhoto2 = recipe.getRPhoto2();
        MultipartFile rPhoto3 = recipe.getRPhoto3();

        // (2) 파일이름 설정하기
        String originalFileName1 = rPhoto1.getOriginalFilename();
        String originalFileName2 = rPhoto2.getOriginalFilename();
        String originalFileName3 = rPhoto3.getOriginalFilename();

        // (3) 난수 생성하기                              0 ~ 8번째 전 글자까지(8자리)
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기 : asdfgh11_사진이름.png
        String rPhotoName1 = uuid + "_" + originalFileName1;
        String rPhotoName2 = uuid + "_" + originalFileName2;
        String rPhotoName3 = uuid + "_" + originalFileName3;

        // (5) 파일 저장위치
        String savaPath1 = "C:/Users/rlamy/IdeaProjects/EGOSARA/src/main/resources/static/img/recipe/" + rPhotoName1;
        String savaPath2 = "C:/Users/rlamy/IdeaProjects/EGOSARA/src/main/resources/static/img/recipe/" + rPhotoName2;
        String savaPath3 = "C:/Users/rlamy/IdeaProjects/EGOSARA/src/main/resources/static/img/recipe/" + rPhotoName3;

        // (6) 파일 선택여부 / 저장
        if(!rPhoto1.isEmpty()){
            // 파일을 선택했다면
            recipe.setRPhotoName1(rPhotoName1);
            rPhoto1.transferTo(new File(savaPath1));
        } else{
            // 파일을 선택하지 않았다면
            rPhoto1.transferTo(new File("logo6.jpg"));
        }

        if(!rPhoto2.isEmpty()){
            recipe.setRPhotoName2(rPhotoName2);
            rPhoto2.transferTo(new File(savaPath2));
        } else{
            rPhoto2.transferTo(new File("logo6.jpg"));
        }

        if(!rPhoto3.isEmpty()){
            recipe.setRPhotoName3(rPhotoName3);
            rPhoto3.transferTo(new File(savaPath3));
        } else {
            rPhoto3.transferTo(new File("logo6.jpg"));
        }

        int result = dao.rModify(recipe);


        if(result>0){
            mav.setViewName("redirect:/rView?rNum=" + recipe.getRNum());
        } else{
            mav.setViewName("redirect:/rModifForm?rNum=" + recipe.getRNum());
        }

        return mav;
    }

    // 레시피 삭제
    public ModelAndView rDelete(int rNum) {

        int result = dao.rDelete(rNum);

        if(result>0){
            mav.setViewName("redirect:/rList");
        }else{
            mav.setViewName("redirect:/rView?rNum=" + rNum);
        }

        return mav;

    }

    /* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 */

    // 나의 좋아요 클릭 여부
    public String sLike(RLIKE like) {
        String result = dao.sLike(like);
        return result;
    }

    // 전체 좋아요 개수 확인
    public String rLike(int rNum) {
        String result = dao.getLike(rNum);
        return result;
    }

    // 처음 좋아요 활성화
    public void insertLike(RLIKE like){
        dao.setLike1(like); // 처음 좋아요 활성화 (0)
        dao.onLike(like);  // 좋아요 on (1)
        dao.plus1(like);   // 전체 게시글 좋아요 +1
    }


    // 좋아요
    public RLIKE mLike(RLIKE like) {

        // 저장된 좋아요가 무엇인지 확인
        RLIKE like3 = dao.result(like);

         if(like3.getLHeart().equals("1")){ // 좋아요가 on 일때

            // 좋아요 활성화 & 게시글 좋아요 -1
            System.out.println("좋아요가 이미 활성화 되어있을때");
            dao.offLike(like);  // 좋아요 off(2)
            dao.minus1(like);   // 전체 게시글 좋아요 -1

        } else if(like3.getLHeart().equals("2")){ // 좋아요가 off 일떄

            // 좋아요 비활성화 & 게시글 좋아요 +1
            System.out.println("좋아요를 누르지 않았을 때");
            dao.onLike(like);   // 좋아요 on(1)
            dao.plus1(like);   // 전체 게시글 좋아요 +1
        }

        return like3;

    }

    /* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 */




    // 댓글리스트 불러오기
    public List<RCOMMENT> rcList(int rNo) {


        List<RCOMMENT> rcommentList = dao.rcList(rNo);


        if(rcommentList.isEmpty()){
            dao.zero(rNo);
        }

        return rcommentList;

    }



    // 댓글 작성
    public List<RCOMMENT> rcWrite(RCOMMENT rcomment) throws IOException {

        List<RCOMMENT> rcommentList = null;

        // 이미 작성한 아이디가 있는지 확인 (있으면 댓글 작성 x)
        String rIdCheck = dao.rIdCheck(rcomment);


        if(rIdCheck == null){

            int result = dao.rcWrite(rcomment); // 댓글 작성하기

            if(result > 0){

                rcommentList = dao.rcList(rcomment.getRNo());

                // 평점 업데이트
                dao.rAvg(rcomment);

            }else{
                rcommentList = null;
            }
        } else{

            rcommentList = null;

        }

        return rcommentList;
    }


    // 댓글 삭제
    public List<RCOMMENT> rcDelete(RCOMMENT rcomment) {

        List<RCOMMENT> rcommentList = null;

        int result = dao.rcDelete(rcomment);



        if(result > 0){


            rcommentList = dao.rcList(rcomment.getRNo());



            if(rcommentList.isEmpty()){
                dao.zero(rcomment.getRNo());
            } else{
                dao.rAvg(rcomment);
            }

        } else{
            rcommentList = null;
        }

        return  rcommentList;

    }


    // 대댓글 리스트 불러오기
    public List<RECOMMENT> reList(RECOMMENT recomment) {


        List<RECOMMENT> recommentList = dao.reList(recomment);



        return recommentList;
    }



    // 대댓글 작성
    public List<RECOMMENT> reWrite(RECOMMENT recomment) {


        List<RECOMMENT> recommentList = null;


            int result = dao.reWrite(recomment); // 댓글 작성하기


            if (result > 0) {

                //recommentList = dao.rcList(rcomment.getRNo());

                recommentList = dao.reList(recomment);

            } else {
                recommentList = null;
            }


            return recommentList;
        }







    // 검색
    List<RECIPE> sList = new ArrayList<RECIPE>();
    public ModelAndView rSearch(String selectVal, String keyword) {


        if(selectVal.equals("rName")){
            sList = dao.rSearchName(selectVal,keyword);
        } else if(selectVal.equals("rId")){
            sList = dao.rSearchId(selectVal,keyword);
        } else{
            sList = dao.rSearchTag(selectVal,keyword);
        }

        mav.addObject("sList",sList);
        mav.setViewName("RS_List");


        return mav;
    }


    // 상세검색
    List<RECIPE> s2List = new ArrayList<RECIPE>();
    public ModelAndView rSearch2(String rType, String rFood, String rTime) {


        if(rType.equals("on")) { // 종류가 전체선택일때

            if (rFood.equals("on") && rTime.equals("on")) { // 1. 둘 다 전체선택 -> 모두검색

                s2List = dao.all();

            } else if (rFood.equals("on") && !rTime.equals("on")) { // 2. 메인 전체선택 -> 시간으로만 검색

                s2List = dao.rTime(rTime);

            } else if (!rFood.equals("on") && rTime.equals("on")) { // 3. 시간 전체선택 -> 메인으로만 검색

                s2List = dao.rFood(rFood);

            } else { // 메인, 시간으로 검색

                s2List = dao.rFT(rFood, rTime);

            }
        } else{ // 종류가 전체선택이 아닐때
            if (rFood.equals("on") && rTime.equals("on")) { // 1. 종류만 검색

                s2List = dao.rType(rType);

            } else if (rFood.equals("on") && !rTime.equals("on")) { // 2. 메인 전체선택 -> 종류, 시간으로 검색

                s2List = dao.rTT(rType, rTime);

            } else if (!rFood.equals("on") && rTime.equals("on")) { // 3. 시간 전체선택 -> 종류, 메인으로 검색

                s2List = dao.rTF(rType, rFood);

            } else { // 상세 검색

                s2List = dao.rSelect(rType, rFood, rTime);

            }

        }

        mav.addObject("sList",s2List);
        mav.setViewName("RS_List");

        return mav;
    }



}