package edu.ncsu.mas.platys.multiparty_privacy.dao;

import java.util.List;

import edu.ncsu.mas.platys.multiparty_privacy.model.Employee;

public interface EmployeeDao {

  Employee findById(int id);

  void saveEmployee(Employee employee);

  void deleteEmployeeBySsn(String ssn);

  List<Employee> findAllEmployees();

  Employee findEmployeeBySsn(String ssn);

}
