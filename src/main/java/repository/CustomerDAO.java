package repository;

import domain.Customer;
import error.InternalException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j2
public class CustomerDAO extends GenericDAO {

    public Optional<Customer> getCustomer(int id) {
        EntityManager entityManager = getEntityManager();
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.close();
        if (customer == null)
            return Optional.empty();
        else
            return Optional.of(customer);
    }

    public Optional<Customer> getCustomer(String name) {
        EntityManager entityManager = getEntityManager();
        Customer customer = (Customer) entityManager.createQuery("select customer from Customer customer where customer.name = :name")
                .setParameter("name", name)
                .getSingleResult();
        entityManager.close();
        if (customer == null)
            return Optional.empty();
        else
            return Optional.of(customer);
    }

    public List<Customer> getAllCustomer() {
        EntityManager entityManager = getEntityManager();
        List<Customer> customers = entityManager.createQuery("select customer from Customer customer")
                .getResultList();
        entityManager.close();
        return customers;
    }

    public void addCustomer(Customer customer) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteCustomer(int id) throws InternalException {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Customer customerFromDB = entityManager.find(Customer.class, id);
        if (customerFromDB != null) {
            entityManager.remove(customerFromDB);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            entityManager.close();
            log.error("Delete operation. Customer not found by id: " + id);
            throw new InternalException("Delete operation. Customer not found by id: " + id);
        }
    }

    public void editCustomer(Customer customer) throws InternalException {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Customer customerFromDB = entityManager.find(Customer.class, customer.getId());
        if (customerFromDB != null) {
            entityManager.merge(customer);
            entityManager.persist(customerFromDB);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            entityManager.close();
            log.error("Edit operation. Project not found by id: " + customer.getId());
            throw new InternalException("Edit operation. Project not found by id: " + customer.getId());
        }
    }


}
