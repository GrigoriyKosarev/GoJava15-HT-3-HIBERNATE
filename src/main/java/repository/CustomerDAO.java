package repository;

import domain.Customer;
import domain.ID;
import error.InternalException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j2
public class CustomerDAO {

    public Optional<Customer> getCustomer(int id) {
        return new GenericDAO<Customer, ID>().get(id, Customer.class);
    }

    public Optional<Customer> getCustomer(String name) {
        return new GenericDAO<Customer, ID>().get(name, "select customer from Customer customer where customer.name = :name");
    }

    public List<Customer> getAllCustomer() {
        return new GenericDAO<Customer, ID>().getAll("select customer from Customer customer");
    }

    public void addCustomer(Customer customer) {
        new GenericDAO<Customer, ID>().add(customer);
    }

    public void deleteCustomer(int id) {
        new GenericDAO<Customer, ID>().delete(Customer.class, id);
    }

    public void editCustomer(Customer customer) {
        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer customerFromDB = entityManager.find(Customer.class, customer.getId());
            if (customerFromDB != null) {
                entityManager.merge(customer);
                entityManager.persist(customerFromDB);
                entityManager.getTransaction().commit();
            } else {
                log.error("Edit operation. Project not found by id: " + customer.getId());
                throw new InternalException("Edit operation. Project not found by id: " + customer.getId());
            }
        } catch (Exception e) {
            log.error("editCustomer operation Exception.");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }


}
