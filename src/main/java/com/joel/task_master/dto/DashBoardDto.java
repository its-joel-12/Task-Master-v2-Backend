package com.joel.task_master.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DashBoardDto {
    private Long totalEmployees;
    private Long totalTasks;
    private Long totalPendingTasks;
    private Long totalCompletedTasks;
}
