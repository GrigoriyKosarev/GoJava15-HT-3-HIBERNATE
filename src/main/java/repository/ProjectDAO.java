package repository;


import domain.Project;
import error.InternalException;
import lombok.extern.log4j.Log4j2;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProjectDAO extends GenericDAO {

    public ProjectDAO() {
    }

    public Optional<Project> getProject(int id) {
        EntityManager entityManager = getEntityManager();
        Project project = entityManager.find(Project.class, id);
        entityManager.close();
        if (project == null)
            return Optional.empty();
        else
            return Optional.of(project);
    }

    public Optional<Project> getProject(String name) {
        EntityManager entityManager = getEntityManager();
        Project project = (Project) entityManager.createQuery("select project from Project project where project.name = :name")
                .setParameter("name", name)
                .getSingleResult();
        entityManager.close();
        if (project == null)
            return Optional.empty();
        else
            return Optional.of(project);
    }

    public List<Project> getAllProjects(){
        EntityManager entityManager = getEntityManager();
        List<Project> projects = entityManager.createQuery("select project from Project project")
                .getResultList();
        entityManager.close();
        return projects;
    }

    public void addProject(Project project) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteProject(int id) throws InternalException {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Project projectFromDB = entityManager.find(Project.class, id);
        if (projectFromDB != null) {
            entityManager.remove(projectFromDB);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            entityManager.close();
            log.error("Delete operation. Project not found by id: " + id);
            throw new InternalException("Delete operation. Project not found by id: " + id);
        }
    }

    public void editProject(Project project) throws InternalException {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Project projectFromDB = entityManager.find(Project.class, project.getId());
        if (projectFromDB != null) {
            entityManager.merge(project);
            entityManager.persist(projectFromDB);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            entityManager.close();
            log.error("Edit operation. Project not found by id: " + project.getId());
            throw new InternalException("Edit operation. Project not found by id: " + project.getId());
        }
    }

}
