package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.example.projektjavaee.model.User;

@Stateless
public class UserDAOImpl implements UserDAO {
    @PersistenceContext(unitName = "SklepPU")
    private EntityManager em;
    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public void create(User user) { em.persist(user); }
}
