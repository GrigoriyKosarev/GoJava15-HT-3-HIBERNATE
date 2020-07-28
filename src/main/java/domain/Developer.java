package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "salary")
    private BigDecimal salary;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="developers_projects",
            joinColumns=@JoinColumn(name="developer_id"),
            inverseJoinColumns=@JoinColumn(name="project_id")
    )
    private List<Project> projects;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="skills_developers",
            joinColumns=@JoinColumn(name="developer_id"),
            inverseJoinColumns=@JoinColumn(name="skill_id")
    )
    private List<Developer> developers;

    public Developer(String name, int age, Sex sex, BigDecimal salary) {
        setName(name);
        setAge(age);
        setSex(sex);
        setSalary(salary);
    }

    public Developer(int id, String name, int age, Sex sex, BigDecimal salary) {
        setId(id);
        setName(name);
        setAge(age);
        setSex(sex);
        setSalary(salary);
    }

    public Developer() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", salary=" + salary +
                '}';
    }
}
