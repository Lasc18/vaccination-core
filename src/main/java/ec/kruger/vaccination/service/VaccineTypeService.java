package ec.kruger.vaccination.service;

import ec.kruger.vaccination.model.company.VaccinatedEmployee;
import ec.kruger.vaccination.record.input.VaccinatedEmployeeINP;

import java.util.Optional;

/**
 * @author Santiago Araujo
 * Service of vaccinated employee
 */
public interface VaccinatedEmployeeService {

    /**
     * Register the vaccinated employee
     *
     * @param vaccinatedEmployeeINP input data
     * @return vaccinated employee registered
     */
    Optional<VaccinatedEmployee> registerVaccinatedEmployee(VaccinatedEmployeeINP vaccinatedEmployeeINP);
}
