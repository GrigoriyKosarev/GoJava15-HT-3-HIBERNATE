package service;

import repository.CustomerDAO;
import repository.DeveloperDAO;
import repository.ProjectDAO;

public abstract class ServiceFactory {

    public static ProjectService getProjectService() {
        return new ProjectService(new ProjectDAO());
    }

    public static DeveloperService getDeveloperService() {
        return new DeveloperService(new DeveloperDAO());
    }

    public static CustomerService getCustomerService() {
        return new CustomerService(new CustomerDAO());
    }

}
