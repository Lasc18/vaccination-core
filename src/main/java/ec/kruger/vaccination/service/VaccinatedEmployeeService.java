package ec.kruger.vaccination.service;

import ec.kruger.vaccination.model.company.Employee;
import ec.kruger.vaccination.record.input.PersonEmployeeINP;
import ec.kruger.vaccination.record.output.PersonEmployeeOUT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author Santiago Araujo
 * Service of employee
 */
public interface EmployeeService {

    /**
     * Register an employee with the person information, if you do not have a user or generate
     *
     * @param personEmployeeINP input data
     * @return employee registered
     */
    Optional<Employee> registerEmployee(PersonEmployeeINP personEmployeeINP);

    /**
     * Get employee list of system
     * @param pageable pagination
     * @return Page<EmployeeOUT> employee, person and information person data
     */
    Page<PersonEmployeeOUT> getEmployee(Pageable pageable);

    /**
     * Delete employee with the person and information person
     * @param id employee id
     */
    void deleteEmployee(int id);

    /**
     * Get employee, person and information person by id
     * @param id employee id
     * @return personEmployeeOUT with data
     */
    PersonEmployeeOUT getEmployeeById(int id);
}
