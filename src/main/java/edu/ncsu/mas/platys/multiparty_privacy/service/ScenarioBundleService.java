package edu.ncsu.mas.platys.multiparty_privacy.service;

import edu.ncsu.mas.platys.multiparty_privacy.model.ScenarioBundle;

public interface ScenarioBundleService {
  ScenarioBundle findById(int id);
  long getCount();
}
