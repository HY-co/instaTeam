package com.hanyang.instateam.dao;

import com.hanyang.instateam.model.Collaborator;
import java.util.List;

public interface CollaboratorDao {
  List<Collaborator> findAll();
  Collaborator findById(Long id);
  List<Collaborator> findByRoleId(Long roleId);
  void save(Collaborator collaborator);
  void delete(Collaborator collaborator);
}
