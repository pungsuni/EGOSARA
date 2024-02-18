package com.sds.egosara.controller;

import com.sds.egosara.dto.RCOMMENT;
import com.sds.egosara.dto.RECIPE;
import com.sds.egosara.dto.RECOMMENT;
import com.sds.egosara.dto.RLIKE;
import com.sds.egosara.service.RService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class RController {

    @Autowired
    private RService rsvc;

    private ModelAndView mav = new ModelAndView();


    /*// index : 초기 페이지를 인덱스 페이지로 -> 코드 합칠시 삭제
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index1(){

        return "index";
    }*/

    // rWriteForm : 레시피 작성 페이지로 이동
    @RequestMapping(value = "/rWriteForm", method = RequestMethod.GET)
    public String rWriteForm(){

        return "R_Write";
    }

    // rWrite : 레시피 작성
    @RequestMapping(value = "/rWrite", method = RequestMethod.POST)
    public ModelAndView rWrite(@ModelAttribute RECIPE recipe) throws IOException {

        mav = rsvc.rWrite(recipe);

        return mav;
    }

    // rList : 레시피 목록
    @RequestMapping(value = "/rList", method = RequestMethod.GET)
    public ModelAndView rList(){

        mav = rsvc.rList();

        return mav;
    }

    // rView : 레시피 자세히 보기
    @RequestMapping(value = "/rView", method = RequestMethod.GET)
    public ModelAndView rView(@RequestParam("rNum") int rNum){

        mav = rsvc.rView(rNum);

        return mav;
    }

    // rModiForm : 레시피 수정 페이지로 이동
    @RequestMapping(value = "rModiForm", method = RequestMethod.GET)
    public ModelAndView rModiForm(@RequestParam("rNum") int rNum){

        mav = rsvc.rModiForm(rNum);

        return mav;
    }

    // rModify : 레시피 수정
    @RequestMapping(value = "rModify", method = RequestMethod.POST)
    public ModelAndView rModify(@ModelAttribute RECIPE recipe) throws IOException {

        mav = rsvc.rModify(recipe);


        return mav;
    }

    // rDelete : 레시피 삭제
    @RequestMapping(value = "rDelete", method = RequestMethod.GET)
    public ModelAndView rDelete(@RequestParam("rNum") int rNum){

        mav = rsvc.rDelete(rNum);

        return mav;
    }

    /* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 */

    // sLike : 나의 좋아요 클릭 여부 확인
    @RequestMapping(value = "/sLike", method = RequestMethod.POST)
    public @ResponseBody String sLike(@ModelAttribute RLIKE like){

        String result = rsvc.sLike(like);

        return result;
    }

    // rLike : 전체 좋아요 개수 확인
    @RequestMapping(value = "/rLike", method = RequestMethod.POST)
    public @ResponseBody String rLike(@RequestParam("lrNum") int lrNum){

        String result = rsvc.rLike(lrNum);

        return result;
    }

    // insertLike : 처음 좋아요 활성화 (0) -> 좋아요 on (1)
    @RequestMapping(value = "/insertLike", method = RequestMethod.POST)
    public @ResponseBody void insertLike(@ModelAttribute RLIKE like){

        rsvc.insertLike(like);

    }


    // Like : 좋아요
    @RequestMapping(value = "/mLike", method = RequestMethod.POST)
    public @ResponseBody RLIKE mLike(@ModelAttribute RLIKE like){

        RLIKE result = rsvc.mLike(like);

        return result;
    }

    /* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 *//* 좋아요 */






    // rcList : 댓글 리스트 불러오기
    @RequestMapping(value = "rcList", method = RequestMethod.POST)
    public @ResponseBody List<RCOMMENT> rcList(@RequestParam("rNo") int rNo){

        List<RCOMMENT> rcommentList = rsvc.rcList(rNo);

        return rcommentList;
    }


    // rcWrite : 댓글 작성
    @RequestMapping(value = "rcWrite", method = RequestMethod.POST)
    public @ResponseBody List<RCOMMENT> rcWrite(@ModelAttribute RCOMMENT rcomment) throws IOException {


        List<RCOMMENT> rcommentList = rsvc.rcWrite(rcomment);

        return rcommentList;
    }

    // rcDelete: 댓글삭제
    @RequestMapping(value = "rcDelete", method = RequestMethod.GET)
    public @ResponseBody List<RCOMMENT> rcDelete(@ModelAttribute RCOMMENT rcomment){

        List<RCOMMENT> rcommentList = rsvc.rcDelete(rcomment);

        return rcommentList;
    }

    // reList : 대댓글 리스트 불러오기
    @RequestMapping(value = "reList", method = RequestMethod.POST)
    public @ResponseBody List<RECOMMENT> reList(@ModelAttribute RECOMMENT recomment){

        System.out.println("=================대댓글 리스트 =======================");
        System.out.println("1. controller -> recomment : " + recomment);

        System.out.println("rcNo : " + recomment.getRcNo());

        List<RECOMMENT> recommentList = rsvc.reList(recomment);

        System.out.println("4. controller -> recommentList : " + recommentList);

        return recommentList;
    }

    // reWrite : 대댓글 작성
    @RequestMapping(value = "reWrite", method = RequestMethod.POST)
    public @ResponseBody List<RECOMMENT> reWrite(@ModelAttribute RECOMMENT recomment){


        List<RECOMMENT> recommentList = rsvc.reWrite(recomment);

        return recommentList;
    }




    // rSearch : 검색
    @RequestMapping(value = "rSearch", method = RequestMethod.GET)
    public ModelAndView rSearch(@RequestParam("selectVal") String selectVal, @RequestParam("keyword") String keyword){

        mav = rsvc.rSearch(selectVal,keyword);

        return mav;
    }

    // rSearch2 : 상세검색
    @RequestMapping(value = "rSearch2", method = RequestMethod.GET)
    public ModelAndView rSearch2(@RequestParam("rType") String rType, @RequestParam("rFood") String rFood, @RequestParam("rTime") String rTime){

        System.out.println("1. rType : " + rType + ", rFood : " + rFood + ", rTime: " + rTime);

        mav = rsvc.rSearch2(rType, rFood, rTime);

        System.out.println("4. mav : " + mav);

        return mav;
    }





}
