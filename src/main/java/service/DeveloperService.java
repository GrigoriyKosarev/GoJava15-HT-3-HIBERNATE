package service;

import domain.Developer;
import error.InternalException;
import repository.DeveloperDAO;

import java.math.BigDecimal;
import java.util.List;

public class DeveloperService {

    private final DeveloperDAO developerDAO;

    public DeveloperService(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    public Developer getDeveloper(int id) {
        return developerDAO.getDeveloper(id).orElseThrow(() -> {throw new RuntimeException("Developer not found by id " + id);});
    }

    public Developer getDeveloper(String name) {
        return developerDAO.getDeveloper(name).orElseThrow(() -> {throw new RuntimeException("Developer not found by name " + name);});
    }

    public List<Developer> getAllDevelopers() {
        return developerDAO.getAllDevelopers();
    }

    public void addDeveloper(Developer developers) {
        developerDAO.addDeveloper(developers);
    }

    public void deleteDeveloper(int id) throws InternalException {
        developerDAO.deleteDeveloper(id);
    }

    public void editDeveloper(Developer developers) throws InternalException {
        developerDAO.editDeveloper(developers);
    }

    public BigDecimal salaryFromAllDevelopersInProject(String projectName) {
        return developerDAO.salaryFromAllDevelopersInProject(projectName);
    }

    public List<Developer> getAllDevelopersForProject(String projectName) {
        return developerDAO.getAllDevelopersForProject(projectName);
    }

    public List<Developer> getAllDevelopersForSkill(String skillName) {
        return developerDAO.getAllDevelopersForSkill(skillName);
    }

    public List<Developer> getAllDevelopersForSkillLevel(String skillLevel) {
        return developerDAO.getAllDevelopersForSkillLevel(skillLevel);
    }

}
