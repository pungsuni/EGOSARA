package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("basket")
public class BASKET {
    /*
    실제 사용 DB와 dto와 다름!!
    CREATE TABLE BASKET(
        BID    NVARCHAR2(20),
        BNUM   NUMBER,
        BUYNO   NUMBER,
        BSELECT NVARCHAR2(2),

        CONSTRAINT FK_BASKET_DNUM FOREIGN KEY(BNUM) REFERENCES GOODS(GNUM),
        CONSTRAINT FK_BASKET_DID FOREIGN KEY(BID) REFERENCES SDSMEMBER(SID),
        CONSTRAINT PK_BASKET PRIMARY KEY(BID, BNUM)
    );
    */
    private String bId;
    private int bNum;
    private String bType;
    private String bName;
    private int bPrice;
    private int buyNo;
    private int bStock;
    private String bSelect;
}
