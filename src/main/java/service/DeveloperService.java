package service;

import domain.Developer;
import error.InternalException;
import repository.DeveloperDAO;

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

    public double salaryFromAllDevelopersInProject(String projectName) {
        return developerDAO.salaryFromAllDevelopersInProject(projectName);
    }

    public List<Developer> projectDevelopers(String projectName) {
        return developerDAO.projectDevelopers(projectName);
    }

    public List<Developer> developersSkill(String skillName) {
        return developerDAO.developerSkill(skillName);
    }

    public List<Developer> developersSkillLevel(String skillLevel) {
        return developerDAO.developersSkillLevel(skillLevel);
    }

}
