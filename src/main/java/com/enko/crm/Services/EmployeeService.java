package com.enko.crm.Services;

import com.enko.crm.Models.Department;
import com.enko.crm.Models.Employee;
import com.enko.crm.Models.Gender;
import com.enko.crm.Repositories.DepartmentRepository;
import com.enko.crm.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not found") );
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmpDepartment(Integer emp_id , Integer dept_id) {
        Employee employee = employeeRepository.getReferenceById(emp_id);
        if(employee == null) {
            throw new RestClientException("Employee with id "+emp_id+" not found");
        }

        Department department = departmentRepository.getReferenceById(dept_id);
        if(department == null) {
            throw new RestClientException("Department with id "+dept_id+" not found");
        }

        employee.setDepartment(department);
        employeeRepository.save(employee);

        return employee;
    }

    public List<Employee> findEmployeesByGender(Gender gender) {
        return employeeRepository.findByGender(gender);
    }

    public  List<Employee> searchEmployeesByGender(Gender gender) {
        return employeeRepository.searchByGender(gender);
    }

    public Employee updateEmployee(Employee employee) {
        Employee emp = employeeRepository.getById(employee.getId());
        if(emp == null) {
            throw new RestClientException("Employee with id "+employee.getId()+" not found");
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
