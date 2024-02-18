package com.sds.egosara.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("page")
public class PAGE {

    private int page;
    private int maxPage;
    private int startPage;
    private int endPage;
    private int startRow;
    private int endRow;
}
