package com.lazydaze.weblog.admin.model.vo.dashboard;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindDashboardCategoryStatisticsInfoRspVO {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 文章总数
     */
    private Integer value;
}
