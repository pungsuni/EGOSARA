package com.sds.egosara.controller;

import com.sds.egosara.dto.NOTICE;
import com.sds.egosara.dto.QNA;
import com.sds.egosara.service.NService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class NController {

    @Autowired
    private NService nsvc;

    private ModelAndView mav = new ModelAndView();

    // nWriteForm : 공지사항 작성 페이지 이동
    @RequestMapping(value = "/nWriteForm", method = RequestMethod.GET)
    public String nWriteForm() { return "N_Write";  }

    // nWrite : 공지사항 작성
    @RequestMapping(value = "/nWrite", method = RequestMethod.POST)
    public ModelAndView nWrite(@ModelAttribute NOTICE notice) throws IllegalStateException, IOException {

        System.out.println("controller : NOTICE -> " + notice);

        mav = nsvc.nWrite(notice);

        System.out.println("controller : mav -> " + mav);
        return mav;
    }

    // nList : 공지사항 목록보기
    @RequestMapping(value="/nList", method = RequestMethod.GET)
    public ModelAndView nList(){
        mav = nsvc.nList();
        return mav;
    }

    // nView : 공지사항 상세보기
    @RequestMapping(value="/nView", method = RequestMethod.GET)
    public ModelAndView nView(@RequestParam("nNum")int nNum){
        mav = nsvc.nView(nNum);
        return mav;
    }

    // nModiForm : 공지사항 수정페이지로 이동
    @RequestMapping(value="nModiForm", method = RequestMethod.GET)
    public ModelAndView nModiForm(@RequestParam("nNum") int nNum){
        mav = nsvc.nModiForm(nNum);
        return mav;
    }

    // nModify : 공지사항 수정
    @RequestMapping(value="/nModify", method = RequestMethod.POST)
    public ModelAndView nModify(@ModelAttribute NOTICE notice) throws IOException {
        mav = nsvc.nModify(notice);
        return mav;
    }

    // nDelete : 공지사항 삭제
    @RequestMapping(value="/nDelete", method = RequestMethod.GET)
    public ModelAndView nDelete(@RequestParam("nNum")int nNum){
        mav = nsvc.nDelete(nNum);
        return mav;
    }

}
