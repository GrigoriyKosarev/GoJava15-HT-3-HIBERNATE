package repository;

import domain.Developer;
import domain.ID;
import domain.Sex;
import error.InternalException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class DeveloperDAO {

    public Optional<Developer> getDeveloper(int id) {
        return new GenericDAO<Developer, ID>().get(id, Developer.class);
    }

    public Optional<Developer> getDeveloper(String name) {
        return new GenericDAO<Developer, ID>().get(name, "select developer from Developer developer where developer.name = :name");
    }

    public List<Developer> getAllDevelopers() {
        return new GenericDAO<Developer, ID>().getAll("select developer from Developer developer");
    }

    public void addDeveloper(Developer developer) {
        new GenericDAO<Developer, ID>().add(developer);
    }

    public void deleteDeveloper(int id) {
        new GenericDAO<Developer, ID>().delete(Developer.class, id);
    }

    public void editDeveloper(Developer developer) throws InternalException {
        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Developer developerFromDB = entityManager.find(Developer.class, developer.getId());
            if (developerFromDB != null) {
                entityManager.merge(developer);
                entityManager.persist(developerFromDB);
                entityManager.getTransaction().commit();
            } else {
                log.error("Edit operation. Developer not found by id: " + developer.getId());
                throw new InternalException("Edit operation. Developer not found by id: " + developer.getId());
            }
        } catch (Exception e) {
            log.error("editDeveloper operation Exception.");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public BigDecimal salaryFromAllDevelopersInProject(String projectName) {
        String selectQwery = "SELECT " +
                "sum(developers.salary) as salary " +
                "FROM projects " +
                "Left JOIN developers_projects ON developers_projects.project_id = projects.id " +
                "Left JOIN developers ON developers_projects.developer_id = developers.id " +
                "where projects.name = '" + projectName + "'";
        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            Double resultFromQuery = (Double) entityManager.createNativeQuery(selectQwery).getSingleResult();
            return new BigDecimal(resultFromQuery);
        } catch (Exception e) {
            log.error("salaryFromAllDevelopersInProject operation Exception.");
            return new BigDecimal(0);
        } finally {
            entityManager.close();
        }
    }

    public List<Developer> getAllDevelopersForProject(String projectName) {
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
        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            List<Object[]> resultQuery = entityManager.createNativeQuery(selectQuery).getResultList();
            for (Object[] row : resultQuery) {
                Developer developer = new Developer();
                developer.setId(Integer.parseInt(row[2].toString()));
                developer.setName(row[1].toString());
                developer.setAge(Integer.parseInt(row[3].toString()));
                developer.setSex(Sex.valueOf(row[4].toString()));
                developer.setSalary(new BigDecimal(Double.parseDouble(row[5].toString())));
                developers.add(developer);
            }
            return developers;
        } catch (Exception e) {
            log.error("getAllDevelopersForProject operation Exception.");
            return new ArrayList<Developer>();
        } finally {
            entityManager.close();
        }
    }

    public List<Developer> getAllDevelopersForSkill(String skillName) {
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

        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            List<Object[]> resultQuery = entityManager.createNativeQuery(selectQuery).getResultList();
            for (Object[] row : resultQuery) {
                Developer developer = new Developer();
                developer.setId(Integer.parseInt(row[1].toString()));
                developer.setName(row[2].toString());
                developer.setAge(Integer.parseInt(row[3].toString()));
                developer.setSex(Sex.valueOf(row[4].toString()));
                developer.setSalary(BigDecimal.valueOf(Double.parseDouble(row[5].toString())));
                developers.add(developer);
            }
            return developers;
        } catch (Exception e) {
            log.error("getAllDevelopersForSkill operation Exception.");
            return new ArrayList<Developer>();
        } finally {
            entityManager.close();
        }
    }

    public List<Developer> getAllDevelopersForSkillLevel(String skillLevel) {
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

        EntityManager entityManager = new GenericDAO().getEntityManager();
        try {
            List<Object[]> resultQuery = entityManager.createNativeQuery(selectQuery).getResultList();
            for (Object[] row : resultQuery) {
                Developer developer = new Developer();
                developer.setId(Integer.parseInt(row[1].toString()));
                developer.setName(row[2].toString());
                developer.setAge(Integer.parseInt(row[3].toString()));
                developer.setSex(Sex.valueOf(row[4].toString()));
                developer.setSalary(new BigDecimal(Double.parseDouble(row[5].toString())));
                developers.add(developer);
            }
            return developers;
        } catch (Exception e) {
            log.error("getAllDevelopersForSkillLevel operation Exception.");
            return new ArrayList<Developer>();
        } finally {
            entityManager.close();
        }
    }

}
