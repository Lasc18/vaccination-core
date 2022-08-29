package ec.kruger.vacunacion.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import ec.kruger.vacunacion.util.Constant;

/**
 * Convert param request format DATE_FORMAT
 */
public class DateDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return LocalDate.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(Constant.DATE_FORMAT)).
                atStartOfDay(ZoneOffset.UTC);
    }
}
