package edu.ncsu.mas.platys.multiparty_privacy.dao;

import edu.ncsu.mas.platys.multiparty_privacy.model.ScenarioBundle;

public interface ScenarioBundleDao {
  
  ScenarioBundle findById(int id);
  
  long getCount();
}
