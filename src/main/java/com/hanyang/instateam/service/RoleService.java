package com.hanyang.instateam.service;

import com.hanyang.instateam.model.Role;
import java.util.List;

public interface RoleService {
  List<Role> findAll();
  Role findById(Long id);
  void save(Role role);
  void delete(Role role);
}
