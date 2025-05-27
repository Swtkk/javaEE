package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.projektjavaee.model.Product;

import java.util.List;

@Stateless
public class ProductDAOImpl implements ProductDAO {
    @PersistenceContext(unitName = "SklepPU")
    public EntityManager em;
    public void create(Product p) { em.persist(p); }
    public Product find(Long id) { return em.find(Product.class, id); }
    public List<Product> findAll() { return em.createQuery("SELECT p FROM Product p", Product.class).getResultList(); }
    public void update(Product p) { em.merge(p); }
    public void delete(Long id) {
        Product p = find(id);
        if (p != null) em.remove(p);
    }
    public void setEntityManager(EntityManager em) { this.em = em; }
}
