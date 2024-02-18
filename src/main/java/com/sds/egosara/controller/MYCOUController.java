package com.sds.egosara.controller;

import com.sds.egosara.dto.MYCOUPON;
import com.sds.egosara.service.MYCOUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MYCOUController {

    @Autowired
    private MYCOUService mycousvc;

    private ModelAndView mav = new ModelAndView();

    // mycouSave : 내 쿠폰 저장
    @RequestMapping(value="mycouSave", method = RequestMethod.GET)
    public @ResponseBody void mycouSave(@ModelAttribute MYCOUPON mycoupon){
        mycousvc.mycouSave(mycoupon);
    }

    // mycouList : 내 쿠폰목록보기
    @RequestMapping(value="/mycouList", method = RequestMethod.GET)
    public ModelAndView mycouList(@RequestParam("cId") String cId){
        mav = mycousvc.mycouList(cId);
        return mav;
    }

    //mycouDelete : 내 쿠폰 삭제
    @RequestMapping(value="/mycouDelete", method = RequestMethod.GET)
    public ModelAndView mycouDelete(@RequestParam("cId") String cId , @RequestParam("couNum") int couNum){
        mav = mycousvc.mycouDelete(cId,couNum);
        return mav;
    }

}
