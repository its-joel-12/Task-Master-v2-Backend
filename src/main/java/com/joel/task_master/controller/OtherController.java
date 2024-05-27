package com.joel.task_master.controller;

import com.joel.task_master.dto.DashBoardDto;
import com.joel.task_master.service.OtherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task-master/api/dashboard")
@Tag(name = "Other Controller")
public class OtherController {
    @Autowired
    private OtherService otherService;

    @GetMapping
    public ResponseEntity<DashBoardDto> getDashboardData() {
        return new ResponseEntity<>(otherService.getDashBoardData(), HttpStatus.OK);
    }
}
