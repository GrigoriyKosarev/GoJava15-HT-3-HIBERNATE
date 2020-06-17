package repository;

import domain.Developer;
import domain.Sex;
import error.InternalException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class DeveloperDAO extends GenericDAO{

    public Optional<Developer> getDeveloper(int id) {
        EntityManager entityManager = getEntityManager();
        Developer developer = entityManager.find(Developer.class, id);
        entityManager.close();
        if (developer == null)
            return Optional.empty();
        else
            return Optional.of(developer);
    }

    public Optional<Developer> getDeveloper(String name) {
        EntityManager entityManager = getEntityManager();
        Developer developer = (Developer) entityManager.createQuery("select developer from Developer developer where developer.name = :name")
                .setParameter("name", name)
                .getSingleResult();
        entityManager.close();
        if (developer == null)
            return Optional.empty();
        else
            return Optional.of(developer);
    }

    public List<Developer> getAllDevelopers() {
        EntityManager entityManager = getEntityManager();
        List<Developer> developers = entityManager.createQuery("select developer from Developer developer")
                .getResultList();
        entityManager.close();
        return developers;
    }

    public void addDeveloper(Developer developer) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(developer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteDeveloper(int id) throws InternalException {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Developer developerFromDB = entityManager.find(Developer.class, id);
        if (developerFromDB != null) {
            entityManager.remove(developerFromDB);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            entityManager.close();
            log.error("Delete operation. Developer not found by id: " + id);
            throw new InternalException("Delete operation. Developer not found by id: " + id);
        }
    }

    public void editDeveloper(Developer developer) throws InternalException {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Developer developerFromDB = entityManager.find(Developer.class, developer.getId());
        if (developerFromDB != null) {
            entityManager.merge(developer);
            entityManager.persist(developerFromDB);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            entityManager.close();
            log.error("Edit operation. Developer not found by id: " + developer.getId());
            throw new InternalException("Edit operation. Developer not found by id: " + developer.getId());
        }
    }

    public double salaryFromAllDevelopersInProject(String projectName) {
        String selectQwery = "SELECT " +
                "sum(developers.salary) as salary " +
                "FROM projects " +
                "Left JOIN developers_projects ON developers_projects.project_id = projects.id " +
                "Left JOIN developers ON developers_projects.developer_id = developers.id " +
                "where projects.name = '" + projectName + "'";
        EntityManager entityManager = getEntityManager();
        double result = (Double) entityManager.createNativeQuery(selectQwery).getSingleResult();
        entityManager.close();
        return result;
    }

    public List<Developer> projectDevelopers(String projectName) {
        List<Developer> developers = new ArrayList<>();
        String selectQuery = "SELECT projects.name as project_name, " +
                "developers.name as developer_name, " +
                "developers.id as developer_id, " +
                "developers.age as developer_age, " +
                "developers.sex as developer_sex,  " +
                "developers.salary as developer_salary  " +
                "FROM developers_projects " +
                "Left join developers on developers.id = developers_projects.developer_id " +
                "Left join projects on projects.id = developers_projects.project_id " +
                "where projects.name = '" + projectName + "'";
        EntityManager entityManager = getEntityManager();
        List<Object[]> resultQuery = entityManager.createNativeQuery(selectQuery).getResultList();
        for (Object[] row : resultQuery) {
            Developer developer = new Developer();
            developer.setId(Integer.valueOf(row[2].toString()));
            developer.setName(row[1].toString());
            developer.setAge(Integer.valueOf(row[3].toString()));
            developer.setSex(Sex.valueOf(row[4].toString()));
            developer.setSalary(BigDecimal.valueOf(Double.valueOf(row[5].toString())));
            developers.add(developer);
        }
        entityManager.close();
        return developers;
    }

    public List<Developer> developerSkill(String skillName) {
        List<Developer> developers = new ArrayList<>();
        String selectQuery = "SELECT " +
                "skills.name as skill_name, " +
                "developers.id as developer_id, " +
                "developers.name as developer_name, " +
                "developers.age as developer_age, " +
                "developers.sex as developer_sex, " +
                "developers.salary as developer_salary " +
                "FROM skills_developers " +
                "left join skills on skills_developers.skill_id = skills.id " +
                "left join developers on skills_developers.developer_id = developers.id " +
                "where skills.name = '" + skillName + "'";

        EntityManager entityManager = getEntityManager();
        List<Object[]> resultQuery = entityManager.createNativeQuery(selectQuery).getResultList();
        for (Object[] row : resultQuery) {
            Developer developer = new Developer();
            developer.setId(Integer.valueOf(row[1].toString()));
            developer.setName(row[2].toString());
            developer.setAge(Integer.valueOf(row[3].toString()));
            developer.setSex(Sex.valueOf(row[4].toString()));
            developer.setSalary(BigDecimal.valueOf(Double.valueOf(row[5].toString())));
            developers.add(developer);
        }
        entityManager.close();
        return developers;
    }

    public List<Developer> developersSkillLevel(String skillLevel) {
        List<Developer> developers = new ArrayList<>();
        String selectQuery = "SELECT " +
                "skill_levels.name as skill_levels_name, " +
                "developers.id as developer_id, " +
                "developers.name as developer_name, " +
                "developers.age as developer_age, " +
                "developers.sex as developer_sex, " +
                "developers.salary as developer_salary " +
                "FROM skills_developers " +
                "left join skill_levels on skills_developers.skill_level_id = skill_levels.id " +
                "left join developers on skills_developers.developer_id = developers.id " +
                "where skill_levels.name = '" + skillLevel + "'";

        EntityManager entityManager = getEntityManager();
        List<Object[]> resultQuery = entityManager.createNativeQuery(selectQuery).getResultList();
        for (Object[] row : resultQuery) {
            Developer developer = new Developer();
            developer.setId(Integer.valueOf(row[1].toString()));
            developer.setName(row[2].toString());
            developer.setAge(Integer.valueOf(row[3].toString()));
            developer.setSex(Sex.valueOf(row[4].toString()));
            developer.setSalary(BigDecimal.valueOf(Double.valueOf(row[5].toString())));
            developers.add(developer);
        }
        entityManager.close();
        return developers;
    }

}
