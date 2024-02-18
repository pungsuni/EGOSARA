package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("smember")
public class SMEMBER {
    String sId;         // 업체아이디
    String sPw;         // 업체비밀번호
    String sName;       // 업체이름
    String sPhone;      // 업체전화번호
    String sAddr;       // 업체주소
    String sEmail;      // 업체이메일
    String sNumber;     // 사업자번호

    String addr1;       // 주소1
    String addr2;       // 주소2
    String addr3;       // 주소3
}
