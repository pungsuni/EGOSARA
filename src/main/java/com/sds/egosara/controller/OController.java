package com.sds.egosara.controller;

import com.sds.egosara.dto.BASKET;
import com.sds.egosara.dto.OrderingList;
import com.sds.egosara.service.OService;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@Controller
public class OController {
    @Autowired
    private OService osvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // 결제 성공 여부에 따라서 알람띄운 후 페이지를 인덱스로 이동하기
    // 1. 결제 성공 : kakaoSuccess
    @RequestMapping("/kakaoSuccess")
    public String kakaoSuccess(){
        return "index";
    }

    // 2. 결제 실패 : kakaoFail
    @RequestMapping("/kakaoFail")
    public String kakaoFail(){
        return "index";
    }

    // 3. 결제 취소 : kakaoCancel
    @RequestMapping("/kakaoCancel")
    public String kakaoCancel(){
        return "index";
    }


    // 카카오페이 결제를 위해 추가한 내용
    @Setter(onMethod_ = @Autowired)
    private OService getOsvc;

    // 결제
    // 결제 요청
    @PostMapping("/orderingExcu")
    public String ordering(@RequestParam("bId") String bId, @RequestParam("bNum") List<Integer> bNum, @ModelAttribute BASKET basket) {
        String result = osvc.orderExcu(bId, bNum);
        log.info("kakaoPay post :: " + getOsvc.orderExcu(bId, bNum));

        return "redirect:" + getOsvc.orderExcu(bId, bNum);
    }

    // 결제 성공
    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, @RequestParam("oNum") String oNum, @RequestParam("bId") String bId) {

        // BId : 로그인 아이디 -> basket에서 BSelect 가 'y'인거 정보 가져오기
        // ONum : 주문번호 -> ORDERING 테이블에 정보 insert
        mav = osvc.bSelectList(bId, oNum);

        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);


    }

    // 주문내역 목록 만들기 (마이페이지 -> 주문내역)
    // 주문번호 불러오기
    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    public ModelAndView getOrderList(@RequestParam("oId") String oId){
        mav = osvc.getOrderList(oId);
        return mav;
    }
    // 주문번호를 가지고 dto의 OrderingList를 이용해서 정보 담아오기
    @RequestMapping(value = "/orderInfo", method = RequestMethod.POST)
    public @ResponseBody List<OrderingList> orderInfo(@RequestParam("olNum") String olNum){
        List<OrderingList> orderList = osvc.orderInfo(olNum);

        return orderList;
    }

}
