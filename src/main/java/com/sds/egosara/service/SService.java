package com.sds.egosara.service;

import com.sds.egosara.dao.SDAO;
import com.sds.egosara.dto.SMEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Service
public class SService {

    @Autowired
    private SDAO sdao;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder pwEnc;


    private ModelAndView mav = new ModelAndView();

    // sJoin : 업체 회원가입
    public ModelAndView sJoin(SMEMBER smember) {

        // [1] 우리가 입력한 패스워드
        // [2] 암호화
        // [3] 암호화 된 패스워드를 SMEMBER에 다시 저장

        // 암호화 완성
        smember.setSPw(pwEnc.encode(smember.getSPw()));

        // 주소 1, 2, 3을 합쳐서 SAddr로 바꿔주기(set)
        String sAddress = smember.getAddr1() + " " + smember.getAddr2()+ " " + smember.getAddr3();
        smember.setSAddr(sAddress);

        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = sdao.sJoin(smember);

        if(result>0){
            // 성공
            mav.setViewName("index");
        } else {
            // 실패
            mav.setViewName("S_JoinForm");
        }

        return mav;

    }

    // S_idoverlap : 아이디 중복 검사
    public String idOverlap(String sId) {

        String idCheck = sdao.idOverlap(sId);
        String result = null;

        if(idCheck==null) {
            result = "OK";	// 중복x
        } else {
            result = "NO";	// 중복o
        }

        return result;
    }

    // sLogin : 로그인
    public ModelAndView sLogin(SMEMBER smember) {
        // [1] 입력한 아이디로 디비에 저장된 비밀번호와 이메일 검색
        SMEMBER smember1 = sdao.sLogin(smember);

        // [2] 입력한 비밀번호와 디비에 저장된 (암호화)비밀번호를 매칭
        // 1111 <=> $2a$10$Ep774UotJFXuJ1yWeH5HAu2i8br5vu4mSTs4RTEFF939UJIzt3A2G

        // pwEnc.matches() 타입은 boolean => true or false
        if(pwEnc.matches(smember.getSPw(), smember1.getSPw())){
            System.out.println("비밀번호 일치");
            mav.setViewName("index");
            if(smember1!=null){
                // 로그인 성공
                session.setAttribute("sloginId", smember1.getSId());
                mav.setViewName("index");
            } else {
                // 로그인 실패
                mav.setViewName("S_LoginForm");
            }
        } else {
            System.out.println("비밀번호 불일치");
            mav.setViewName("S_LoginForm");
        }

        return mav;
    }

    // sList : 업체목록 보기
    public ModelAndView sList() {

        // 목록 -> List<DTO> , 상세 -> DTO
        List<SMEMBER> smemberList = sdao.sList();

        mav.setViewName("S_List");
        mav.addObject("smemberList", smemberList);

        return mav;
    }

    // sView : 업체 상세보기
    public ModelAndView sView(String sId) {
        SMEMBER smember = sdao.sView(sId);

        if(smember!=null){
            // 검색 한 회원의 정보가 존재할 때 (not null일때)
            mav.addObject("smember", smember);
            mav.setViewName("S_View");
        } else {
            // 검색 한 회원의 정보가 존재하지 않을 때 (null일때) -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/sList");
        }

        return mav;
    }

    // sModiForm : 업체 수정 페이지로 이동
    public ModelAndView sModiForm(String sId) {
        SMEMBER smember = sdao.sView(sId);

        if(smember!=null){
            // 검색 한 회원의 정보가 존재할 때 (not null일때)
            mav.addObject("smember", smember);
            mav.setViewName("S_Modify");
        } else {
            // 검색 한 회원의 정보가 존재하지 않을 때 (null일때) -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/sList");
        }

        return mav;
    }

    // sModify : 업체 수정
    public ModelAndView sModify(SMEMBER smember) throws IOException {

        // [1] 우리가 입력한 패스워드
        // [2] 암호화
        // [3] 암호화 된 패스워드를 SMEMBER에 다시 저장

        // 암호화 완성
        smember.setSPw(pwEnc.encode(smember.getSPw()));

        // 주소 1, 2, 3을 합쳐서 SAddr로 바꿔주기(set)
        String sAddress = smember.getAddr1() + " " + smember.getAddr2()+ " " + smember.getAddr3();
        smember.setSAddr(sAddress);

        int result = sdao.sModify(smember);

        if(result>0){
            mav.setViewName("redirect:/sView?sId="+smember.getSId());
        } else {
            mav.setViewName("redirect:/sModiForm?sId="+smember.getSId());
        }

        return mav;
    }

    // sDelete : 업체 삭제(GET방식)
    public ModelAndView sDelete(String sId) {
        int result = sdao.sDelete(sId);

        if(result>0){
            mav.setViewName("redirect:/sLogout");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }
}
