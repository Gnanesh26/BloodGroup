package demo.blood.group.Service;

import demo.blood.group.Entity.Person;
import demo.blood.group.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonService {


    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }
    public List<Person> getPersonsByBloodGroup(String bloodGroup) {
        return personRepository.findByBloodgroupIgnoreCase(bloodGroup);
    }


    public String getAgeByName(String name) {
        Person person = personRepository.findByName(name);

        if (person == null) {
            return null;
        }

        Date dob = person.getDob();
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Period period = Period.between(birthDate, currentDate);

        return String.format("%d years %d months %d days",
                period.getYears(), period.getMonths(), period.getDays());
    }


    public List<Person> getDonorsByBloodGroup(String bloodGroup) {
        List<Person> allPersons = personRepository.findAll();
        List<Person> donors = new ArrayList<>();

        for (Person person : allPersons) {
            if (canDonateBlood(person.getBloodgroup(), bloodGroup)) {
                donors.add(person);
            }
        }

        return donors;
    }


    // TERNARY OPERATOR
    private boolean canDonateBlood(String donorBloodGroup, String recipientBloodGroup) {
        return recipientBloodGroup.equals("O+") ? (donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-"))
                : recipientBloodGroup.equals("O-") ? donorBloodGroup.equals("O-")
                : recipientBloodGroup.equals("A+") ? (donorBloodGroup.equals("A+") || donorBloodGroup.equals("A-") || donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-"))
                : recipientBloodGroup.equals("A-") ? (donorBloodGroup.equals("A-") || donorBloodGroup.equals("O-"))
                : recipientBloodGroup.equals("B+") ? (donorBloodGroup.equals("B+") || donorBloodGroup.equals("B-") || donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-"))
                : recipientBloodGroup.equals("B-") ? (donorBloodGroup.equals("B-") || donorBloodGroup.equals("O-"))
                : recipientBloodGroup.equals("AB+") ? true
                : recipientBloodGroup.equals("AB-") ? (donorBloodGroup.equals("AB-") || donorBloodGroup.equals("A-") || donorBloodGroup.equals("B-") || donorBloodGroup.equals("O-"))
                : false;
    }
//
//    private boolean canDonateBlood(String donorBloodGroup, String recipientBloodGroup) {
//
//        if (recipientBloodGroup.equals("O+")) {
//            return donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-");
//        } else if (recipientBloodGroup.equals("O-")) {
//            return donorBloodGroup.equals("O-");
//        } else if (recipientBloodGroup.equals("A+")) {
//            return donorBloodGroup.equals("A+") || donorBloodGroup.equals("A-") || donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-");
//        } else if (recipientBloodGroup.equals("A-")) {
//            return donorBloodGroup.equals("A-") || donorBloodGroup.equals("O-");
//        } else if (recipientBloodGroup.equals("B+")) {
//            return donorBloodGroup.equals("B+") || donorBloodGroup.equals("B-") || donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-");
//        } else if (recipientBloodGroup.equals("B-")) {
//            return donorBloodGroup.equals("B-") || donorBloodGroup.equals("O-");
//        } else if (recipientBloodGroup.equals("AB+")) {
//            return true;  // AB+ is compatible with all blood groups
//        } else if (recipientBloodGroup.equals("AB-")) {
//            return donorBloodGroup.equals("AB-") || donorBloodGroup.equals("A-") || donorBloodGroup.equals("B-") || donorBloodGroup.equals("O-");
//        } else {
//            return false; // Invalid recipient blood group
//        }
    }
