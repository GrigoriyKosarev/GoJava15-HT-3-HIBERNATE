import domain.Customer;
import domain.Developer;
import domain.Project;
import domain.Sex;
import error.InternalException;
import lombok.extern.log4j.Log4j2;
import service.ServiceFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

@Log4j2
public class Main {
    private static ServiceFactory serviceFactory = new ServiceFactory();

    public static void main(String[] args) throws InternalException {

        //CUSTOMER TEST

        serviceFactory.getCustomerService().addCustomer(new Customer("Apple", "Apple"));
        log.info("find customers by name: " + serviceFactory.getCustomerService().getCustomer("BMW"));
        serviceFactory.getCustomerService().addCustomer(new Customer("IBM", "IBM"));
        //serviceFactory.getCustomerService().deleteCustomer(1);
        Customer newCustomer = new Customer(4, "Apple+", "Apple+");
        serviceFactory.getCustomerService().editCustomer(newCustomer);
        log.info("all customers: " + serviceFactory.getCustomerService().getAllCustomers());

        //PROJECT TEST
        serviceFactory.getProjectService().addProject(new Project("Apple to do", "to do", new BigDecimal(1000), LocalDate.of(2020, 01, 01)));
        log.info("find projects by name: " + serviceFactory.getProjectService().getProject("Apple to do"));
        serviceFactory.getProjectService().addProject(new Project("IBM to do", "to do", new BigDecimal(2000), LocalDate.of(2020, 01, 01)));
        //serviceFactory.getProjectService().deleteProject(2);
        Project newProject = new Project(2, "Apple to do +", "to do +", new BigDecimal(5000), LocalDate.of(2020, 01, 01));
        serviceFactory.getProjectService().editProject(newProject);
        log.info("all projects: " + serviceFactory.getProjectService().getAllProjects());

        //DEVELOPER TEST
        serviceFactory.getDeveloperService().addDeveloper(new Developer("Petro", 40, Sex.MEN, new BigDecimal(4000)));
        log.info("find projects by name: " + serviceFactory.getDeveloperService().getDeveloper("Petro"));
        serviceFactory.getDeveloperService().addDeveloper(new Developer("Vas-Vas", 20, Sex.MEN, new BigDecimal(800)));
        //serviceFactory.getDeveloperService().deleteDeveloper(serviceFactory.getDeveloperService().getDeveloper("Vas-Vas").getId());
        Developer newDeveloper = new Developer(4, "Petro+", 42, Sex.MEN, new BigDecimal(4000));
        serviceFactory.getDeveloperService().editDeveloper(newDeveloper);
        log.info("all projects: " + serviceFactory.getDeveloperService().getAllDevelopers());

        log.info("Зарплата всех разработчиков отдельного проекта ('boots'): " +
                serviceFactory.getDeveloperService().salaryFromAllDevelopersInProject("boots"));

        log.info("Cписок разработчиков отдельного проекта ('boots'): " +
                serviceFactory.getDeveloperService().projectDevelopers("boots"));

        log.info("Cписок всех Java разработчиков: " +
                serviceFactory.getDeveloperService().developersSkill("Java"));


        log.info("Cписок всех middle разработчиков: " +
                serviceFactory.getDeveloperService().developersSkillLevel("Middle"));

        log.info("Cписок проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте: ");
        log.info(serviceFactory.getProjectService().formatedProjectList());

    }
}
