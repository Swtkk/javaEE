package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.projektjavaee.dao.OrderDAO;
import org.example.projektjavaee.model.Order;
import org.example.projektjavaee.model.User;

import java.util.List;

@Stateless
public class OrderDAOImpl implements OrderDAO {
    @PersistenceContext(unitName = "SklepPU")
    private EntityManager em;

    public void create(Order order) {
        em.persist(order);
    }

    public List<Order> findByUser(User user) {
        return em.createQuery("SELECT o FROM Order o WHERE o.user = :user", Order.class)
                .setParameter("user", user)
                .getResultList();
    }
}
