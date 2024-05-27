package com.joel.task_master.service;

import com.joel.task_master.dto.DashBoardDto;
import com.joel.task_master.model.Employee;
import com.joel.task_master.model.Task;
import com.joel.task_master.repository.EmployeeRepository;
import com.joel.task_master.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OtherServiceImpl implements OtherService {
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private TaskRepository taskRepo;

    private long totalTasksCompleted = 0;

    @Override

    public DashBoardDto getDashBoardData() {

        totalTasksCompleted = 0;

        Pageable empPageable = PageRequest.of(0, 5);
        Page<Employee> allPageEmps = employeeRepo.findAll(empPageable);

        Pageable taskPageable = PageRequest.of(0, 5);
        Page<Task> allPageTasks = taskRepo.findAll(taskPageable);

        DashBoardDto dashBoardDto = new DashBoardDto();


        allPageTasks.forEach(t -> {
            if (t.getTaskStatus().equalsIgnoreCase("completed")) {
                totalTasksCompleted++;
            }
        });

        dashBoardDto.setTotalEmployees(allPageEmps.getTotalElements());
        dashBoardDto.setTotalTasks(allPageTasks.getTotalElements());
        dashBoardDto.setTotalCompletedTasks(totalTasksCompleted);
        dashBoardDto.setTotalPendingTasks(allPageTasks.getTotalElements() - totalTasksCompleted);
        return dashBoardDto;
    }
}
