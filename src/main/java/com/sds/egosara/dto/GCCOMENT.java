package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("gccoment")
public class GCCOMENT {

    private int gNo;                // 댓글번호
    private int gcNum;              // 상품 번호
    private String gcId;            // 댓글작성자
    private String gcContent;       // 댓글내용
    private int gcStar;             // 댓글별점
    private MultipartFile gcFile;   // 댓글사진
    private String gcPhoto;         // 댓글사진이름
    private String gcDate;          // 댓글날짜

}
