package edu.ncsu.mas.platys.multiparty_privacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.mas.platys.multiparty_privacy.dao.ScenarioBundleDao;
import edu.ncsu.mas.platys.multiparty_privacy.model.ScenarioBundle;

@Service("scenarioBundleService")
@Transactional
public class SecanrioBundleServiceImpl implements ScenarioBundleService {

  @Autowired
  private ScenarioBundleDao dao;
  
  @Override
  public ScenarioBundle findById(int id) {
    return dao.findById(id);
  }
  
  @Override
  public long getCount() {
    return dao.getCount();
  }
}
