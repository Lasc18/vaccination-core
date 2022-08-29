package ec.kruger.vacunacion.model.security;

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
 * System User entity
 */
@Data
@Entity
@Table(name = "usuario", schema = "kru_security")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    private Employee employee;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Column(name = "usuario", length = 50, unique = true)
    private String username;

    @NotEmpty
    @Size(min = 1, max = 200)
    @Column(name = "contrasena", length = 200, nullable = false)
    private String password;

    @NotNull
    @Column(name = "activo", nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Role role;
}
