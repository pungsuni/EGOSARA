package com.sds.egosara.controller;

import com.sds.egosara.dto.SMEMBER;
import com.sds.egosara.service.SService;
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
public class SController {

    @Autowired
    private SService ssvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private JavaMailSender mailSender;


    private ModelAndView mav = new ModelAndView();

    // sJoinForm : 업체 회원가입 페이지로 이동
    @RequestMapping(value="sJoinForm", method = RequestMethod.GET)
    public String sJoinForm(){

        return "S_JoinForm";
    }

    // sJoin : 업체 회원가입
    @RequestMapping(value="sJoin", method = RequestMethod.POST)
    public ModelAndView sJoin(@ModelAttribute SMEMBER smember) throws IOException {

        mav = ssvc.sJoin(smember);
        return mav;
    }

    // S_idoverlap : 아이디 중복 검사
    @RequestMapping(value="/S_idoverlap", method = RequestMethod.POST)
    public @ResponseBody
    String idOverlap(@RequestParam("sId") String sId) {

        String result = ssvc.idOverlap(sId);
        return result;
    }

    // sEmailConfirm : 이메일 인증
    @RequestMapping(value="/sEmailConfirm", method = RequestMethod.GET)
    public @ResponseBody
    String sEmailConfirm(@RequestParam("sEmail") String sEmail) {

        String uuid = UUID.randomUUID().toString().substring(1,7);
        //System.out.println("uuid : " + uuid);

        String str = "<h2>안녕하세요. EGOSARA입니다</h2>"
                +"<p>인증번호는 " + uuid + " 입니다.</p>";

        MimeMessage mail = mailSender.createMimeMessage();

        try {
            mail.setSubject("EGOSARA 업체 이메일 인증");
            mail.setText(str, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(sEmail));

            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return uuid;
    }

    // sLoginForm : 업체 로그인 페이지로 이동
    @RequestMapping(value="sLoginForm", method = RequestMethod.GET)
    public String sLoginForm(){
        return "S_LoginForm";
    }

    // sLogin : 업체 로그인
    @RequestMapping(value="sLogin", method = RequestMethod.POST)
    public ModelAndView sLogin(@ModelAttribute SMEMBER smember){

        mav = ssvc.sLogin(smember);
        return mav;
    }

    // sLogout : 로그아웃
    @RequestMapping(value="sLogout", method = RequestMethod.GET)
    public String sLogout(){
        session.invalidate();   // session을 초기화 하는 작업
        return "index";
    }

    // sList : 업체목록 보기
    @RequestMapping(value="sList", method = RequestMethod.GET)
    public ModelAndView sList(){

        mav = ssvc.sList();
        return mav;
    }

    // sView : 업체 상세보기
    @RequestMapping(value="sView", method = RequestMethod.GET)
    public ModelAndView sView(@RequestParam("sId") String sId){
        mav = ssvc.sView(sId);
        return mav;
    }

    // sModiForm : 업체 수정 페이지로 이동
    @RequestMapping(value="sModiForm", method = RequestMethod.GET)
    public ModelAndView sModiForm(@RequestParam("sId") String sId){
        mav = ssvc.sModiForm(sId);
        return mav;
    }

    // sModify : 업체 수정
    @RequestMapping(value="sModify", method = RequestMethod.POST)
    public ModelAndView sModify(@ModelAttribute SMEMBER smember) throws IOException {

        mav = ssvc.sModify(smember);
        return mav;
    }

    // sDelete : 업체 삭제(GET방식)
    @RequestMapping(value="/sDelete", method = RequestMethod.GET)
    public ModelAndView sDelete(@RequestParam("sId") String sId){
        mav = ssvc.sDelete(sId);
        return mav;
    }
}
