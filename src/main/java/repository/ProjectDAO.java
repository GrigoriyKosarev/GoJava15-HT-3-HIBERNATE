package repository;


import domain.ID;
import domain.Project;
import error.InternalException;
import lombok.extern.log4j.Log4j2;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProjectDAO extends GenericDAO<Project, Integer> {

    public ProjectDAO() {
    }

    public Optional<Project> getProject(int id) throws InternalException {
        return get(id, Project.class);
    }

    public Optional<Project> getProject(String name) throws InternalException {
        return get(name, Project.class);
    }

    public List<Project> getAllProjects() throws InternalException {
        return getAll(Project.class);
    }

    public void addProject(Project project) throws InternalException {
        add(project);
    }

    public void deleteProject(int id) throws InternalException {
        delete(id, Project.class);
    }

    public void editProject(Project project) {
        edit(project, project.getId(), Project.class);
    }

}
