package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;


@Data
@Alias("qcomment")
public class QCOMMENT {
    int qcNum;  // 댓글번호
    int qcbNum; // 댓글작성한 글 번호
    String qcId;
    String qcContent;
    String qcDate;
}
