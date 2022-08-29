package ec.kruger.vaccination.repository.person;

import ec.kruger.vaccination.model.person.InformationPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author Santiago Araujo
 * Repository for {@link InformationPerson} entity
 */
public interface InformationPersonRepository extends JpaRepository<InformationPerson, Serializable> {

}
