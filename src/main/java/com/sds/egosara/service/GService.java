package com.sds.egosara.service;

import com.sds.egosara.dao.GDAO;
import com.sds.egosara.dto.GCCOMENT;
import com.sds.egosara.dto.GOODS;
import com.sds.egosara.dto.ORDERING;
import com.sds.egosara.dto.RECIPE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class GService {
    @Autowired
    private GDAO dao;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();


    // 추가!!!!!!
    // 인덱스에 보여질 인기상품 목록 가져오기
    public List<GOODS> hotGoodsList() {
        List<GOODS> hotGoods = dao.hotGoodsList();
        mav.addObject("hotGoods", hotGoods);
        return hotGoods;
    }
    // 추가!!!!!
    // 인덱스에 보여질 인기레시피 목록 가져오기


    // 상품 작성 메소드
    public ModelAndView gWrite(GOODS goods) throws IOException {
        // 1. 파일 불러오기
        MultipartFile gFile = goods.getGFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = gFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String gPhoto = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/goods/"+gPhoto;

        // 6. 파일 선택여부
        if(!gFile.isEmpty()){
            goods.setGPhoto(gPhoto);
            gFile.transferTo(new File(savePath));
        }
        int result = dao.gWrite(goods);

        if(result>0){
            mav.setViewName("index");
        } else {
            mav.setViewName("G_Write");
        }

        return mav;
    }


    // 상품 상세보기 메소드
    public ModelAndView gView(int gNum) {
        // (1)조회수 증가
        dao.gCount(gNum);

        GOODS goods = dao.gView(gNum);

        if(goods!=null){
            mav.addObject("view", goods);
            mav.setViewName("G_View");
        } else {
            mav.setViewName("redirect:/gList");
        }
        return mav;
    }

    // 상품 수정페이지
    public ModelAndView gModiForm(int gNum, String gId) {
        GOODS goods = dao.gView(gNum);

        if(gId.equals(goods.getGId())){
            mav.addObject("goods", goods);
            mav.setViewName("G_Modify");
        } else {
            mav.setViewName("index");
        }
        return mav;
    }
    // 상품 수정
    public ModelAndView gModify(GOODS goods) throws IOException {
        // 1. 파일 불러오기
        MultipartFile gFile = goods.getGFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = gFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String gPhoto = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/goods/"+gPhoto;

        // 6. 파일 선택여부
        if(!gFile.isEmpty()){
            goods.setGPhoto(gPhoto);
            gFile.transferTo(new File(savePath));
        }
        int result = dao.gModify(goods);

        if(result>0){
            mav.setViewName("redirect:/gView?gNum="+goods.getGNum());
        } else {
            mav.setViewName("redirect:/gModiForm?gNum="+goods.getGNum());
        }

        return mav;
    }

    // 상품 삭제 메소드
    public ModelAndView gDelete(GOODS goods) {
        // 장바구니 우선 제거
        int result_b = dao.gbDelete(goods.getGNum());
        // 댓글 우선 제거
        int result_c = dao.gccDelete(goods.getGNum());
        // 찜 삭제....
        int result_d = dao.gdDelete(goods.getGNum());
        // 진짜 상품 삭제
        int result = dao.gDelete(goods);

        if(result>0){
            mav.setViewName("redirect:/gList?gCate=0");
        } else {
            mav.setViewName("redirect:/gView?gNum=" + goods.getGNum());
        }
        return mav;
    }


    // 상품 목록보기 메소드
    public ModelAndView gList(GOODS goods) {

        if(goods.getGCate().equals("0")){
            List<GOODS> goodsList = dao.gList();
            mav.addObject("goodsList",goodsList);
            mav.setViewName("G_List");
        } else {
            List<GOODS> goodsList = dao.gList1(goods.getGCate());
            mav.addObject("goodsList",goodsList);
            mav.setViewName("G_List");
        }
        return mav;
    }

    // 리뷰 작성 메소드
    public ModelAndView gcWrite(GCCOMENT gccoment) throws IOException {
        // 1. 파일 불러오기
        MultipartFile gcFile = gccoment.getGcFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = gcFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String gcPhoto = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/goods/"+gcPhoto;

        // 6. 파일 선택여부
        if(!gcFile.isEmpty()){
            gccoment.setGcPhoto(gcPhoto);
            gcFile.transferTo(new File(savePath));
        }
        int result = dao.gcWrite(gccoment);

        // 리뷰 입력 시 상품의 별점 평균을 업데이트 하기
        dao.gcStarUpdate(gccoment);

        if(result>0){
            mav.setViewName("redirect:/gcList?gcNum=" + gccoment.getGcNum());
        } else {
            mav.setViewName("GC_Write");
        }
        return mav;
    }


    // 리뷰 삭제 메소드
    public ModelAndView gcDelete(GCCOMENT gccoment) {
        int result = dao.gcDelete(gccoment);

        if(result>0){
            mav.setViewName("redirect:/gcList?gcNum=" + gccoment.getGcNum());
        } else {
            mav.setViewName("index");
        }
        return mav;
    }


    // 리뷰목록보기 메소드
    public ModelAndView gcList(int gcNum) {
        List<GCCOMENT> gccommentList = dao.gcList(gcNum);

        mav.setViewName("GC_List");
        mav.addObject("gccommentList",gccommentList);

        return mav;
    }

    // 상품 리뷰 작성시 상품의 상품번호 가져가기
    public ModelAndView gcWrite1(int gNum, String gcId) {
        GCCOMENT gccoment = new GCCOMENT();

        gccoment.setGcNum(gNum);
        gccoment.setGcId(gcId);

        // 구매기록있는 사람만 작성가능
        List<ORDERING> order = dao.example(gccoment);

        if (order.isEmpty()) {
            // 주문한 적이 없다면 = 해당 아이디와 상품번호로 주문번호 조회시 null이 가져와지는 경우
            mav.setViewName("redirect:/gView?gNum=" + gccoment.getGcNum());
        } else {
            // 주문한 적이 있으면 리뷰 작성 페이지로 이동
            session.setAttribute("sessiongNum", gccoment.getGcNum());
            mav.setViewName("GC_Write");
        }
        return mav;
    }

    // gSearch : 상품검색
    public ModelAndView gSearch(GOODS goods) {
        List<GOODS> goodsList;

        if(goods.getSelectVal().equals("gName") ){
            goodsList = dao.gSearch(goods.getKeyword());
        } else {
            goodsList = dao.gPriceSearch(goods);
        }
        mav.addObject("goodsList", goodsList);
        mav.setViewName("G_List");

        return mav;
    }


    // ㅔ레ㅔㅔㅔ
    //public List<RECIPE> topRecipeList() {
    //        List<RECIPE> topCook = dao.topRecipeList();
    //        mav.addObject("topCook", topCook);
    //        return topCook;
    //    }
    public List<RECIPE> topRecipeList() {
        List<RECIPE> topCook = dao.topRecipeList();

        mav.addObject("topCook", topCook);

        return topCook;
    }
}