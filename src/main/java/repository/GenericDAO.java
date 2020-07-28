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
public abstract class GenericDAO<T, ID> {
    private EntityManager entityManager;

    GenericDAO() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-goit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Optional<T> get(ID id, Class<T> entityClass) throws InternalException {
        T entity = null;
        entityManager = getEntityManager();
        try {
            entity = entityManager.find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            log.error("get "+ entityClass +" operation Exception. Cause={}", e.getMessage());
            log.trace(e.getStackTrace());
            throw new InternalException("get operation Exception. " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public Optional<T> get(String name, Class<T> entityClass) throws InternalException {
        entityManager = getEntityManager();
        String entityClassName = entityClass.getSimpleName();
        try {
            List<T> resultList = (List<T>) entityManager.createQuery("select " +entityClassName.toLowerCase() + " from " + entityClassName + " " + entityClassName.toLowerCase() + " where " + entityClassName.toLowerCase() + ".name = :name")
                    .setParameter("name", name)
                    .getResultList();
            if (resultList.size() > 0)
                return Optional.ofNullable(resultList.get(0));
            else
                return Optional.empty();
        } catch (Exception e) {
            log.error("get operation Exception. Cause={}", e.getMessage());
            log.trace(e.getStackTrace());
            throw new InternalException("get operation Exception. " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public List<T> getAll(Class<T> entityClass) throws InternalException {
        entityManager = getEntityManager();
        try {
            return entityManager.createQuery("select " + entityClass.getSimpleName().toLowerCase() + " from " + entityClass.getSimpleName() + " " + entityClass.getSimpleName().toLowerCase()).getResultList();
        } catch (Exception e) {
            log.error("getAll operation Exception. Cause={}", e.getMessage());
            log.trace(e.getStackTrace());
            throw new InternalException("getAll operation Exception. " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void add(T entity) throws InternalException {
        entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error("add" +  entity.getClass().getSimpleName() + " operation Exception. Cause={}", e.getMessage());
            log.trace(e.getStackTrace());
            entityManager.getTransaction().rollback();
            throw new InternalException("add" +  entity.getClass().getSimpleName() + " operation Exception. " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void delete(int id, Class<T> entityClass) throws InternalException {
        entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            T entityFromDB = entityManager.find(entityClass, id);
            if (entityFromDB != null) {
                entityManager.remove(entityFromDB);
                entityManager.getTransaction().commit();
            } else {
                log.error("Delete operation. Entity not found by id: " + id);
                throw new InternalException("Delete operation. Entity not found by id: " + id);
            }
        } catch (Exception e) {
            log.error("delete operation Exception. Cause={}", e.getMessage());
            log.trace(e.getStackTrace());
            entityManager.getTransaction().rollback();
            throw new InternalException("delete operation Exception. " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void edit(T entity, ID id, Class<T> entityClass) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            T entityFromDB = entityManager.find(entityClass, id);
            if (entityFromDB != null) {
                entityManager.merge(entity);
                entityManager.persist(entityFromDB);
                entityManager.getTransaction().commit();
            } else {
                log.error("Edit operation. Project not found by id: " + id);
                throw new InternalException("Edit operation. Project not found by id: " + id);
            }
        } catch (Exception e) {
            log.error("editCustomer operation Exception.");
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }


}
