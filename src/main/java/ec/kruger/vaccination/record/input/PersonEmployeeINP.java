package ec.kruger.vaccination.record.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import ec.kruger.vaccination.util.Constant;
import ec.kruger.vaccination.util.DateDeserializer;


@Data
public class EmployeeINP implements Serializable {

    @JsonProperty("id")
    Integer id;

    @NotNull
    @JsonProperty("person")
    Person person;

    @Data
    public static class Person implements Serializable {
        @JsonProperty("id")
        Integer id;
        @NotNull(message = "identification not be null")
        @JsonProperty("idetification")
        String identification;
        @NotNull(message = "name not be null")
        @JsonProperty("name")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name;
        @NotNull(message = "surnames not be null")
        @JsonProperty("surnames")
        @Size(min = 3, max = 100, message = "Surname must be between 3 and 100 characters")
        String surnames;
        @NotNull(message = "name not be null")
        @JsonProperty("email")
        @Size(min = 6, max = 200, message = "Email must be between 3 and 200 characters")
        String email;
        @JsonProperty("informationPerson")
        InformationPerson informationPerson;

        @Data
        public static class InformationPerson implements Serializable{
            @JsonProperty("id")
            Integer id;
            @NotNull(message = "Birthdate not be null")
            @JsonFormat(pattern = Constant.DATE_FORMAT)
            @JsonDeserialize(using = DateDeserializer.class)
            @JsonProperty("birthDate")
            ZonedDateTime birthDate;
            @NotNull(message = "Direction not be null")
            @JsonProperty("direction")
            String direction;
            @NotNull(message = "Mobile phone not be null")
            @JsonProperty("mobilePhone")
            String mobilePhone;
        }
    }
}