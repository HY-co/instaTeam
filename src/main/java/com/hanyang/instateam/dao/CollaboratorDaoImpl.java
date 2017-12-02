package com.hanyang.instateam.dao;

import com.hanyang.instateam.model.Collaborator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CollaboratorDaoImpl implements CollaboratorDao {
  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<Collaborator> findAll() {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Collaborator> criteria = builder.createQuery(Collaborator.class);
    criteria.from(Collaborator.class);
    List<Collaborator> collaborators = session.createQuery(criteria).getResultList();
    session.close();
    return collaborators;
  }

  @Override
  public Collaborator findById(Long id) {
    Session session = sessionFactory.openSession();
    Collaborator collaborator = session.get(Collaborator.class, id);
    session.close();
    return collaborator;
  }

  @Override
  public void save(Collaborator collaborator) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.saveOrUpdate(collaborator);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(Collaborator collaborator) {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.delete(collaborator);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public List<Collaborator> findByRoleId(Long roldId) {
    List<Collaborator> collaborators = findAll();
    return collaborators.stream()
                        .filter(c -> c.getRole().getId() == roldId)
                        .collect(Collectors.toList());
  }
}
