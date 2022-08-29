package ec.kruger.vaccination.record.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ec.kruger.vaccination.util.Constant;
import ec.kruger.vaccination.util.DateDeserializer;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Santiago Araujo
 * Class for request input
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
public class VaccionatedEmployeeINP implements Serializable {

    @JsonProperty("id")
    Integer id;

    @With
    @NotNull(message = "identification not be null")
    @Size(min = 10, max = 10, message = "Identification must be 10 characters")
    @JsonProperty("identification")
    String identification;

    @With
    @NotNull(message = "name not be null")
    @JsonProperty("name")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name;

    @With
    @NotNull(message = "surnames not be null")
    @JsonProperty("surnames")
    @Size(min = 3, max = 100, message = "Surname must be between 3 and 100 characters")
    String surnames;

    @With
    @NotNull(message = "name not be null")
    @JsonProperty("email")
    @Size(min = 6, max = 200, message = "Email must be between 3 and 200 characters")
    @Email(message = "email not valid")
    String email;

    @With
    @JsonProperty("informationPerson")
    InformationPerson informationPerson;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    @Builder
    public static class InformationPerson implements Serializable{

        @With
        @NotNull(message = "Birthdate not be null")
        @JsonFormat(pattern = Constant.DATE_FORMAT)
        @JsonDeserialize(using = DateDeserializer.class)
        @JsonProperty("birthDate")
        ZonedDateTime birthDate;

        @With
        @NotNull(message = "Direction not be null")
        @JsonProperty("direction")
        String direction;

        @With
        @NotNull(message = "Mobile phone not be null")
        @JsonProperty("mobilePhone")
        String mobilePhone;

        @Tolerate
        InformationPerson(){}
    }
    @Tolerate
    VaccionatedEmployeeINP(){}
}