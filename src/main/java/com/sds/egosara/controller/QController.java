package com.sds.egosara.controller;

import com.sds.egosara.dto.QCOMMENT;
import com.sds.egosara.dto.QNA;
import com.sds.egosara.service.QService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QController {

    @Autowired
    private QService qsvc;

    private ModelAndView mav = new ModelAndView();



    // qWriteForm : QNA 작성 페이지 이동
    @RequestMapping(value = "/qWriteForm", method = RequestMethod.GET)
    public String qWriteForm() {

        return "Q_Write";
    }

    // qWrite : QNA 작성
    @RequestMapping(value = "/qWrite", method = RequestMethod.POST)
    public ModelAndView qWrite(@ModelAttribute QNA qna) throws IllegalStateException, IOException {
        mav = qsvc.qWrite(qna);
        return mav;
    }

    // qList : QNA 목록보기
    @RequestMapping(value="/qList", method = RequestMethod.GET)
    public ModelAndView qList(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        if (page <= 0) {
            page = 1;
        }
        mav = qsvc.qList(page);
        return mav;
    }

    // qView : QNA 상세보기
    @RequestMapping(value="/qView", method = RequestMethod.GET)
    public ModelAndView qView(@RequestParam("qNum")int qNum){
        mav = qsvc.qView(qNum);
        return mav;
    }

    // qModiForm : QNA 수정페이지로 이동
    @RequestMapping(value="qModiForm", method = RequestMethod.GET)
    public ModelAndView qModiForm(@RequestParam("qNum") int qNum){
        mav = qsvc.qModiForm(qNum);
        return mav;
    }

    // qModify : QNA 수정
    @RequestMapping(value="/qModify", method = RequestMethod.POST)
    public ModelAndView qModify(@ModelAttribute QNA qna) throws IOException {
        mav = qsvc.qModify(qna);
        return mav;
    }

    // qDelete : QNA 삭제
    @RequestMapping(value="/qDelete", method = RequestMethod.GET)
    public ModelAndView qDelete(@RequestParam("qNum")int qNum){
        mav = qsvc.qDelete(qNum);
        return mav;
    }







    // 댓글

    List<QCOMMENT> qcommentList = new ArrayList<QCOMMENT>();

    // qcList : 댓글 목록 보기
    @RequestMapping(value = "qcList", method = RequestMethod.POST)
    public @ResponseBody List<QCOMMENT> qcList(@RequestParam("qcbNum") int qcbNum) {

        List<QCOMMENT> qcommentList = qsvc.qcList(qcbNum);
        return qcommentList;

    }

    // qcWrite : 댓글 작성
    @RequestMapping(value = "qcWrite", method = RequestMethod.POST)
    public @ResponseBody List<QCOMMENT> qcWrite(@ModelAttribute QCOMMENT qcomment) {
        System.out.println("[1]controller : qcomment :: " + qcomment);
        List<QCOMMENT> qcommentList = qsvc.qcWrite(qcomment);
        return qcommentList;
    }

    // qcDelete : 댓글 삭제
    @RequestMapping(value = "qcDelete", method = RequestMethod.GET)
    public @ResponseBody List<QCOMMENT> qcDelete(@ModelAttribute QCOMMENT qcomment) {
        List<QCOMMENT> qcommentList = qsvc.qcDelete(qcomment);
        return qcommentList;
    }











}