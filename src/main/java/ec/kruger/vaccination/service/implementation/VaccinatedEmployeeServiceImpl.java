package ec.kruger.vaccination.service.implementation;

import ec.kruger.vaccination.model.company.Employee;
import ec.kruger.vaccination.record.input.PersonEmployeeINP;
import ec.kruger.vaccination.record.output.PersonEmployeeOUT;
import ec.kruger.vaccination.service.EmployeeService;
import ec.kruger.vaccination.service.process.EmployeeProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Service for employee
 */
@Service
public class EmployeeServiceImpl extends EmployeeProcess implements EmployeeService {

    /**
     * Service for register employee, person and information person data
     *
     * @param personEmployeeINP input data
     * @return Optional<Employee> employee entity data
     */
    @Override
    @Transactional
    public Optional<Employee> registerEmployee(PersonEmployeeINP personEmployeeINP) {
        return register(personEmployeeINP);
    }

    /**
     * Service for get employee, person and information person data
     *
     * @param pageable pagination information
     * @return Page<EmployeeOUT> employee, person, information person data
     */
    @Override
    public Page<PersonEmployeeOUT> getEmployee(Pageable pageable){
        return getPersonEmployee(pageable);
    }

    /**
     * Service for delete an employee, person and information person data
     *
     * @param id employee id
     */
    @Override
    public void deleteEmployee(int id) { deletePersonEmployee(id); }

    /**
     * Service for get employee, person, information person by id
     *
     * @param id employee id
     * @return personEmployeeOUT with data
     */
    public PersonEmployeeOUT getEmployeeById(int id) {
        return getPersonEmployeeById(id);
    }
}
