package ec.kruger.vaccination.service.implementation;

import ec.kruger.vaccination.model.common.VaccineType;
import ec.kruger.vaccination.service.VaccineTypeService;
import ec.kruger.vaccination.service.process.VaccineTypeProcess;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Santiago Araujo
 * Service for vaccine type
 */
@Service
public class VaccineTypeServiceImpl extends VaccineTypeProcess implements VaccineTypeService {

    /**
     * Service for get the vaccine type list
     *
     * @return List<VaccineType> vaccine type list
     */
    @Override
    public List<VaccineType> getVaccineType() { return getVaccineTypeList(); }
}
