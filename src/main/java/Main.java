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

    public static void main(String[] args) throws InternalException {

        //!Помилка
        ServiceFactory.getCustomerService().deleteCustomer(1);

        //CUSTOMER TEST
        ServiceFactory.getCustomerService().addCustomer(new Customer("Apple", "Apple"));
        log.info("find customers by name: " + ServiceFactory.getCustomerService().getCustomer("BMW"));
        ServiceFactory.getCustomerService().addCustomer(new Customer("IBM", "IBM"));
        //ServiceFactory.getCustomerService().deleteCustomer(1);
        Customer newCustomer = new Customer(4, "Apple+", "Apple+");
        ServiceFactory.getCustomerService().editCustomer(newCustomer);
        log.info("all customers: " + ServiceFactory.getCustomerService().getAllCustomers());

        //PROJECT TEST
        ServiceFactory.getProjectService().addProject(new Project("Apple to do", "to do", new BigDecimal(1000), LocalDate.of(2020, 1, 1)));
        log.info("find projects by name: " + ServiceFactory.getProjectService().getProject("Apple to do"));
        ServiceFactory.getProjectService().addProject(new Project("IBM to do", "to do", new BigDecimal(2000), LocalDate.of(2020, 1, 1)));
        //serviceFactory.getProjectService().deleteProject(2);
        Project newProject = new Project(2, "Apple to do +", "to do +", new BigDecimal(5000), LocalDate.of(2020, 1, 1));
        ServiceFactory.getProjectService().editProject(newProject);
        log.info("all projects: " + ServiceFactory.getProjectService().getAllProjects());

        //DEVELOPER TEST
        ServiceFactory.getDeveloperService().addDeveloper(new Developer("Petro", 40, Sex.MEN, new BigDecimal(4000)));
        log.info("find developers by name: " + ServiceFactory.getDeveloperService().getDeveloper("Petro"));
        ServiceFactory.getDeveloperService().addDeveloper(new Developer("Vas-Vas", 20, Sex.MEN, new BigDecimal(800)));
        //serviceFactory.getDeveloperService().deleteDeveloper(serviceFactory.getDeveloperService().getDeveloper("Vas-Vas").getId());
        Developer newDeveloper = new Developer(4, "Petro+", 42, Sex.MEN, new BigDecimal(4000));
        ServiceFactory.getDeveloperService().editDeveloper(newDeveloper);
        log.info("all developers: " + ServiceFactory.getDeveloperService().getAllDevelopers());

        log.info("Зарплата всех разработчиков отдельного проекта ('boots'): " +
                ServiceFactory.getDeveloperService().salaryFromAllDevelopersInProject("boots"));

        log.info("Cписок разработчиков отдельного проекта ('boots'): " +
                ServiceFactory.getDeveloperService().getAllDevelopersForProject("boots"));

        log.info("Cписок всех Java разработчиков: " +
                ServiceFactory.getDeveloperService().getAllDevelopersForSkill("Java"));


        log.info("Cписок всех middle разработчиков: " +
                ServiceFactory.getDeveloperService().getAllDevelopersForSkillLevel("Middle"));

        log.info("Cписок проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте: ");
        log.info(ServiceFactory.getProjectService().formatedProjectList());
        
    }
}
