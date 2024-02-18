package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("orderingList")
public class OrderingList {
    // 주문 내역에 띄울 데이터 dto만들기

    // 보여줄 데이터
    // 상품번호,  상품종류, 상품이름, 상품판매자, 해당상품 주문 수량, 상품가격 , 상품이미지, 총 결제가격
    // BNum,    GType  , GName , GId     , OBuyNo         , GPrice  , GPhoto  , OPrice
    // OlBNum,  OlType , OlName, Olsaler , OlBuyNo        , OlPrice , OlPhoto , OltotalPrice

    private int OlBNum;
    private String OlType;
    private String OlName;
    private String Olsaler;

    private int OlBuyNo;
    private int OlPrice;

    private String OlPhoto;
    private int OltotalPrice;

}
