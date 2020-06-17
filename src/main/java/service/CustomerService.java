package service;

import domain.Customer;
import error.InternalException;
import repository.CustomerDAO;

import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer getCustomer(int id) {
        return customerDAO.getCustomer(id).orElseThrow(() -> {throw new RuntimeException("Customer not found by id " + id);});
    }

    public Customer getCustomer(String name) {
        return customerDAO.getCustomer(name).orElseThrow(() -> {throw new RuntimeException("Customer not found by name " + name);});
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomer();
    }

    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    public void deleteCustomer(int id) throws InternalException {
        customerDAO.deleteCustomer(id);
    }

    public void editCustomer(Customer customer) throws InternalException {
        customerDAO.editCustomer(customer);
    }

}
