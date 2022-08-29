package ec.kruger.vacunacion.service.process;

import ec.kruger.vacunacion.error.CustomError;
import ec.kruger.vacunacion.model.company.Employee;
import ec.kruger.vacunacion.model.person.InformationPerson;
import ec.kruger.vacunacion.model.person.Person;
import ec.kruger.vacunacion.record.input.EmployeeINP;
import ec.kruger.vacunacion.repository.EmployeeRepository;
import ec.kruger.vacunacion.repository.InformationPersonRepository;
import ec.kruger.vacunacion.repository.PersonRepository;
import ec.kruger.vacunacion.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author Santiago Araujo
 * Class for process of employee
 */
@Service
public class EmployeeProcess {

    private static final String CLASS_NAME = EmployeeProcess.class.toString();

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    InformationPersonRepository informationPersonRepository;

    /**
     * Validate data employee
     * @param employeeINP input data
     */
    private void validateRegisterEmployee(EmployeeINP employeeINP) {
        if(employeeINP.getPerson() != null)
            validatePerson(employeeINP.getPerson());
    }

    /**
     * Validate data person
     * @param person data person
     */
    private void validatePerson(EmployeeINP.Person person){
        if(person.getIdentification() == null ||
                person.getIdentification().isEmpty() ||
                person.getIdentification().isBlank())
            throw new CustomError("Identification is required", CLASS_NAME);
        if(!person.getIdentification().matches(Constant.REGEX_NUMBER))
            throw new CustomError("Identification not valid", CLASS_NAME);
        if(person.getName() == null || person.getName().isEmpty() || person.getName().isBlank())
            throw new CustomError("Name is required", CLASS_NAME);
        if(!person.getName().matches(Constant.REGEX_ONLY_TEXT))
            throw new CustomError("Name not valid", CLASS_NAME);
        if(person.getSurnames() == null || person.getSurnames().isEmpty() || person.getSurnames().isBlank())
            throw new CustomError("Surnames is required", CLASS_NAME);
        if(!person.getName().matches(Constant.REGEX_ONLY_TEXT))
            throw new CustomError("Surnames not valid", CLASS_NAME);
        if(person.getEmail() == null || person.getEmail().isEmpty() || person.getEmail().isBlank())
            throw new CustomError("Email is required", CLASS_NAME);
        if(!person.getName().matches(Constant.REGEX_EMAIL))
            throw new CustomError("Email not valid", CLASS_NAME);
        if(person.getInformationPerson() != null)
            validateInformationPerson(person.getInformationPerson());
    }

    /**
     * Register the employee data
     * @param employeeINP input employee
     * @return employee entity
     */
    protected Optional<Employee> register(EmployeeINP employeeINP){
        validateRegisterEmployee(employeeINP);
        Employee employee = registerEmployeeEntity(employeeINP);
        if(employeeINP.getPerson() != null) {
            Person person = registerPersonEntity(employeeINP.getPerson());
            if (employeeINP.getPerson().getInformationPerson() != null)
                person.setInformationPerson(registerInformationPersonEntity(
                        employeeINP.getPerson().getInformationPerson()));
            employee.setPerson(person);
        }
        return Optional.of(employee);
    }

    /**
     * Validate data information person
     * @param informationPerson data information person
     */
    private void validateInformationPerson(EmployeeINP.Person.InformationPerson informationPerson){
        if(informationPerson.getDirection() == null ||
                informationPerson.getDirection().isEmpty() ||
                informationPerson.getDirection().isBlank())
            throw new CustomError("Direction is required", CLASS_NAME);
        if(informationPerson.getMobilePhone() == null ||
                informationPerson.getMobilePhone().isEmpty() ||
                informationPerson.getMobilePhone().isBlank())
            throw new CustomError("Mobile Phone is required", CLASS_NAME);
        if(informationPerson.getMobilePhone().matches(Constant.REGEX_NUMBER))
            throw new CustomError("Mobile Phone not valid", CLASS_NAME);
        if(informationPerson.getBirthDate() == null)
            throw new CustomError("Birth Date is required", CLASS_NAME);
        if(informationPerson.getBirthDate().isAfter(ZonedDateTime.now()))
            throw new CustomError("Birth Date cannot be greater then today", CLASS_NAME);
    }

