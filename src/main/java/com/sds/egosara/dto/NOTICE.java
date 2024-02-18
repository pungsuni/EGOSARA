package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@Alias("notice")
public class NOTICE {
    int nNum;
    String nId;
    String nTitle;
    String nContent;
    Date nDate;
    MultipartFile nPhoto;
    String nPhotoName;
}
