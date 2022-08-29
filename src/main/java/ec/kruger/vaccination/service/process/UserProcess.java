package ec.kruger.vaccination.service.process;

import ec.kruger.vaccination.model.common.VaccineType;
import ec.kruger.vaccination.repository.common.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Santiago Araujo
 * Class for process of vaccine type
 */
@Service
public class VaccineTypeProcess {

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    /**
     * Get vaccine type list
     *
     * @return vaccinetype list
     */
    protected List<VaccineType> getVaccineTypeList(){
        return vaccineTypeRepository.findAll();
    }
}
