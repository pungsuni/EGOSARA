package com.sds.egosara.service;

import com.sds.egosara.dao.BDAO;
import com.sds.egosara.dto.BASKET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class BService {
    @Autowired
    private BDAO dao;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // G_View 페이지에서 받아온 정보를 장바구니에 저장하기
    public ModelAndView bGetGoods(BASKET basket) {
        int result = dao.bGetGoods(basket);
        if(result>0){
            // 성공
            mav.setViewName("redirect:/bView?bId=" + basket.getBId());
        } else {
            // 실패
            mav.setViewName("redirect:/gView?bNum=" + basket.getBNum());
        }

        return mav;
    }

    // 장바구니 페이지로 내가 장바구니 담은 물건 모두 가져오기
    public ModelAndView bView(String bId) {
        List<BASKET> basketList = dao.bView(bId);

        mav.addObject("basketList", basketList);
        mav.setViewName("B_View");

        return mav;
    }

    // B_View에서 받아온 정보 중 체크박스에 선택된 데이터만 BSelete = 'y' 로 업데이트 하기
    public void bSelectIn(BASKET bas) {
        dao.bSelectIn(bas);
    }

    // List에 BSelete = 'y', BId = ${session.loginId} 인 정보 받아와서 addObject로 O_View페이지에 넘기기
    public ModelAndView bSelectList(String bId) {
        List<BASKET> basList = dao.bSelectList(bId);

        mav.addObject("basList", basList);
        mav.setViewName("O_Check");

        return mav;
    }

    // 장바구니에 담은 제품의 구매수량 변경
    public ModelAndView bModify(BASKET basket) {
        // 일단 해당 제품 삭제 -> 아래의 bDelete과 동일
        int result = dao.bDelete(basket);
        // 삭제가 되면, 상품 번호를 가져가서 G_View로 이동해서 다시 제품을 담을 수 있게 하기
        if (result>0){
            mav.setViewName("redirect:/gView?GNum=" + basket.getBNum());
        } else {
            mav.setViewName("redirect:/bView?bId=" + basket.getBId());
        }
        return mav;
    }

    // 장바구니 항목 삭제
    public ModelAndView bDelete(BASKET basket) {
        // 삭제가 잘 됐는지 확인
        int result = dao.bDelete(basket);

        if(result>0){
            mav.setViewName("redirect:/bView?bId=" + basket.getBId());
        }
        return mav;
    }

}