package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@Alias("qna")
public class QNA {
    int qNum;
    String qId;
    String qTitle;
    String qContent;
    Date qDate;
    int qHit;
    MultipartFile qPhoto;
    String qPhotoName;
    String qPublic;
}
