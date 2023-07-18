package demo.blood.group.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column
    private String gender;

    @Column
    private int contactno;

    @Column(name = "Dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @Column(name = "BloodGroup")
    private String bloodgroup;

    @Column(name = "Email")
    @NotBlank(message = "Email is required")
    private String emailid;

    public Person(int id, String name, String gender, int contactno, Date dob, String bloodgroup, String emailid) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.contactno = contactno;
        this.dob = dob;
        this.bloodgroup = bloodgroup;
        this.emailid = emailid;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getContactno() {
        return contactno;
    }

    public void setContactno(int contactno) {
        this.contactno = contactno;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && contactno == person.contactno && Objects.equals(name, person.name) && Objects.equals(gender, person.gender) && Objects.equals(dob, person.dob) && Objects.equals(bloodgroup, person.bloodgroup) && Objects.equals(emailid, person.emailid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, contactno, dob, bloodgroup, emailid);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", contactno=" + contactno +
                ", dob=" + dob +
                ", bloodgroup='" + bloodgroup + '\'' +
                ", emailid='" + emailid + '\'' +
                '}';
    }
}
