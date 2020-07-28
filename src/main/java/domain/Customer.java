package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "full_Name")
    private String fullName;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="customers_projects",
            joinColumns=@JoinColumn(name="customer_id"),
            inverseJoinColumns=@JoinColumn(name="project_id")
    )
    private List<Project> projects;

    public Customer(int id, String name, String fullName) {
        setId(id);
        setName(name);
        setFullName(fullName);
    }

    public Customer(String name, String fullName) {
        setName(name);
        setFullName(fullName);
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", full_name='" + fullName + '\'' +
                '}';
    }
}
