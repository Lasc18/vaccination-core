package ec.kruger.vaccination.service;

import ec.kruger.vaccination.model.common.VaccineType;

import java.util.List;

/**
 * @author Santiago Araujo
 * Service of vaccine type
 */
public interface VaccineTypeService {

    /**
     * Get the vaccine type list
     *
     * @return vaccine type list
     */
    List<VaccineType> getVaccineType();
}
