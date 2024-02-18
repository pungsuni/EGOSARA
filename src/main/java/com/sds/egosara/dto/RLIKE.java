package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("like")
public class RLIKE {
    private int lrNum;
    private String lId;
    private String lHeart;
    private int rLike;
}
