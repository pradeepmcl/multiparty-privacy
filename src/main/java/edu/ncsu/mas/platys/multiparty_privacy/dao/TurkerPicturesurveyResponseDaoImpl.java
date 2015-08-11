package edu.ncsu.mas.platys.multiparty_privacy.dao;

import org.springframework.stereotype.Repository;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPicturesurveyResponse;

@Repository("turkerPicturesurveyResponseDao")
public class TurkerPicturesurveyResponseDaoImpl extends
    AbstractDao<Integer, TurkerPicturesurveyResponse> implements TurkerPicturesurveyResponseDao {

  public void saveResponse(TurkerPicturesurveyResponse response) {
    persist(response);
  }

}