    /**
     * Register the employee entity
     * @param employeeINP data employee
     * @return employee entity
     */
    protected Employee registerEmployeeEntity(EmployeeINP employeeINP){
        if(employeeINP.getId() == null)
            return createEmployee();
        else
            return updateEmployee(employeeINP);
    }

    /**
     * Update employee entity
     * @param employeeINP  employee data
     * @return employee entity
     */
    protected Employee updateEmployee(EmployeeINP employeeINP){
        return employeeRepository.findById(employeeINP.getId()).orElseThrow(this::errorEmployeeNotFound);
    }

    /**
     * Create employee entity
     * @return employee entity
     */
    protected Employee createEmployee(){
        Employee employee = new Employee();
        employee.setAdmission_date(ZonedDateTime.now());
        return employee;
    }

    /**
     * Register (create or update) person entity
     * @param personINP person data
     * @return person entity
     */
    protected Person registerPersonEntity(EmployeeINP.Person personINP){
        if(personINP.getId() == null)
            return createPerson(personINP);
        else
            return updatePerson(personINP);
    }

    /**
     * Update person entity
     * @param personINP  person data
     * @return person entity
     */
    protected Person updatePerson(EmployeeINP.Person personINP){
        Person person = personRepository.findById(personINP.getId()).orElseThrow(this::errorPersonNotFound);
        person.setIdentification(personINP.getIdentification());
        person.setName(personINP.getName());
        person.setSurnames(person.getSurnames());
        person.setEmail(person.getEmail());
        return person;
    }

    /**
     * Create person entity
     * @param personINP person data
     * @return person entity
     */
    protected Person createPerson(EmployeeINP.Person personINP){
        Person person = new Person();
        person.setIdentification(personINP.getIdentification());
        person.setName(personINP.getName());
        person.setSurnames(person.getSurnames());
        person.setEmail(person.getEmail());
        return person;
    }

    /**
     * Register (create or update) information person entity
     * @param informationPersonINP information person data
     * @return person entity
     */
    protected InformationPerson registerInformationPersonEntity(EmployeeINP.Person.InformationPerson informationPersonINP){
        if(informationPersonINP.getId() == null)
            return createInformationPerson(informationPersonINP);
        else
            return updateInformationPerson(informationPersonINP);
    }

    /**
     * Update information person entity
     * @param informationPersonINP  information person data
     * @return information person entity
     */
    protected InformationPerson updateInformationPerson(EmployeeINP.Person.InformationPerson informationPersonINP){
        InformationPerson informationPerson = informationPersonRepository.findById(informationPersonINP.getId()).
                orElseThrow(this::errorInformationPersonNotFound);
        informationPerson.setDirection(informationPersonINP.getDirection());
        informationPerson.setBirthDate(informationPersonINP.getBirthDate());
        informationPerson.setMobilePhone(informationPersonINP.getMobilePhone());
        return informationPerson;
    }

    /**
     * Create information person entity
     * @param informationPersonINP information person data
     * @return information person entity
     */
    protected InformationPerson createInformationPerson(EmployeeINP.Person.InformationPerson informationPersonINP){
        InformationPerson informationPerson = new InformationPerson();
        informationPerson.setMobilePhone(informationPersonINP.getMobilePhone());
        informationPerson.setDirection(informationPersonINP.getDirection());
        informationPerson.setBirthDate(informationPersonINP.getBirthDate());
        informationPerson.setVaccinated(Boolean.FALSE);
        return informationPerson;
    }

    /**
     * Error not found of employee entity
     * @return custom error
     */
    protected CustomError errorEmployeeNotFound(){
        return new CustomError("Employee not found", CLASS_NAME);
    }
    /**
     * Error not found of person entity
     * @return custom error
     */
    protected CustomError errorPersonNotFound(){
        return new CustomError("Person not found", CLASS_NAME);
    }
    /**
     * Error not found of information person entity
     * @return custom error
     */
    protected CustomError errorInformationPersonNotFound(){
        return new CustomError("Information Person not found", CLASS_NAME);
    }
}
