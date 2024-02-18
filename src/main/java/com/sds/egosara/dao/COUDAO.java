package com.sds.egosara.dao;

import com.sds.egosara.dto.COUPON;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface COUDAO {

    // 쿠폰 등록
    int couWrite(COUPON coupon);

    // 쿠폰 목록
    List<COUPON> couList();
}
