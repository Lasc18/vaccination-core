package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.model.company.Employee;
import ec.kruger.vaccination.model.company.VaccinatedEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Repository for {@link VaccinatedEmployee} entity
 */
public interface VaccinatedEmployeeRepository extends JpaRepository<VaccinatedEmployee, Serializable> {
    /**
     * Get vaccinated employee by employee
     * @param employee employee entity
     * @return Set<VaccinatedEmployee> set with vaccinatedEmployee entity
     */
    List<VaccinatedEmployee> findByEmployeeOrderById(Employee employee);

    /**
     * Get last vaccinated employee entity by employee
     * @param employee employee entity
     * @return Optional<VaccinatedEmployee> entity
     */
    Optional<VaccinatedEmployee> findFirstByEmployeeOrderByIdDesc(Employee employee);
}
