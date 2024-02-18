package com.sds.egosara.controller;

import com.sds.egosara.dto.BASKET;
import com.sds.egosara.service.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BController {

    @Autowired
    private BService bsvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // bGetGoods : G_View 페이지에서 재고 내 수량을 선택하여 Basket에 값 넣기
    @RequestMapping(value = "/bGetGoods", method = RequestMethod.POST)
    public ModelAndView bGetGoods(@ModelAttribute BASKET basket){
        mav = bsvc.bGetGoods(basket);
        return mav;
    }

    // bView : 장바구니 페이지에서 내가 담은 상품 조회(아이디를 가져옴)
    @RequestMapping(value = "/bView")
    public ModelAndView bView(@RequestParam("bId") String bId){
        mav = bsvc.bView(bId);
        return mav;
    }

    // bSelectIn : 선택한 제품의 상품번호와 아이디를 가져와서 BSelete를 'y'로 변경하기
    @RequestMapping(value = "/bSelectIn", method = RequestMethod.GET)
    public ModelAndView bSelectIn(@RequestParam("bId") String bId, @RequestParam("bNum") List<Integer> bNum){
        // 객체 bas 선언
        BASKET bas = new BASKET();
        // bas에 로그인아이디 BId로 설정
        bas.setBId(bId);
        // 반복문을 이용해서 bas에 상품번호를 BNum으로 설정
        for(int i=0; i< bNum.size(); i++){
            bas.setBNum(bNum.get(i));
            bsvc.bSelectIn(bas);
        }
        // bSelectList : BSelete = 'y', BId = ${session.loginId}인 데이터를 리스트로 받아오기
        mav = bsvc.bSelectList(bId);
        return mav;
    }

    // bModify : 장바구니 목록에서 담은 상품의 수량 변경하기
    @RequestMapping(value = "/bModify", method = RequestMethod.GET)
    public ModelAndView bModify(@RequestParam("bId") String bId, @RequestParam("bNum") int bNum){
        // basket 객체 선언
        BASKET basket = new BASKET();

        // basket에 BId, BNum 설정
        basket.setBNum(bNum);
        basket.setBId(bId);

        mav = bsvc.bModify(basket);

        return mav;
    }

    // bDelete : 장바구니 목록에서 로그인 아이디와 상품번호를 가져와서 삭제
    @RequestMapping(value = "/bDelete", method = RequestMethod.GET)
    public ModelAndView bDelete(@RequestParam("bId") String bId, @RequestParam("bNum") int bNum){
        // basket 객체 선언
        BASKET basket = new BASKET();

        // basket에 BId, BNum 설정
        basket.setBNum(bNum);
        basket.setBId(bId);

        mav = bsvc.bDelete(basket);

        return mav;
    }


}