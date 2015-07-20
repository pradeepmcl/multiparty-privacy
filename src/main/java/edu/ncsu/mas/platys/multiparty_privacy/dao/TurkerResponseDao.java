package edu.ncsu.mas.platys.multiparty_privacy.dao;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerResponse;

public interface TurkerResponseDao {
  void saveResponse(TurkerResponse response);
}
