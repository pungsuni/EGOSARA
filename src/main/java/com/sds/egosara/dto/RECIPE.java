package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("recipe")
public class RECIPE {
    private int rNum;
    private String rId;
    private String rName;
    private String rLevel;
    private String rType;
    private String rFood;
    private String rOven;
    private String rAmount;
    private int rTime;
    private String rContent;
    MultipartFile rPhoto1; // 파일자체
    String rPhotoName1;    // 파일이름
    MultipartFile rPhoto2; // 파일자체
    String rPhotoName2;    // 파일이름
    MultipartFile rPhoto3; // 파일자체
    String rPhotoName3;    // 파일이름
    private String rTag;
    private String rDate;
    private int rHit;
    private int rLike;
    private double rStar;
}
