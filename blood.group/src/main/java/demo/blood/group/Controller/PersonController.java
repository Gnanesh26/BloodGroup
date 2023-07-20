package demo.blood.group.Controller;

import demo.blood.group.Entity.Person;
import demo.blood.group.Repository.PersonRepository;
import demo.blood.group.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class PersonController {


    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonService personService;


    @PostMapping("/add")
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        if (!isValidEmail(person.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email address");
        }

        if (!isValidBloodGroup(person.getBloodgroup())) {
            return ResponseEntity.badRequest().body("Invalid blood group");
        }
        Person createdPerson = personService.createPerson(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidBloodGroup(String bloodGroup) {
        return bloodGroup != null && bloodGroup.matches("[ABO]+");
    }


    @GetMapping("/bloodgroup")
    public ResponseEntity<?> getDonorsByBloodGroup(@RequestParam("bloodGroup") String bloodGroup) {
        List<Person> donors = personService.getDonorsByBloodGroup(bloodGroup);
        if (donors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Blood Group !!");
        }
        return ResponseEntity.ok(donors);
    }



        @GetMapping("/age")
        public ResponseEntity<String> getAgeByName (@RequestParam("name") String name){
            Person person = personRepository.findByName(name);
            if (name == null || name.matches("\\d+")) {
                String errorMessage = "Invalid name provided.";
                return badRequest().body(errorMessage);
            }

            if (person == null) {
                String errorMessage = "Person with name '" + name + "' not found.";
                return status(HttpStatus.NOT_FOUND).body(errorMessage);
            }


            Date dob = person.getDob();
            LocalDate currentDate = LocalDate.now();
            LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            Period period = Period.between(birthDate, currentDate);

            String age = String.format("%d years %d months %d days",
                    period.getYears(), period.getMonths(), period.getDays());
            return ok(age);
        }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity deletePerson(@PathVariable int id) {
        if (!personRepository.existsById(id)) {
            String errorMessage = "Person with ID " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }

        personRepository.deleteById(id);
        String message = "Row Deleted";
        return ResponseEntity.ok(message);
    }

    }
