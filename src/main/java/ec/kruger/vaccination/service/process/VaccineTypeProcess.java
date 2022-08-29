package ec.kruger.vaccination.service.process;

import ec.kruger.vaccination.error.CustomError;
import ec.kruger.vaccination.model.common.VaccineType;
import ec.kruger.vaccination.model.company.Employee;
import ec.kruger.vaccination.model.company.VaccinatedEmployee;
import ec.kruger.vaccination.model.person.InformationPerson;
import ec.kruger.vaccination.model.person.Person;
import ec.kruger.vaccination.record.input.VaccinatedEmployeeINP;
import ec.kruger.vaccination.record.output.PersonEmployeeOUT;
import ec.kruger.vaccination.repository.EmployeeRepository;
import ec.kruger.vaccination.repository.InformationPersonRepository;
import ec.kruger.vaccination.repository.VaccinatedEmployeeRepository;
import ec.kruger.vaccination.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * @author Santiago Araujo
 * Class for process of vaccinated employee
 */
@Service
public class VaccinatedEmployeeProcess {

    private static final String CLASS_NAME = VaccinatedEmployeeProcess.class.toString();

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    VaccinatedEmployeeRepository vaccinatedEmployeeRepository;
    @Autowired
    VaccineTypeRepository vaccineTypeRepository;
    @Autowired
    InformationPersonRepository informationPersonRepository;

    /**
     * Register the employee data
     *
     * @param vaccinatedEmployeeINP input vaccinated employee
     * @return vaccinated employee entity
     */
    @Transactional
    protected Optional<VaccinatedEmployee> register(VaccinatedEmployeeINP vaccinatedEmployeeINP){
        Employee employee = employeeRepository.findById(vaccinatedEmployeeINP.getIdEmployee()).
                orElseThrow(this::errorEmployeeNotFound);
        VaccineType vaccineType = vaccineTypeRepository.findById(vaccinatedEmployeeINP.getIdVaccineType()).
                orElseThrow(this::errorVaccineTypeNotFound);
        if(vaccinatedEmployeeINP.getVaccinatedDate().isAfter(ZonedDateTime.now()))
            throw errorMessage("Vaccinated date cannot be greater then today");
        Optional<VaccinatedEmployee> vaccinatedEmployee = vaccinatedEmployeeRepository.
                findFirstByEmployeeOrderByIdDesc(employee);
        if(vaccinatedEmployee.isPresent() && vaccinatedEmployee.get().getVaccineDate().isAfter(vaccinatedEmployeeINP.getVaccinatedDate()))
            throw errorMessage("Vaccinated date cannot be less than the last recorded vaccination data");
        Integer doseNumber = vaccinatedEmployee.isPresent() ? vaccinatedEmployee.get().getDoseNumber() + 1 : 1;
        VaccinatedEmployee vaccinatedEmployeeSaved = createVaccinatedEmployee(vaccinatedEmployeeINP, employee,
                vaccineType, Short.parseShort(doseNumber.toString()));
        InformationPerson informationPerson = employee.getPerson().getInformationPerson();
        if(!vaccinatedEmployee.isPresent() || !informationPerson.isVaccinated()) {
            informationPerson.setVaccinated(true);
            informationPersonRepository.save(informationPerson);
        }
        return Optional.of(vaccinatedEmployeeSaved);
    }

    /**
     * Create the vaccinated employee entity
     * @param vaccinatedEmployeeINP input data
     * @param employee employee entity
     * @param vaccineType vaccine type entity
     * @param doseNumber dose number of employee
     * @return vaccinatedEmployee
     */
    private VaccinatedEmployee createVaccinatedEmployee(VaccinatedEmployeeINP vaccinatedEmployeeINP, Employee employee,
                                                        VaccineType vaccineType, short doseNumber){
        return vaccinatedEmployeeRepository.save(VaccinatedEmployee.builder().
                employee(employee).
                vaccineDate(vaccinatedEmployeeINP.getVaccinatedDate()).
                vaccineType(vaccineType).
                doseNumber(doseNumber).
                build());
    }

    /**
     * Error not found of employee entity
     * @return custom error
     */
    private CustomError errorEmployeeNotFound(){
        return new CustomError("Employee not found", CLASS_NAME);
    }
    /**
     * Error not found of vaccine type entity
     * @return custom error
     */
    private CustomError errorVaccineTypeNotFound(){
        return new CustomError("Vaccine type not found", CLASS_NAME);
    }

    /**
     * Custom error for validation in the service
     * @param message error message
     * @return custom error
     */
    private CustomError errorMessage(String message){
        return new CustomError(message, CLASS_NAME);
    }

}
