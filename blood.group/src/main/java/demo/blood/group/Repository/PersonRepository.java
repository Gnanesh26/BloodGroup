package demo.blood.group.Repository;

import demo.blood.group.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByBloodgroupIgnoreCase(String bloodGroup);

    Person findByName(String name);

}