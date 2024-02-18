package com.sds.egosara.controller;

import com.sds.egosara.dto.CMEMBER;
import com.sds.egosara.service.CService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;


@Controller
public class CController {

    @Autowired
    private CService csvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private JavaMailSender mailSender;


    private ModelAndView mav = new ModelAndView();

//    // 프로젝트 시작 시 실행되는 메인페이지
//    @RequestMapping(value="/index", method = RequestMethod.GET)
//    public String index1(){
//        return "index";
//    }


    @RequestMapping(value = "/index_move", method = RequestMethod.GET)
    public String index_move(){
        return "index";
    }

    // cJoinForm : 회원가입 페이지로 이동
    @RequestMapping(value="/cJoinForm", method = RequestMethod.GET)
    public String cJoinForm(){

        return "C_JoinForm";
    }

    // cJoin : 회원가입
    @RequestMapping(value="/cJoin", method = RequestMethod.POST)
    public ModelAndView cJoin(@ModelAttribute CMEMBER cmember) throws IOException {

        mav = csvc.cJoin(cmember);
        return mav;
    }

    // A_idoverlap : 아이디 중복 검사
    @RequestMapping(value="/A_idoverlap", method = RequestMethod.POST)
    public @ResponseBody
    String idOverlap(@RequestParam("cId") String cId) {

        String result = csvc.idOverlap(cId);
        return result;
    }

    // A_nickoverlap : 닉네임 중복 검사
    @RequestMapping(value="/A_nickoverlap", method = RequestMethod.POST)
    public @ResponseBody
    String nickOverlap(@RequestParam("cNickname") String cNickname) {

        String result = csvc.nickOverlap(cNickname);
        return result;
    }

    // cEmailConfirm : 이메일 인증
    @RequestMapping(value="/cEmailConfirm", method = RequestMethod.GET)
    public @ResponseBody
    String cEmailConfirm(@RequestParam("cEmail") String cEmail) {

        String uuid = UUID.randomUUID().toString().substring(1,7);
        //System.out.println("uuid : " + uuid);

        String str = "<h2>안녕하세요. EGOSARA입니다</h2>"
                +"<p>인증번호는 " + uuid + " 입니다.</p>";

        MimeMessage mail = mailSender.createMimeMessage();

        try {
            mail.setSubject("EGOSARA 일반회원 이메일 인증");
            mail.setText(str, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(cEmail));

            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return uuid;
    }

    // cLoginForm : 회원 로그인 페이지로 이동
    @RequestMapping(value="/cLoginForm", method = RequestMethod.GET)
    public String cLoginForm(){
        return "C_LoginForm";
    }

    // cLogin : 회원 로그인
    @RequestMapping(value="/cLogin", method = RequestMethod.POST)
    public ModelAndView cLogin(@ModelAttribute CMEMBER cmember){

        mav = csvc.cLogin(cmember);
        return mav;
    }

    // cLogout : 로그아웃
    @RequestMapping(value="/cLogout", method = RequestMethod.GET)
    public String cLogout(){
        session.invalidate();   // session을 초기화 하는 작업
        return "index";
    }

    // cList : 회원 목록보기
    @RequestMapping(value="/cList", method = RequestMethod.GET)
    public ModelAndView cList(){

        mav = csvc.cList();
        return mav;
    }

    // cView : 회원 상세보기
    @RequestMapping(value="/cView", method = RequestMethod.GET)
    public ModelAndView cView(@RequestParam("cId") String cId){
        mav = csvc.cView(cId);
        return mav;
    }

    // cModiForm : 회원 수정 페이지로 이동
    @RequestMapping(value="/cModiForm", method = RequestMethod.GET)
    public ModelAndView cModiForm(@RequestParam("cId") String cId){
        mav = csvc.cModiForm(cId);
        return mav;
    }

    // cModify : 회원 수정
    @RequestMapping(value="/cModify", method = RequestMethod.POST)
    public ModelAndView cModify(@ModelAttribute CMEMBER cmember) throws IOException {

        mav = csvc.cModify(cmember);
        return mav;
    }

    // cDelete : 회원 삭제(GET방식)
    @RequestMapping(value="/cDelete", method = RequestMethod.GET)
    public ModelAndView cDelete(@RequestParam("cId") String cId){
        mav = csvc.cDelete(cId);
        return mav;
    }


}
