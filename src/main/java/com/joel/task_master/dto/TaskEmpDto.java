package com.joel.task_master.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEmpDto {
    // FIELDS ------------------------------------------------------------------------------------------------------
    private Long taskId;
    private String taskTitle;
    private String taskDescription;
    private String taskStatus;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dueDate;

    private String employeeName;
    // FIELDS ------------------------------------------------------------------------------------------------------
}
