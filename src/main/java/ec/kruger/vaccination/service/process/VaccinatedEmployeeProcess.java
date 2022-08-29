package ec.kruger.vaccination.service.process;

import ec.kruger.vaccination.error.CustomError;
import ec.kruger.vaccination.model.company.Employee;
import ec.kruger.vaccination.model.person.InformationPerson;
import ec.kruger.vaccination.model.person.Person;
import ec.kruger.vaccination.record.input.PersonEmployeeINP;
import ec.kruger.vaccination.record.output.PersonEmployeeOUT;
import ec.kruger.vaccination.repository.EmployeeRepository;
import ec.kruger.vaccination.repository.InformationPersonRepository;
import ec.kruger.vaccination.repository.PersonRepository;
import ec.kruger.vaccination.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
     * Register the employee data
     *
     * @param personEmployeeINP input employee
     * @return employee entity
     */
    @Transactional
    protected Optional<Employee> register(PersonEmployeeINP personEmployeeINP){
        validateRegisterPersonEmployee(personEmployeeINP);
        Person person = registerPersonEntity(personEmployeeINP);
        Employee employee = registerEmployeeEntity(person);
        InformationPerson informationPerson = null;
        if(personEmployeeINP.getInformationPerson() != null) {
            informationPerson = registerInformationPersonEntity(
                    personEmployeeINP.getInformationPerson(), person.getInformationPerson());
        }
        person = personRepository.save(person);
        employee.setPerson(person);
        employeeRepository.save(employee);
        if(informationPerson != null){
            informationPerson.setPerson(person);
            informationPersonRepository.save(informationPerson);
        }
        return Optional.of(employee);
    }

    /**
     * Get the employee, person and information persons data
     *
     * @param pageable pagination information
     * @return Page<PersonEmployeeOUT> employee, person and information person data
     */
    protected Page<PersonEmployeeOUT> getPersonEmployee(Pageable pageable){
        Page<Person> persons = personRepository.findAll(pageable);
        List<PersonEmployeeOUT> personEmployeeOUTList = new ArrayList<>();
        persons.stream().forEach(person -> {
            PersonEmployeeOUT.Employee employeeOUT = null;
            PersonEmployeeOUT.InformationPerson informationPersonOUT = null;
            if(person.getEmployee() != null){
                employeeOUT = PersonEmployeeOUT.Employee.builder().
                        id(person.getEmployee().getId()).
                        admissionDate(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_COMPLETE).
                                format(person.getEmployee().getAdmissionDate())).build();
            }
            if(person.getInformationPerson() != null){
                informationPersonOUT = (PersonEmployeeOUT.InformationPerson.builder().
                        id(person.getInformationPerson().getId()).
                        birthDate(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT).
                                format(person.getInformationPerson().getBirthDate())).
                        direction(person.getInformationPerson().getDirection())).
                        mobilePhone(person.getInformationPerson().getMobilePhone()).
                        vaccinated(person.getInformationPerson().isVaccinated()).
                        build();
            }
            personEmployeeOUTList.add(PersonEmployeeOUT.builder().
                    id(person.getId()).
                    identification(person.getIdentification()).
                    name(person.getName()).
                    surnames(person.getSurnames()).
                    email(person.getEmail()).
                    employee(employeeOUT).
                    informationPerson(informationPersonOUT).
                    build());
        });
        return new PageImpl<>(personEmployeeOUTList, pageable, personEmployeeOUTList.size());
    }

    /**
     * Delete the employee with person and information person
     * @param id employee id
     */
    @Transactional
    protected void deletePersonEmployee(int id){
        Employee employee = employeeRepository.findById(id).orElseThrow(this::errorEmployeeNotFound);
        personRepository.delete(employee.getPerson());
    }

    /**
     * Get employee, person and information person by id
     * @param id employee id
     * @return personEmployeeOUT with data
     */
    protected PersonEmployeeOUT getPersonEmployeeById(int id){
        Employee employee = employeeRepository.findById(id).orElseThrow(this::errorEmployeeNotFound);
        PersonEmployeeOUT.Employee employeeOUT = PersonEmployeeOUT.Employee.builder().
                id(employee.getId()).
                admissionDate(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_COMPLETE).
                        format(employee.getAdmissionDate())).
                build();
        PersonEmployeeOUT.InformationPerson informationPersonOUT = null;
        if(employee.getPerson().getInformationPerson() != null){
            informationPersonOUT = (PersonEmployeeOUT.InformationPerson.builder().
                    id(employee.getPerson().getInformationPerson().getId()).
                    birthDate(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT).
                            format(employee.getPerson().getInformationPerson().getBirthDate())).
                    direction(employee.getPerson().getInformationPerson().getDirection())).
                    mobilePhone(employee.getPerson().getInformationPerson().getMobilePhone()).
                    vaccinated(employee.getPerson().getInformationPerson().isVaccinated()).
                    build();
        }
        return PersonEmployeeOUT.builder().
                id(employee.getPerson().getId()).
                identification(employee.getPerson().getIdentification()).
                name(employee.getPerson().getName()).
                surnames(employee.getPerson().getSurnames()).
                email(employee.getPerson().getEmail()).
                employee(employeeOUT).
                informationPerson(informationPersonOUT).
                build();
    }

    /**
     * Validate data employee
     * @param personEmployeeINP input data
     */
    private void validateRegisterPersonEmployee(PersonEmployeeINP personEmployeeINP) {
        validatePerson(personEmployeeINP);
    }

    /**
     * Validate data person
     * @param person data person
     */
    private void validatePerson(PersonEmployeeINP person){
        if(person.getIdentification() == null ||
                person.getIdentification().isEmpty() ||
                person.getIdentification().isBlank())
            throw errorMessage("Identification is required");
        if(!person.getIdentification().matches(Constant.REGEX_NUMBER))
            throw errorMessage("Identification not valid");
        if(person.getName() == null || person.getName().isEmpty() || person.getName().isBlank())
            throw errorMessage("Name is required");
        if(!person.getName().matches(Constant.REGEX_ONLY_TEXT))
            throw errorMessage("Name not valid");
        if(person.getSurnames() == null || person.getSurnames().isEmpty() || person.getSurnames().isBlank())
            throw errorMessage("Surnames is required");
        if(!person.getName().matches(Constant.REGEX_ONLY_TEXT))
            throw errorMessage("Surnames not valid");
        if(person.getEmail() == null || person.getEmail().isEmpty() || person.getEmail().isBlank())
            throw errorMessage("Email is required");
        if(!person.getEmail().matches(Constant.REGEX_EMAIL))
            throw errorMessage("Email not valid");
        if(person.getInformationPerson() != null)
            validateInformationPerson(person.getInformationPerson());
        validatePersonRepository(person);
    }

    /**
     * Validate against repository
     * @param person data person
     */
    private void validatePersonRepository(PersonEmployeeINP person){
        if(person.getId() != null && person.getId() != 0){
            Person personUpdate = personRepository.findById(person.getId()).orElseThrow(this::errorPersonNotFound);
            Optional<Person> personValidate = personRepository.findByEmail(person.getEmail());
            if(personValidate.isPresent()
                    && person.getEmail().strip().equals(personValidate.get().getEmail().strip())
                    && personUpdate.getId() != personValidate.get().getId())
                throw errorMessage("Email already registered");
            personValidate = personRepository.findByIdentification(person.getIdentification());
            if(personValidate.isPresent()
                    && person.getIdentification().strip().equals(personValidate.get().getIdentification().strip())
                    && personUpdate.getId() != personValidate.get().getId())
                throw errorMessage("Identification already registered");
        }else{
            Optional<Person> personValidate = personRepository.findByEmail(person.getEmail());
            if(personValidate.isPresent()
                    && person.getEmail().strip().equals(personValidate.get().getEmail().strip()))
                throw errorMessage("Email already registered");
            personValidate = personRepository.findByIdentification(person.getIdentification());
            if(personValidate.isPresent()
                    && person.getIdentification().strip().equals(personValidate.get().getIdentification().strip()))
                throw errorMessage("Identification already registered");
        }
    }

    /**
     * Validate data information person
     * @param informationPerson data information person
     */
    private void validateInformationPerson(PersonEmployeeINP.InformationPerson informationPerson){
        if(informationPerson.getDirection() == null ||
                informationPerson.getDirection().isEmpty() ||
                informationPerson.getDirection().isBlank())
            throw errorMessage("Direction is required");
        if(informationPerson.getMobilePhone() == null ||
                informationPerson.getMobilePhone().isEmpty() ||
                informationPerson.getMobilePhone().isBlank())
            throw errorMessage("Mobile Phone is required");
        if(informationPerson.getBirthDate() == null)
            throw errorMessage("Birth Date is required");
        if(informationPerson.getBirthDate().isAfter(ZonedDateTime.now()))
            throw errorMessage("Birth Date cannot be greater then today");
    }

    /**
     * Register the employee entity
     * @param person person entity
     * @return employee entity
     */
    private Employee registerEmployeeEntity(Person person){
        if(person.getEmployee() == null)
            return createEmployee();
        return person.getEmployee();
    }

    /**
     * Create employee entity
     * @return employee entity
     */
    private Employee createEmployee(){
        return Employee.builder().admissionDate(ZonedDateTime.now()).build();
    }

    /**
     * Register (create or update) person entity
     * @param personINP person data
     * @return person entity
     */
    private Person registerPersonEntity(PersonEmployeeINP personINP){
        if(personINP.getId() == null || personINP.getId() == 0)
            return createPerson(personINP);
        return updatePerson(personINP);
    }

    /**
     * Update person entity
     * @param personINP  person data
     * @return person entity
     */
    private Person updatePerson(PersonEmployeeINP personINP){
        Person person = personRepository.findById(personINP.getId()).orElseThrow(this::errorPersonNotFound);
        person.setIdentification(personINP.getIdentification().strip());
        person.setName(personINP.getName().toUpperCase().strip());
        person.setSurnames(personINP.getSurnames().toUpperCase().strip());
        person.setEmail(personINP.getEmail().strip());
        return person;
    }

    /**
     * Create person entity
     * @param personINP person data
     * @return person entity
     */
    private Person createPerson(PersonEmployeeINP personINP){
        return Person.builder().
                identification(personINP.getIdentification().strip()).
                name(personINP.getName().toUpperCase().strip()).
                surnames(personINP.getSurnames().toUpperCase().strip()).
                email(personINP.getEmail().strip()).build();
    }

    /**
     * Register (create or update) information person entity
     * @param informationPersonINP information person data
     * @return person entity
     */
    private InformationPerson registerInformationPersonEntity(PersonEmployeeINP.InformationPerson informationPersonINP,
                                                                InformationPerson informationPerson){
        if(informationPerson == null)
            return createInformationPerson(informationPersonINP);
        return updateInformationPerson(informationPersonINP, informationPerson);
    }

    /**
     * Update information person entity
     * @param informationPersonINP  information person data
     * @return information person entity
     */
    private InformationPerson updateInformationPerson(PersonEmployeeINP.InformationPerson informationPersonINP,
                                                        InformationPerson informationPerson){
        informationPerson.setDirection(informationPersonINP.getDirection().strip());
        informationPerson.setBirthDate(informationPersonINP.getBirthDate());
        informationPerson.setMobilePhone(informationPersonINP.getMobilePhone().strip());
        return informationPerson;
    }

    /**
     * Create information person entity
     * @param informationPersonINP information person data
     * @return information person entity
     */
    private InformationPerson createInformationPerson(PersonEmployeeINP.InformationPerson informationPersonINP){
        return InformationPerson.builder().
                mobilePhone(informationPersonINP.getMobilePhone().strip()).
                direction(informationPersonINP.getDirection().strip()).
                birthDate(informationPersonINP.getBirthDate()).
                vaccinated(Boolean.FALSE).build();
    }
    /**
     * Error not found of person entity
     * @return custom error
     */
    private CustomError errorPersonNotFound(){
        return new CustomError("Person not found", CLASS_NAME);
    }

    /**
     * Error not found of person entity
     * @return custom error
     */
    private CustomError errorEmployeeNotFound(){
        return new CustomError("Employee not found", CLASS_NAME);
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
