package service;

import domain.Project;
import error.InternalException;
import repository.DeveloperDAO;
import repository.ProjectDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public Project getProject(int id) throws InternalException {
        return projectDAO.getProject(id).orElseThrow(() -> {throw new RuntimeException("Project not found by id " + id);});
    }

    public Project getProject(String name) throws InternalException {
        return projectDAO.getProject(name).orElseThrow(() -> {throw new RuntimeException("Project not found by name " + name);});
    }

    public List<Project> getAllProjects() throws InternalException {
        return projectDAO.getAllProjects();
    }

    public void addProject(Project project) throws InternalException {
        projectDAO.addProject(project);
    }

    public void deleteProject(int id) throws InternalException {
        projectDAO.deleteProject(id);
    }

    public void editProject(Project project) throws InternalException {
        projectDAO.editProject(project);
    }

    public List<String> formatedProjectList() throws InternalException {
        ArrayList<String> projects = new ArrayList<>();
        for (Project project : getAllProjects()) {
            String projectName = project.getName();
            projects.add(String.format("%s - %s - %s", Optional.ofNullable(project.getCreateDate()).map(LocalDate::toString).orElse("empty date"),
                    projectName, countDevelopersInProject(projectName)));
        }
        return projects;
    }

    private int countDevelopersInProject(String projectName) {
        return new DeveloperService(new DeveloperDAO()).getAllDevelopersForProject(projectName).size();
    }

}
