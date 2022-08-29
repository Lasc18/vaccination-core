package ec.kruger.vacunacion.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Santiago Araujo
 * Create the custom error
 */
public class CustomError extends RuntimeException {

    /**
     * Contructor
     * @param error error message
     */
    public CustomError(String error, String className){
        super(error);
        Logger log = LoggerFactory.getLogger(className);
        log.error(error);
    }
}
