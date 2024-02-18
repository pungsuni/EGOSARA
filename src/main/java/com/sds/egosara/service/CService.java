package com.sds.egosara.service;

import com.sds.egosara.dao.CDAO;
import com.sds.egosara.dto.CMEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CService {

    @Autowired
    private CDAO cdao;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder pwEnc;


    private ModelAndView mav = new ModelAndView();

    // cJoin : 회원가입
    public ModelAndView cJoin(CMEMBER cmember) throws IOException {

        // [1] 우리가 입력한 패스워드
        // [2] 암호화
        // [3] 암호화 된 패스워드를 CMEMBER에 다시 저장

        // 암호화 완성
        cmember.setCPw(pwEnc.encode(cmember.getCPw()));

        // 주소 1, 2, 3을 합쳐서 CAddr로 바꿔주기(set)
        String cAddress = cmember.getAddr1() + " " + cmember.getAddr2()+ " " + cmember.getAddr3();
        cmember.setCAddr(cAddress);

        // (1) 파일 불러오기
        MultipartFile cProfile = cmember.getCProfile();

        // (2) 파일이름 설정하기
        String originalFileName = cProfile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기
        String cProfileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/profile/" +cProfileName;

        // (6) 파일 선택여부
        if(!cProfile.isEmpty()){
            cmember.setCProfileName(cProfileName);
            cProfile.transferTo(new File(savePath));
        } else {
            cmember.setCProfileName("default.JPG");
        }

        // Q. 어떤 작업? 가입(입력)
        // Q. 입력, 수정, 삭제 시 int result 사용!
        int result = cdao.cJoin(cmember);

        if(result>0){
            // 성공
            mav.setViewName("index");
        } else {
            // 실패
            mav.setViewName("C_JoinForm");
        }

        return mav;

    }

    // A_idoverlap : 아이디 중복 검사
    public String idOverlap(String cId) {

        String idCheck = cdao.idOverlap(cId);
        String result = null;

        if(idCheck==null) {
            result = "OK";	// 중복x
        } else {
            result = "NO";	// 중복o
        }

        return result;
    }

    // A_nickoverlap : 닉네임 중복 검사
    public String nickOverlap(String cNickname) {
        String nickCheck = cdao.nickOverlap(cNickname);
        String result = null;

        if(nickCheck==null) {
            result = "OK";	// 중복x
        } else {
            result = "NO";	// 중복o
        }

        return result;
    }

    // cLogin : 회원 로그인
    public ModelAndView cLogin(CMEMBER cmember) {
        // [1] 입력한 아이디로 디비에 저장된 비밀번호와 이메일 검색
        CMEMBER cmember1 = cdao.cLogin(cmember);

        // [2] 입력한 비밀번호와 디비에 저장된 (암호화)비밀번호를 매칭
        // 1111 <=> $2a$10$Ep774UotJFXuJ1yWeH5HAu2i8br5vu4mSTs4RTEFF939UJIzt3A2G

        // pwEnc.matches() 타입은 boolean => true or false
        if(pwEnc.matches(cmember.getCPw(), cmember1.getCPw())){
            System.out.println("비밀번호 일치");
            mav.setViewName("index");

            if(cmember1!=null){
                // 로그인 성공
                session.setAttribute("loginId", cmember1.getCId());
                session.setAttribute("nickname", cmember1.getCNickname());
                session.setAttribute("profile", cmember1.getCProfile());
                mav.setViewName("index");
            } else {
                // 로그인 실패
                mav.setViewName("C_LoginForm");
            }
        } else {
            System.out.println("비밀번호 불일치");
            mav.setViewName("C_LoginForm");
        }

        return mav;
    }

    // cList : 회원 목록보기
    public ModelAndView cList() {

        // 목록 -> List<DTO> , 상세 -> DTO
        List<CMEMBER> cmemberList = cdao.cList();

        mav.setViewName("C_List");
        mav.addObject("cmemberList", cmemberList);

        return mav;
    }

    // cView : 회원 상세보기
    public ModelAndView cView(String cId) {
        CMEMBER cmember = cdao.cView(cId);

        if(cmember!=null){
            // 검색 한 회원의 정보가 존재할 때 (not null일때)
            mav.addObject("cmember", cmember);
            mav.setViewName("C_View");
        } else {
            // 검색 한 회원의 정보가 존재하지 않을 때 (null일때) -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/cList");
        }

        return mav;
    }

    // cModiForm : 회원 수정 페이지로 이동
    public ModelAndView cModiForm(String cId) {
        CMEMBER cmember = cdao.cView(cId);

        if(cmember!=null){
            // 검색 한 회원의 정보가 존재할 때 (not null일때)
            mav.addObject("cmember", cmember);
            mav.setViewName("C_Modify");
        } else {
            // 검색 한 회원의 정보가 존재하지 않을 때 (null일때) -> 리스트로 돌아가기
            // html파일이 아닌 controller의 주소로 값을 보낼 때 redirect:/주소
            mav.setViewName("redirect:/cList");
        }

        return mav;
    }

    // cModify : 회원 수정
    public ModelAndView cModify(CMEMBER cmember) throws IOException {

        // [1] 우리가 입력한 패스워드
        // [2] 암호화
        // [3] 암호화 된 패스워드를 SecuDTO에 다시 저장

        // 암호화 완성
        cmember.setCPw(pwEnc.encode(cmember.getCPw()));

        // 주소 1, 2, 3을 합쳐서 CAddr로 바꿔주기(set)
        String cAddress = cmember.getAddr1() + " " + cmember.getAddr2()+ " " + cmember.getAddr3();
        cmember.setCAddr(cAddress);

        // (1) 파일 불러오기
        MultipartFile cProfile = cmember.getCProfile();

        // (2) 파일이름 설정하기
        String originalFileName = cProfile.getOriginalFilename();

        // 스프링 파일 업로드 할 때 문제점! + 파일 이름이 같을 경우!

        // (3) 난수 생성하기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // (4) 난수와 파일이름 합치기
        String cProfileName = uuid + "_" + originalFileName;

        // (5) 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/profile/"+cProfileName;

        // (6) 파일 선택여부
        if(!cProfile.isEmpty()){
            cmember.setCProfileName(cProfileName);
            cProfile.transferTo(new File(savePath));
        } else {
            cmember.setCProfileName("default.JPG");
        }

        int result = cdao.cModify(cmember);

        if(result>0){
            mav.setViewName("redirect:/cView?cId="+cmember.getCId());
        } else {
            mav.setViewName("redirect:/cModiForm?cId="+cmember.getCId());
        }

        return mav;
    }

    // cDelete : 회원 삭제(GET방식)
    public ModelAndView cDelete(String cId) {
        int result = cdao.cDelete(cId);

        if(result>0){
            mav.setViewName("redirect:/cLogout");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }



}
