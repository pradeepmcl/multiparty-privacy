package edu.ncsu.mas.platys.multiparty_privacy.service;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPostsurveyResponse;

public interface TurkerPostsurveyResponseService {
  void saveResponse(TurkerPostsurveyResponse response);
  long getResponseCount(String mturkId);
  long getMaxBundleId();
}
