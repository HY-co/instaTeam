package com.hanyang.instateam.service;


import com.hanyang.instateam.dao.CollaboratorDao;
import com.hanyang.instateam.model.Collaborator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollaboratorServiceImpl implements CollaboratorDao {
  @Autowired
  private CollaboratorDao collaboratorDao;

  @Override
  public List<Collaborator> findAll() {
    return collaboratorDao.findAll();
  }

  @Override
  public Collaborator findById(Long id) {
    return collaboratorDao.findById(id);
  }

  @Override
  public void save(Collaborator collaborator) {
    collaboratorDao.save(collaborator);
  }

  @Override
  public void delete(Collaborator collaborator) {
    collaboratorDao.delete(collaborator);
  }
}
