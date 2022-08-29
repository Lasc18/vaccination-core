package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.model.common.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author Santiago Araujo
 * Repository for {@link VaccineType} entity
 */
public interface VaccineTypeRepository extends JpaRepository<VaccineType, Serializable> {

}
