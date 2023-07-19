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

@RestController
public class PersonController {


@Autowired
    PersonRepository personRepository;
@Autowired
    PersonService personService;


    @PostMapping("/add")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }



    @GetMapping("/bloodgroup")
    public ResponseEntity<List<Person>> getDonorsByBloodGroup(@RequestParam("bloodGroup") String bloodGroup) {
        List<Person> donors = personService.getDonorsByBloodGroup(bloodGroup);

        if (donors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(donors);
    }

    @GetMapping("/age")
    public ResponseEntity<String> getAgeByName(@RequestParam("name") String name) {
        Person person = personRepository.findByName(name);

        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        Date dob = person.getDob();
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        Period period = Period.between(birthDate, currentDate);

        String age = String.format("%d years %d months %d days",
                period.getYears(), period.getMonths(), period.getDays());
        return ResponseEntity.ok(age);
    }


}