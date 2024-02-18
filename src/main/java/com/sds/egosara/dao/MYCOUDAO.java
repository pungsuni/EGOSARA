package com.sds.egosara.dao;

import com.sds.egosara.dto.COUPON;
import com.sds.egosara.dto.MYCOUPON;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MYCOUDAO {

    // 내 쿠폰 저장
    void mycouSave(MYCOUPON mycoupon);

    // 내 쿠폰 목록
    List<COUPON> mycouList(String cId);

    // 내 쿠폰 삭제
    int mycouDelete(String cId, int couNum);
}
