package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("mycoupon")
public class MYCOUPON {
    private String mcId;    // 내 쿠폰 아이디 (쿠폰을 저장한 회원의 아이디)
    private int mcNum;      // 내 쿠폰 번호

}
