package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("ordering")
public class ORDERING {
    private String oNum;
    private String oId;
    private int oBNum;
    private int oBuyNo;
    private int oPrice;
}
