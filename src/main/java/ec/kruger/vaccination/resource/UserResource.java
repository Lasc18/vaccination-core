package ec.kruger.vaccination.resource;

import ec.kruger.vaccination.model.common.VaccineType;
import ec.kruger.vaccination.service.VaccineTypeService;
import ec.kruger.vaccination.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Santiago Araujo
 * Contanins vaccine type resouces
 */
@RestController
@RequestMapping("/api/")
public class VaccineTypeResource {

    private final Logger log = LoggerFactory.getLogger(VaccineTypeResource.class);

    @Autowired
    VaccineTypeService vaccineTypeService;

    /**
     * {@code GET / : Get the vaccine type list}
     *
     * @return {@link ResponseEntity} with the content of vaccine type data
     */
    @GetMapping(value = Constant.MAPPING_USER + "/vaccine-type/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VaccineType>> getVaccineType(){
        log.debug("Invoke rest service get vaccine type.");
        List<VaccineType> vaccineTypeList = vaccineTypeService.getVaccineType();
        if(vaccineTypeList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(vaccineTypeList);
    }
}
