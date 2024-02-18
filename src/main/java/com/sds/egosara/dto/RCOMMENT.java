package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("rComment")
public class RCOMMENT {

    private int rNo;
    private int rcNum;
    private String rcId;
    private String rcContent;
    private int rcStar;
    private String rcDate;

}
