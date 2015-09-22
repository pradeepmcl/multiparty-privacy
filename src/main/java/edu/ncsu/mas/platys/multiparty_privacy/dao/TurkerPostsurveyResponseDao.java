package edu.ncsu.mas.platys.multiparty_privacy.dao;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPostsurveyResponse;

public interface TurkerPostsurveyResponseDao {
  void saveResponse(TurkerPostsurveyResponse response);
  long getResponseCount(String mturkId);
  long getMaxBundleId(int minId);
}
