package com.joel.task_master.service;

import com.joel.task_master.dto.EmployeeDTO;
import com.joel.task_master.exception.ResourceNotFoundException;
import com.joel.task_master.model.Employee;
import com.joel.task_master.model.Task;
import com.joel.task_master.repository.EmployeeRepository;
import com.joel.task_master.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;

    // SAVE EMPLOYEE WITH TASKS ---------------------------------------------------------------------
    @Override
    public Employee saveEmployeeWithTask(Employee employee) {
        if (employee == null) {
            throw new ResourceNotFoundException("Employee Object is Empty!!");
        } else {
            if (employee.getTasks() == null) {
                throw new ResourceNotFoundException("Task object is Empty!!");
            } else {
                employee.getTasks().forEach(task -> task.setEmployee(employee));
                return employeeRepository.save(employee);
            }
        }
    }

    // SAVE EMPLOYEE --------------------------------------------------------------------------------
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setEmpName(employeeDTO.getEmpName().strip());
        employee.setEmpEmail(employeeDTO.getEmpEmail().strip());
        employee.setEmpDesignation(employeeDTO.getEmpDesignation().strip());
        employee = employeeRepository.save(employee);

        employeeDTO.setEmpId(employee.getEmpId());
        return employeeDTO;

    }

    // GET EMPLOYEE BY TASK ID ----------------------------------------------------------------------
    @Override
    public EmployeeDTO getEmployeeByTaskId(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            Task task = taskRepository.findById(taskId).orElse(null);
            Employee employee = task.getEmployee();

            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmpId(employee.getEmpId());
            employeeDTO.setEmpName(employee.getEmpName());
            employeeDTO.setEmpEmail(employee.getEmpEmail());
            employeeDTO.setEmpDesignation(employee.getEmpDesignation());

            return employeeDTO;
        } else {
            throw new ResourceNotFoundException("Task NOT FOUND with the given ID: " + taskId);
        }
    }

    // GET EMPLOYEE BY ID ---------------------------------------------------------------------------
    @Override
    public EmployeeDTO getEmployeeById(Long empId) {
        if (employeeRepository.existsById(empId)) {
            Employee employee = employeeRepository.findById(empId).orElse(null);

            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmpId(employee.getEmpId());
            employeeDTO.setEmpName(employee.getEmpName());
            employeeDTO.setEmpEmail(employee.getEmpEmail());
            employeeDTO.setEmpDesignation(employee.getEmpDesignation());
            return employeeDTO;
        }
        throw new ResourceNotFoundException("Employee NOT FOUND with the given ID: " + empId);
    }

    // GET ALL EMPLOYEES ----------------------------------------------------------------------------
    @Override
    public List<EmployeeDTO> getAllEmployee(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("empId"));

        Page<Employee> allPageEmployees = employeeRepository.findAll(pageable);
        List<Employee> allEmployee = allPageEmployees.getContent();

//        System.out.println("total pages: " + allPageEmployees.getTotalPages());

        if (!allEmployee.isEmpty()) {
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();

            allEmployee.forEach(employee -> employeeDTOList.add(
                    new EmployeeDTO(
                            employee.getEmpId(),
                            employee.getEmpName(),
                            employee.getEmpEmail(),
                            employee.getEmpDesignation()
                    )));
            return employeeDTOList;
        } else {
            throw new ResourceNotFoundException("Either this page has no employees OR there are No employees in the database !");
        }
    }

    // UPDATE EMPLOYEE BY ID ------------------------------------------------------------------------
    @Override
    public EmployeeDTO updateEmployeeById(Long empId, EmployeeDTO employeeDTO) {
        if (employeeRepository.existsById(empId)) {
            if (employeeDTO == null) {
                throw new ResourceNotFoundException("Employee Object is Empty!!");
            } else {

                Employee updatedEmployee = new Employee();
                updatedEmployee.setEmpId(empId);
                updatedEmployee.setEmpName(employeeDTO.getEmpName().strip());
                updatedEmployee.setEmpEmail(employeeDTO.getEmpEmail().strip());
                updatedEmployee.setEmpDesignation(employeeDTO.getEmpDesignation().strip());
                employeeRepository.save(updatedEmployee);

                employeeDTO.setEmpId(empId);
                return employeeDTO;

            }
        } else {
            throw new ResourceNotFoundException("Employee NOT FOUND with the given ID: " + empId);
        }
    }

    // DELETE EMPLOYEE BY ID ------------------------------------------------------------------------
    @Override
    public void deleteEmployeeById(Long empId) {
        if (employeeRepository.existsById(empId)) {
            employeeRepository.deleteById(empId);
        } else {
            throw new ResourceNotFoundException("Employee doesn't exists in the database with emp ID: " + empId);
        }
    }

    // GET EMPLOYEES BY NAME
    @Override
    public List<EmployeeDTO> getEmployeesByName(String empName) {
        List<Employee> employess = employeeRepository.findByEmpNameContaining(empName);
        if(!employess.isEmpty()) {
            List<EmployeeDTO> empDtos = employess
                    .stream()
                    .map(e -> modelMapper.map(e, EmployeeDTO.class))
                    .collect(Collectors.toList());

            return empDtos;
        }else{
            throw new ResourceNotFoundException("No results founds !");
        }
    }


}
