package com.hanyang.instateam.dao;

import com.hanyang.instateam.model.Role;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Role> findAll() {
    // Ope a session
    Session session = sessionFactory.openSession();

    // Create CriteriaBuilder
    CriteriaBuilder builder = session.getCriteriaBuilder();

    // Create CriteriaQuery
    CriteriaQuery<Role> criteria = builder.createQuery(Role.class);

    // Specify criteria root
    criteria.from(Role.class);

    // Execute query
    List<Role> roles = session.createQuery(criteria).getResultList();

    // Close session
    session.close();

    return roles;
  }

  @Override
  public Role findById(Long id) {
    Session session = sessionFactory.openSession();
    Role role = session.get(Role.class, id);
    session.close();
    return role;
  }

  @Override
  public void save(Role role) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.saveOrUpdate(role);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(Role role) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.delete(role);
    session.getTransaction().commit();
    session.close();
  }
}
