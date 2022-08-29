package ec.kruger.vaccination.record.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.kruger.vaccination.util.Constant;
import ec.kruger.vaccination.util.DateDeserializer;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Santiago Araujo
 * Class for request input
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
public class VaccinatedEmployeeINP implements Serializable {

    @With
    @NotNull(message = "employee not be null")
    @JsonProperty("idEmployee")
    int idEmployee;

    @With
    @NotNull(message = "vaccinated date not be null")
    @JsonFormat(pattern = Constant.DATE_FORMAT)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("vaccinatedDate")
    ZonedDateTime vaccinatedDate;

    @With
    @NotNull(message = "vaccine type not be null")
    @JsonProperty("idVaccineType")
    int idVaccineType;

    @Tolerate
    VaccinatedEmployeeINP(){}
}