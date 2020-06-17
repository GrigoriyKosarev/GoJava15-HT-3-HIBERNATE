package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "createDate")
    private LocalDate createDate;

    public Project(String name, String comment, BigDecimal cost, LocalDate createDate) {
        setName(name);
        setComment(comment);
        setCost(cost);
        setCreateDate(createDate);
    }

    public Project(int id, String name, String comment, BigDecimal cost, LocalDate createDate) {
        setId(id);
        setName(name);
        setComment(comment);
        setCost(cost);
        setCreateDate(createDate);
    }

    public Project(int id, String name, String comment, BigDecimal cost) {
        setId(id);
        setName(name);
        setComment(comment);
        setCost(cost);
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public LocalDate getCreateDate() {
        return  this.createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", cost=" + cost +
                ", createDate=" + createDate +
                '}';
    }
}
