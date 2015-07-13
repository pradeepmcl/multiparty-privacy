package edu.ncsu.mas.platys.multiparty_privacy.service;

import java.util.List;

import edu.ncsu.mas.platys.multiparty_privacy.model.Employee;

public interface EmployeeService {

  Employee findById(int id);

  void saveEmployee(Employee employee);

  void updateEmployee(Employee employee);

  void deleteEmployeeBySsn(String ssn);

  List<Employee> findAllEmployees();

  Employee findEmployeeBySsn(String ssn);

  boolean isEmployeeSsnUnique(Integer id, String ssn);

}
