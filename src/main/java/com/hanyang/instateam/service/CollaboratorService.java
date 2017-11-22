package com.hanyang.instateam.service;

import com.hanyang.instateam.model.Collaborator;
import java.util.List;

public interface CollaboratorService {
  List<Collaborator> findAll();
  Collaborator findById(Long id);
  void save(Collaborator collaborator);
  void delete(Collaborator collaborator);
}
