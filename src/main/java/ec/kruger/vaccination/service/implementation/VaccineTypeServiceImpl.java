package ec.kruger.vaccination.service.implementation;

import ec.kruger.vaccination.model.company.VaccinatedEmployee;
import ec.kruger.vaccination.record.input.VaccinatedEmployeeINP;
import ec.kruger.vaccination.service.VaccinatedEmployeeService;
import ec.kruger.vaccination.service.process.VaccinatedEmployeeProcess;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Service for vaccinated employee
 */
@Service
public class VaccinatedEmployeeServiceImpl extends VaccinatedEmployeeProcess implements VaccinatedEmployeeService {

    /**
     * Service for register the vaccinated employee
     *
     * @param vaccinatedEmployeeINP input data
     * @return Optional<VaccinatedEmployee> vaccinated employee entity data
     */
    @Override
    @Transactional
    public Optional<VaccinatedEmployee> registerVaccinatedEmployee(VaccinatedEmployeeINP vaccinatedEmployeeINP) {
        return register(vaccinatedEmployeeINP);
    }
}
