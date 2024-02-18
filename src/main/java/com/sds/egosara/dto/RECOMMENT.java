package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("reComment")
public class RECOMMENT {

    private int rcNumber;
    private int rcNo;
    private int reNum;
    private String reId;
    private String reContent;
    private String reDate;
}
