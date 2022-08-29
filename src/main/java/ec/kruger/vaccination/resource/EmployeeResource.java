package ec.kruger.vaccination.resource;

import ec.kruger.vaccination.model.company.Employee;
import ec.kruger.vaccination.record.input.PersonEmployeeINP;
import ec.kruger.vaccination.record.output.PersonEmployeeOUT;
import ec.kruger.vaccination.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Contanins employee resouces
 */
@RestController
@RequestMapping("/api/admin/employee")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    @Autowired
    EmployeeService employeeService;

    /**
     * {@code POST /register : Register employee data with person and information person}
     *
     * @param personEmployeeINP data to register
     * @return {@link ResponseEntity} with the content of employee entity
     */
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody PersonEmployeeINP personEmployeeINP) {
        log.debug("Invoke rest service to register employee. REQUEST: {}", personEmployeeINP);
        Optional<Employee> employee = employeeService.registerEmployee(personEmployeeINP);
        return employee.isPresent() ? ResponseEntity.ok(employee.get()) : ResponseEntity.noContent().build();
    }
    /**
     * {@code GET / : Get the employee list with the employee data and person and information person}
     *
     * @param pageable pagination information
     * @return {@link ResponseEntity} with the content of employee, person and information person data
     */
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonEmployeeOUT>> getEmployee(@Valid Pageable pageable){
        log.debug("Invoke rest service get employee.");
        Page<PersonEmployeeOUT> employees = employeeService.getEmployee(pageable);
        return ResponseEntity.ok().body(employees.getContent());
    }

    /**
     * {@code DELETE / : Delete the employee with your person and information person}
     *
     * @param id employee id
     * @return {@link ResponseEntity} with the content of employee, person and information person data
     */
    @DeleteMapping(value = "/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "id") final int id){
        log.debug("Invoke rest service delete employee. REQUEST: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee successfully removed.");
    }
    /**
     * {@code GET / : Get the employee and person and information person by id}
     *
     * @param id employee id
     * @return {@link ResponseEntity} with the content of employee, person and information person data
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonEmployeeOUT> getEmployee(@PathVariable(value = "id") final int id){
        log.debug("Invoke rest service get employee by id. REQUEST: {}", id);
        PersonEmployeeOUT employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok().body(employee);
    }


}
