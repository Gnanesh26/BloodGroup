package demo.blood.group.Service;

import demo.blood.group.Entity.Person;
import demo.blood.group.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;
    public Person createPerson(Person person) {
        return personRepository.save(person);
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

    private boolean canDonateBlood(String donorBloodGroup, String recipientBloodGroup) {

        if (recipientBloodGroup.equals("O+")) {
            return donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-");
        } else if (recipientBloodGroup.equals("O-")) {
            return donorBloodGroup.equals("O-");
        } else if (recipientBloodGroup.equals("A+")) {
            return donorBloodGroup.equals("A+") || donorBloodGroup.equals("A-") || donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-");
        } else if (recipientBloodGroup.equals("A-")) {
            return donorBloodGroup.equals("A-") || donorBloodGroup.equals("O-");
        } else if (recipientBloodGroup.equals("B+")) {
            return donorBloodGroup.equals("B+") || donorBloodGroup.equals("B-") || donorBloodGroup.equals("O+") || donorBloodGroup.equals("O-");
        } else if (recipientBloodGroup.equals("B-")) {
            return donorBloodGroup.equals("B-") || donorBloodGroup.equals("O-");
        } else if (recipientBloodGroup.equals("AB+")) {
            return true;  // AB+ is compatible with all blood groups
        } else if (recipientBloodGroup.equals("AB-")) {
            return donorBloodGroup.equals("AB-") || donorBloodGroup.equals("A-") || donorBloodGroup.equals("B-") || donorBloodGroup.equals("O-");
        } else {
            return false; // Invalid recipient blood group
        }
    }
}
