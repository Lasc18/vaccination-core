package ec.kruger.vacunacion.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.kruger.vacunacion.model.company.VaccinatedEmployee;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Santiago Araujo
 *
 * System Role entity
 */
@Data
@Entity
@Table(name = "rol", schema = "kru_security")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Column(name = "nombre", length = 50, unique = true)
    private String name;

    @NotNull
    @Column(name = "es_administrador", nullable = false)
    private boolean admin;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> userSet;

}
