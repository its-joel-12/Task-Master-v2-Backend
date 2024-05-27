package com.joel.task_master.repository;

import com.joel.task_master.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmpNameContaining(String empName);
}
