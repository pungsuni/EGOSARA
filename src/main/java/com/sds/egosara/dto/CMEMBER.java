package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("cmember")
public class CMEMBER {
    String cId;                     // 회원아이디
    String cNickname;               // 회원닉네임
    String cPw;                     // 회원비밀번호
    String cName;                   // 회원이름
    String cBirth;                  // 회원생일
    String cPhone;                  // 회원전화번호
    String cAddr;                   // 회원주소
    String cEmail;                  // 회원이메일
    MultipartFile cProfile;         // 회원프로필
    String cProfileName;            // 회원프로필이름

    String addr1;                   // 주소1
    String addr2;                   // 주소2
    String addr3;                   // 주소3
}
