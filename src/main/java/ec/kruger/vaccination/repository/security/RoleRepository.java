package ec.kruger.vaccination.repository.security;

import ec.kruger.vaccination.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author Santiago Araujo
 * Repository for {@link User} entity
 */
public interface UserRepository extends JpaRepository<User, Serializable> {

}
