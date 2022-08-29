package ec.kruger.vacunacion.service.implementation;

import ec.kruger.vacunacion.model.company.Employee;
import ec.kruger.vacunacion.record.input.EmployeeINP;
import ec.kruger.vacunacion.service.EmployeeService;
import ec.kruger.vacunacion.service.process.EmployeeProcess;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Service for employee
 */
@Service
public class EmployeeServiceImpl extends EmployeeProcess implements EmployeeService {

    @Override
    @Transactional
    public Optional<Employee> registerEmployee(EmployeeINP employeeINP) {
        return register(employeeINP);
    }
}
