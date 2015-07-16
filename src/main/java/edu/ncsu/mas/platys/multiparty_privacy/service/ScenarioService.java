package edu.ncsu.mas.platys.multiparty_privacy.service;

import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;

public interface ScenarioService {
  Scenario findById(int id);
  long getCount();
}
