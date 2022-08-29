package ec.kruger.vacunacion.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.kruger.vacunacion.model.company.Employee;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author Santiago Araujo
 *
 * System Person entity
 */
@Data
@Entity
@Table(name = "person", schema = "kru_person")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 10, max = 10)
    @Column(name = "identificacion", length = 10, unique = true)
    private String identification;

    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(name = "nombres", length = 100)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(name = "apellidos", length = 100)
    private String surnames;

    @NotNull
    @Size(min = 6, max = 200)
    @Column(name = "correo_electronico", length = 200, unique = true)
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private InformationPerson informationPerson;

    @JsonIgnore
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Employee employee;
}
