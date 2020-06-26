package repository;


import domain.ID;
import domain.Project;
import error.InternalException;
import lombok.extern.log4j.Log4j2;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProjectDAO {

    public ProjectDAO() {
    }

    public Optional<Project> getProject(int id) {
        return new GenericDAO<Project, ID>().get(id, Project.class);
    }

    public Optional<Project> getProject(String name) {
        return new GenericDAO<Project, ID>().get(name, "select project from Project project where project.name = :name");
    }

    public List<Project> getAllProjects(){
        return new GenericDAO<Project, ID>().getAll("select project from Project project");
    }

    public void addProject(Project project) {
        new GenericDAO<Project, ID>().add(project);
    }

    public void deleteProject(int id) throws InternalException {
        new GenericDAO<Project, ID>().delete(Project.class, id);
    }

    public void editProject(Project project) {
        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Project projectFromDB = entityManager.find(Project.class, project.getId());
            if (projectFromDB != null) {
                entityManager.merge(project);
                entityManager.persist(projectFromDB);
                entityManager.getTransaction().commit();
            } else {
                log.error("Edit operation. Project not found by id: " + project.getId());
                throw new InternalException("Edit operation. Project not found by id: " + project.getId());
            }
        } catch (Exception e) {
            log.error("editProject operation Exception.");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

}
