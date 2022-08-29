package ec.kruger.vacunacion.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Santiago Araujo
 *
 * System Information Person entity
 */
@Data
@Entity
@Table(name = "persona_informacion", schema = "kru_person")
public class InformationPerson implements Serializable {

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
    @Column(name = "fecha_nacimiento", nullable = false)
    private ZonedDateTime birthDate;

    @NotEmpty
    @Size(min = 3, max = 200)
    @Column(name = "direccion_domicilio", length = 200, nullable = false)
    private String direction;

    @NotEmpty
    @Size(min = 10, max = 10)
    @Column(name = "telefono_movil", length = 10, nullable = false)
    private String mobilePhone;

    @NotNull
    @Column(name = "vacunado", nullable = false)
    private boolean vaccinated;

}
