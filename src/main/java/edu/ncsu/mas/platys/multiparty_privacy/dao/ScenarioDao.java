package edu.ncsu.mas.platys.multiparty_privacy.dao;

import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;

public interface ScenarioDao {
  
  Scenario findById(int id);
  
  long getCount();
}
