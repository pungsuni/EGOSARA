package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("goods")
public class GOODS {
    /*
    CREATE TABLE GOODS(
        GNUM       NUMBER PRIMARY KEY,
        GID        NVARCHAR2(20),
        GTYPE      NVARCHAR2(10) NOT NULL,
        GNAME      NVARCHAR2(20) NOT NULL,
        GPRICE     NUMBER NOT NULL,
        GDATE      DATE NOT NULL,
        GSTOCK     NUMBER NOT NULL,
        GCONTENT   NVARCHAR2(300) NOT NULL,
        GPHOTHO    NVARCHAR2(100) NOT NULL
    );
    */
    private int gNum;
    private String gId;
    private String gName;
    private int gPrice;
    private String gDate;
    private int gStock;
    private String gContent;
    private MultipartFile gFile;
    private String gPhoto;

    /* 추가 */
    private String gCate;
    private int gPrice1;
    private int gPrice2;
    private String selectVal;
    private int gHit;
    private String keyword;
    private double gStar;

}
