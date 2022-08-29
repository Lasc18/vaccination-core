package ec.kruger.vaccination.repository;

import ec.kruger.vaccination.model.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Repository for {@link Person} entity
 */
public interface PersonRepository extends JpaRepository<Person, Serializable> {

    /**
     * Get person by email
     * @param email email person
     * @return Optional<Person>
     */
    Optional<Person> findByEmail(String email);

    /**
     * Get person by identification
     * @param identification identification person
     * @return Optional<Person>
     */
    Optional<Person> findByIdentification(String identification);
}
