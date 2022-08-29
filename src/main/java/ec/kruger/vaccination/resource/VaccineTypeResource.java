package ec.kruger.vaccination.resource;

import ec.kruger.vaccination.model.company.VaccinatedEmployee;
import ec.kruger.vaccination.record.input.VaccinatedEmployeeINP;
import ec.kruger.vaccination.service.VaccinatedEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Contanins vaccinated employee resouces
 */
@RestController
@RequestMapping("/api/vaccinated-employee")
public class VaccinatedEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(VaccinatedEmployeeResource.class);

    @Autowired
    VaccinatedEmployeeService vaccinatedEmployeeService;

    /**
     * {@code POST /register : Register vaccinated employee data}
     *
     * @param vaccinatedEmployeeINP data to register
     * @return {@link ResponseEntity} with the content of vaccinated employee entity
     */
    @PostMapping("/register")
    public ResponseEntity<VaccinatedEmployee> registerVaccinatedEmployee(
            @Valid @RequestBody VaccinatedEmployeeINP vaccinatedEmployeeINP) {
        log.debug("Invoke rest service to register the vaccinated employee. REQUEST: {}", vaccinatedEmployeeINP);
        Optional<VaccinatedEmployee> vaccinatedEmployee =
                vaccinatedEmployeeService.registerVaccinatedEmployee(vaccinatedEmployeeINP);
        return vaccinatedEmployee.isPresent() ? ResponseEntity.ok(vaccinatedEmployee.get())
                : ResponseEntity.noContent().build();
    }
}
