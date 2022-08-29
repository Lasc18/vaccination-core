package ec.kruger.vacunacion.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.kruger.vacunacion.model.person.Person;
import ec.kruger.vacunacion.model.security.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * @author Santiago Araujo
 *
 * System Employe entity
 */
@Data
@Entity
@Table(name = "empleado", schema = "kru_company")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person person;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private ZonedDateTime admission_date;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VaccinatedEmployee> vaccinatedEmployeeSet;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
}
