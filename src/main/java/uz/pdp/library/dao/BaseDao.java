package uz.pdp.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import uz.pdp.library.entity.Auditable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDao<T extends Auditable, ID extends Serializable> {

    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library_db");
    protected static final EntityManager em = emf.createEntityManager();


    private final Class<T> persistenceClass;

    @SuppressWarnings("unchecked")
    protected BaseDao() {
        this.persistenceClass = (Class<T>) (((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public T save(T entity) {
        begin();
        em.persist(entity);
        commit();
        return entity;
    }

    public T update(T entity) {
        begin();
        em.merge(entity);
        commit();
        return entity;
    }

    public T findById(ID id) {
        return em.find(persistenceClass, id);
    }

    public List<T> findAll() {
        begin();
        List<T> resultList = em.createQuery("from " + persistenceClass.getSimpleName(), persistenceClass).getResultList();
        commit();
        return resultList;
    }


    public boolean deleteById(ID id) {
        begin();
        em.createQuery("delete from " + persistenceClass.getSimpleName()
                        + " t where t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        commit();
        return true;
    }

    public boolean delete(T entity) {
        begin();
        em.remove(entity);
        commit();
        return true;
    }

    protected void begin() {
        em.getTransaction().begin();
    }

    protected void commit() {
        em.getTransaction().commit();
    }

    protected void rollback() {
        em.getTransaction().rollback();
    }

}
