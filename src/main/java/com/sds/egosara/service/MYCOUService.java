package com.sds.egosara.service;

import com.sds.egosara.dao.MYCOUDAO;
import com.sds.egosara.dto.COUPON;
import com.sds.egosara.dto.MYCOUPON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class MYCOUService {
    @Autowired
    private MYCOUDAO mycoudao;

    private ModelAndView mav = new ModelAndView();

    // mycouSave : 내 쿠폰 저장
    public void mycouSave(MYCOUPON mycoupon) {
        mycoudao.mycouSave(mycoupon);
    }

    // mycouList : 내 쿠폰 목록
    public ModelAndView mycouList(String cId) {

        // 상세보기 : DTO
        // 목록보기 : List<DTO>

        List<COUPON> mycouponList = mycoudao.mycouList(cId);

        mav.setViewName("MYCOU_List");
        mav.addObject("mycouponList",mycouponList);

        return mav;
    }

    // mycouDelete : 내 쿠폰 삭제
    public ModelAndView mycouDelete(@RequestParam("cId") String cId , @RequestParam("couNum") int couNum) {
        int result = mycoudao.mycouDelete(cId,couNum);

        if(result>0){
            mav.setViewName("index");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }
}
