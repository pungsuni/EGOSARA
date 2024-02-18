package com.sds.egosara.controller;

import com.sds.egosara.dto.GCCOMENT;
import com.sds.egosara.dto.GOODS;
import com.sds.egosara.dto.RECIPE;
import com.sds.egosara.service.GService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class GController {
    @Autowired
    private GService gsvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();


    // 추가!!!!!
    // 인덱스에 보여질 인기상품 목록 가져오기
    @RequestMapping(value = "/hotGoodsList", method = RequestMethod.POST)
    public @ResponseBody List<GOODS> hotGoodsList(){
        List<GOODS> hotGoods = gsvc.hotGoodsList();
        return hotGoods;
    }
    // 추가!!!!!
    // 인덱스에 보여질 인기레시피 목록 가져오기
    @RequestMapping(value = "/topRecipeList", method = RequestMethod.POST)
    public @ResponseBody List<RECIPE> topRecipeList(){
        List<RECIPE> topCook = gsvc.topRecipeList();
        return  topCook;
    }



    // writeForm : 글쓰기 페이지로 이동
    @RequestMapping(value="/writeForm", method = RequestMethod.GET)
    public String writeForm(){
        return "G_Write";
    }

    // gWrite : 글작성
    @RequestMapping(value="/gWrite", method = RequestMethod.POST)
    public ModelAndView gWrite(@ModelAttribute GOODS goods) throws IOException {
        mav = gsvc.gWrite(goods);
        return mav;
    }

    // gView : 게시글 상세보기
    @RequestMapping(value="/gView", method = RequestMethod.GET)
    public ModelAndView gView(@RequestParam("gNum")int gNum){
        mav = gsvc.gView(gNum);
        return mav;
    }

    // gModiForm : 수정페이지로 이동
    @RequestMapping(value="gModiForm", method = RequestMethod.GET)
    public ModelAndView gModiForm(@RequestParam("gNum") int gNum, @RequestParam("gId") String gId){
        mav = gsvc.gModiForm(gNum, gId);
        return mav;
    }

    // gModify : 게시글 수정
    @RequestMapping(value="/gModify", method = RequestMethod.POST)
    public ModelAndView gModify(@ModelAttribute GOODS goods) throws IllegalStateException, IOException {
        mav = gsvc.gModify(goods);
        return mav;
    }

    // gDelete : 상품 삭제
    @RequestMapping(value="/gDelete", method = RequestMethod.GET)
    public ModelAndView gDelete(@RequestParam("gNum") int gNum, @RequestParam("gId") String gId){
        GOODS goods = new GOODS();
        goods.setGId(gId);
        goods.setGNum(gNum);

        mav = gsvc.gDelete(goods);

        return mav;
    }

    // gList : 글목록보기 -> GCATE 가져가기
    @RequestMapping(value="/gList", method = RequestMethod.GET)
    public ModelAndView gList(@ModelAttribute GOODS goods){
        mav = gsvc.gList(goods);
        return mav;
    }

    // gcwriteForm : 글쓰기 페이지로 이동
    @RequestMapping(value="/gcwriteForm", method = RequestMethod.GET)
    public ModelAndView gcwriteForm(@RequestParam("gNum") int gNum, @RequestParam("gcId") String gcId) {
        mav = gsvc.gcWrite1(gNum, gcId);
        return mav;
    }

    // gcWrite : 글작성
    @RequestMapping(value="/gcWrite", method = RequestMethod.POST)
    public ModelAndView gcWrite(@ModelAttribute GCCOMENT gccoment) throws IOException {
        mav = gsvc.gcWrite(gccoment);
        return mav;
    }

    // gcDelete : 게시글 삭제
    @RequestMapping(value="/gcDelete", method = RequestMethod.GET)
    public ModelAndView gcDelete(@RequestParam("gcId") String gcId, @RequestParam("gcNum") int gcNum, @RequestParam("gNo") int gNo){
        GCCOMENT gccoment = new GCCOMENT();

        gccoment.setGcId(gcId);
        gccoment.setGcNum(gcNum);
        gccoment.setGNo(gNo);

        mav = gsvc.gcDelete(gccoment);

        return mav;
    }

    // gcList : 글목록보기
    @RequestMapping(value="/gcList", method = RequestMethod.GET)
    public ModelAndView gcList(@RequestParam("gcNum") int gcNum){
        mav = gsvc.gcList(gcNum);
        return mav;
    }

    // gSearch : 상품 검색
    @RequestMapping(value = "gSearch", method = RequestMethod.GET)
    public ModelAndView gSearch(@ModelAttribute GOODS goods) {
        mav = gsvc.gSearch(goods);
        return mav;
    }
}
