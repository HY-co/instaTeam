package com.hanyang.instateam.service;

import com.hanyang.instateam.dao.RoleDao;
import com.hanyang.instateam.model.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleDao roleDao;

  @Override
  public List<Role> findAll() {
    return roleDao.findAll();
  }

  @Override
  public Role findById(Long id) {
    return roleDao.findById(id);
  }

  @Override
  public void save(Role role) {
    roleDao.save(role);
  }

  @Override
  public void delete(Role role) {
    roleDao.delete(role);
  }
}
