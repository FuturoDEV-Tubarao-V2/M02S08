package br.com.futurodev.exemplosspringmvc.repository;

import br.com.futurodev.exemplosspringmvc.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
