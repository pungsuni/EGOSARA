package com.sds.egosara.service;

import com.sds.egosara.dao.COUDAO;
import com.sds.egosara.dto.COUPON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class COUService {

    @Autowired
    private COUDAO coudao;

    private ModelAndView mav = new ModelAndView();

    // couWrite : 쿠폰 등록
    public ModelAndView couWrite(COUPON coupon) {

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = coudao.couWrite(coupon);

        // 성공하면 result = 1 , 실패하면 result = 0

        if(result>0){
            mav.setViewName("redirect:/couList");
        } else {
            mav.setViewName("COU_WriteForm");
        }

        return mav;
    }

    // couList : 쿠폰 목록
    public ModelAndView couList() {

        // 상세보기 : DTO
        // 목록보기 : List<DTO>

        List<COUPON> couponList = coudao.couList();

        mav.setViewName("COU_List");
        mav.addObject("couponList",couponList);
        return mav;
    }
}
