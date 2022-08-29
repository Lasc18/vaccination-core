package ec.kruger.vacunacion.model.company;

import ec.kruger.vacunacion.model.common.VaccineType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Santiago Araujo
 *
 * System Vaccinated Employee entity
 */
@Entity
@Table(name = "empleado_vacunado", schema = "kru_company")
public class VaccinatedEmployee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vaccine_type", nullable = false)
    private VaccineType vaccineType;

    @NotNull
    @Column(name = "fecha_vacunacion", nullable = false)
    private ZonedDateTime vaccineDate;

    @NotNull
    @Size(min = 1, max = 9999)
    @Column(name = "numero_dosis", nullable = false)
    private short doseNumber;

}
