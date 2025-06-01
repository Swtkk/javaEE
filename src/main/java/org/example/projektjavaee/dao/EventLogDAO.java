package org.example.projektjavaee.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.projektjavaee.model.EventLog;

import java.util.List;

@Stateless
public class EventLogDAO {

    @PersistenceContext
    private EntityManager em;

    public void log(String username, String action, String details) {
        EventLog event = new EventLog();
        event.setUsername(username);
        event.setAction(action);
        event.setDetails(details);
        event.setTimestamp(java.time.LocalDateTime.now());
        em.persist(event);
    }

    public List<EventLog> findAll() {
        return em.createQuery("SELECT e FROM EventLog e ORDER BY e.timestamp DESC", EventLog.class).getResultList();
    }

    public List<EventLog> findByAction(String action) {
        TypedQuery<EventLog> query = em.createQuery(
                "SELECT e FROM EventLog e WHERE e.action = :action ORDER BY e.timestamp DESC", EventLog.class);
        query.setParameter("action", action);
        return query.getResultList();
    }
}
