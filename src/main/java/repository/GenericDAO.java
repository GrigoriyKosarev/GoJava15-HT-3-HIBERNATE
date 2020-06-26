package repository;

import error.InternalException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class GenericDAO<T, ID> {
    private EntityManager entityManager;

    GenericDAO() {
        //entityManager = new GenericDAO().getEntityManager();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-goit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Optional<T> get(int id, Class<T> entityClass) {
        T entity = null;
        entityManager = getEntityManager();
        try {
            entity = entityManager.find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            log.error("get"+ entity.getClass().getSimpleName() +" operation Exception.");
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    public Optional<T> get(String name, String queryString) {
        entityManager = getEntityManager();
        try {
            List<T> resultList = (List<T>) entityManager.createQuery(queryString)
                    .setParameter("name", name)
                    .getResultList();
            if (resultList.size() > 0)
                return Optional.ofNullable(resultList.get(0));
            else
                return Optional.empty();
        } catch (Exception e) {
            log.error("get operation Exception.");
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    public List<T> getAll(String queryString) {
        entityManager = getEntityManager();
        try {
            return entityManager.createQuery(queryString).getResultList();
        } catch (Exception e) {
            log.error("getAll operation Exception.");
            return new ArrayList<>();
        } finally {
            entityManager.close();
        }
    }

    public void add(T entity) {
        entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error("add" +  entity.getClass().getSimpleName() + " operation Exception.");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void delete(Class<T> entityClass, int id) {
        entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            T entityFromDB = entityManager.find(entityClass, id);
            if (entityFromDB != null) {
                entityManager.remove(entityFromDB);
                entityManager.flush();
                entityManager.clear();
                //entityManager.getTransaction().commit();
            } else {
                log.error("Delete operation. Entity not found by id: " + id);
                throw new InternalException("Delete operation. Entity not found by id: " + id);
            }
        } catch (Exception e) {
            log.error("delete operation Exception.");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

}
