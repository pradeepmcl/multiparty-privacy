package edu.ncsu.mas.platys.multiparty_privacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.mas.platys.multiparty_privacy.dao.ScenarioDao;
import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;

@Service("scenarioService")
@Transactional
public class SecanrioServiceImpl implements ScenarioService {

  @Autowired
  private ScenarioDao dao;
  
  @Override
  public Scenario findById(int id) {
    return dao.findById(id);
  }
  
  @Override
  public long getCount() {
    return dao.getCount();
  }
}
