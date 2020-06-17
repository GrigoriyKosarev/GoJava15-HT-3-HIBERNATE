package service;

import repository.CustomerDAO;
import repository.DeveloperDAO;
import repository.ProjectDAO;

public class ServiceFactory {

    public ProjectService getProjectService() {
        return new ProjectService(new ProjectDAO());
    }

    public DeveloperService getDeveloperService() {
        return new DeveloperService(new DeveloperDAO());
    }

    public CustomerService getCustomerService() {
        return new CustomerService(new CustomerDAO());
    }

}
