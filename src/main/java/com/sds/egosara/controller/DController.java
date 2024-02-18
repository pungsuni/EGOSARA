package com.sds.egosara.controller;

import com.sds.egosara.dto.DIB;
import com.sds.egosara.service.DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DController {
    @Autowired
    private DService dsvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // 찜 여부 조회하기
    // 처음 G_View 페이지에서 document.ready로 찜 버튼을 ♥, ♡ 둘 중에 뭔지 확인!
    // 찜 버튼 누르면 G_View에서 상품번호(GOODS_GNum)과 로그인한 아이디(SDSMEMBER_SId) 가져와서 이미 누른 상태인지 아닌지 확인
    @RequestMapping(value = "/checkDIB", method = RequestMethod.POST)
    public @ResponseBody void checkDIB(@RequestParam("dNum") int dNum, @RequestParam("dId") String dId){
        // 로그인한 아이디와 내가 선택한 상품의 상품 번호를 가져와서 Service에 넘기기
        dsvc.checkDIB(dNum, dId);

        // void 타입으로 메소드를 만들었기 때문에 따로 return 값은 지정하지 않음
    }

    // ooDIB : 찜 실행
    // 로그인한 아이디와 상품 번호를 가져오기
    @RequestMapping(value = "/ooDIB", method = RequestMethod.POST)
    public @ResponseBody void ooDIB(@RequestParam("dNum") int dNum, @RequestParam("dId") String dId, @ModelAttribute DIB dib){
        dib.setDId(dId);
        dib.setDNum(dNum);
        // 로그인한 아이디와 내가 선택한 상품 번호를 ajax로 가져와서 Service에 넘기기
        dsvc.ooDIB(dib);

    }

    // xxDIB : 찜 취소
    @RequestMapping(value = "/xxDIB", method = RequestMethod.POST)
    public @ResponseBody void xxDIB(@RequestParam("dNum") int dNum, @RequestParam("dId") String dId, @ModelAttribute DIB dib){
        dib.setDId(dId);
        dib.setDNum(dNum);
        // 로그인한 아이디와 내가 선택한 상품 번호를 ajax로 가져와서 Service에 넘기기
        dsvc.xxDIB(dib);

    }

    // getDibList : 찜 목록 불러오기
    @RequestMapping(value = "/getDibList", method = RequestMethod.GET)
    public ModelAndView getDibList(@RequestParam("dId") String dId){
        mav = dsvc.getDibList(dId);
        return mav;
    }

    // dDelete : 찜 목록에서 찜한 상품 삭제
    @RequestMapping(value = "/dDelete", method = RequestMethod.GET)
    public ModelAndView dDelete(@RequestParam("dId") String dId, @RequestParam("dNum") int dNum){
        // dib 객체 선언
        DIB dib = new DIB();

        // DId와 DNum을 dib()에 set해주기
        dib.setDId(dId);
        dib.setDNum(dNum);

        // set해준 정보를 가져가서 삭제 진행
        mav = dsvc.dDelete(dib);

        return mav;
    }
}