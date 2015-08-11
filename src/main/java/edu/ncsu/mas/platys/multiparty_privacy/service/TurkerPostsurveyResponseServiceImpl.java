package edu.ncsu.mas.platys.multiparty_privacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.mas.platys.multiparty_privacy.dao.TurkerPostsurveyResponseDao;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPostsurveyResponse;

@Service("turkerPostsurveyResponseService")
@Transactional
public class TurkerPostsurveyResponseServiceImpl implements TurkerPostsurveyResponseService {

  @Autowired
  private TurkerPostsurveyResponseDao dao;

  @Override
  public void saveResponse(TurkerPostsurveyResponse response) {
    dao.saveResponse(response);
  }

}
