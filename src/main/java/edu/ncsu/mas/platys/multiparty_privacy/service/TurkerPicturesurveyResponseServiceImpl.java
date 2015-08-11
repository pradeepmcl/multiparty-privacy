package edu.ncsu.mas.platys.multiparty_privacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.mas.platys.multiparty_privacy.dao.TurkerPicturesurveyResponseDao;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPicturesurveyResponse;

@Service("turkerPicturesurveyResponseService")
@Transactional
public class TurkerPicturesurveyResponseServiceImpl implements TurkerPicturesurveyResponseService {

  @Autowired
  private TurkerPicturesurveyResponseDao dao;

  @Override
  public void saveResponse(TurkerPicturesurveyResponse response) {
    dao.saveResponse(response);
  }
  
}
