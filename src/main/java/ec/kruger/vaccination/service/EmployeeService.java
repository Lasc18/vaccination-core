package ec.kruger.vacunacion.service;

import ec.kruger.vacunacion.model.company.Employee;
import ec.kruger.vacunacion.record.input.EmployeeINP;

import java.util.Optional;

/**
 * @author Santiago Araujo
 * Service of employee
 */
public interface EmployeeService {

    /**
     * Register an employee with the person information, if you do not have a user or generate
     *
     * @param employeeINP input data
     * @return employee registered
     */
    Optional<Employee> registerEmployee(EmployeeINP employeeINP);
}
