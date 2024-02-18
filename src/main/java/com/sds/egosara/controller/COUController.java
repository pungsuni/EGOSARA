package com.sds.egosara.controller;

import com.sds.egosara.dto.COUPON;
import com.sds.egosara.service.COUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class COUController {

    @Autowired
    private COUService cousvc;

    private ModelAndView mav = new ModelAndView();

    // couWriteForm : 쿠폰 등록 페이지로 이동
    // (페이지로 이동은 수정페이지 빼고 나머지는 String 타입으로)
    @RequestMapping(value="/couWriteForm", method = RequestMethod.GET)
    public String couWriteForm(){
        return "COU_WriteForm";
    }

    // couWrite : 쿠폰등록
    @RequestMapping(value="/couWrite", method = RequestMethod.POST)
    public ModelAndView couWrite(@ModelAttribute COUPON coupon) throws IOException {
        mav = cousvc.couWrite(coupon);
        return mav;
    }

    // couList : 쿠폰목록보기
    @RequestMapping(value="/couList", method = RequestMethod.GET)
    public ModelAndView couList(){
        mav = cousvc.couList();
        return mav;
    }

}
