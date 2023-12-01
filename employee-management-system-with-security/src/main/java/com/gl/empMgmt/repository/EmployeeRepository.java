package com.gl.empMgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gl.empMgmt.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
