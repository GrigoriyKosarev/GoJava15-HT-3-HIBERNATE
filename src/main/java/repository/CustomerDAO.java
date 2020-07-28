package repository;

import domain.Customer;
import domain.ID;
import error.InternalException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j2
public class CustomerDAO extends GenericDAO<Customer, Integer> {

    public Optional<Customer> getCustomer(int id) throws InternalException {
        return get(id, Customer.class);
    }

    public Optional<Customer> getCustomer(String name) throws InternalException {
        return get(name, Customer.class);
    }

    public List<Customer> getAllCustomer() throws InternalException {
        return getAll(Customer.class);
    }

    public void addCustomer(Customer customer) throws InternalException {
        add(customer);
    }

    public void deleteCustomer(int id) throws InternalException {
        delete(id, Customer.class);
    }

    public void editCustomer(Customer customer) {
        edit(customer, customer.getId(), Customer.class);
    }


}
