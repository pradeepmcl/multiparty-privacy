package edu.ncsu.mas.platys.multiparty_privacy.dao;

import org.springframework.stereotype.Repository;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPostsurveyResponse;

@Repository("turkerPostsurveyResponseDao")
public class TurkerPostsurveyResponseDaoImpl extends AbstractDao<Integer, TurkerPostsurveyResponse>
    implements TurkerPostsurveyResponseDao {

  public void saveResponse(TurkerPostsurveyResponse response) {
    persist(response);
  }

}
