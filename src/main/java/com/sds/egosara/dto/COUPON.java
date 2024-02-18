package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("coupon")
public class COUPON {
    private int couNum;             // 쿠폰번호
    private String couName;         // 쿠폰이름
    private String couContent;      // 쿠폰내용
    private int couPrice;           // 쿠폰 가격
}
