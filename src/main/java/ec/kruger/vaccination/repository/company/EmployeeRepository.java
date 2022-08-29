package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.model.company.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author Santiago Araujo
 * Repository for {@link Employee} entity
 */
public interface EmployeeRepository extends JpaRepository<Employee, Serializable> {

}
