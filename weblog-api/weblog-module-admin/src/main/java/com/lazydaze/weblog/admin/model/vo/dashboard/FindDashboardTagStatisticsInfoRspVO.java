package com.lazydaze.weblog.admin.model.vo.dashboard;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindDashboardTagStatisticsInfoRspVO {
    private List<String> tagNames;

    private List<Integer> articleCounts;
}
