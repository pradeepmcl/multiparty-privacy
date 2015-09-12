package edu.ncsu.mas.platys.multiparty_privacy.dao;

import java.math.BigInteger;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPostsurveyResponse;

@Repository("turkerPostsurveyResponseDao")
public class TurkerPostsurveyResponseDaoImpl extends AbstractDao<Integer, TurkerPostsurveyResponse>
    implements TurkerPostsurveyResponseDao {

  public void saveResponse(TurkerPostsurveyResponse response) {
    persist(response);
  }

  public long getResponseCount(String mturkId) {
    Query query = getSession().createSQLQuery("select count(id) from turker_postsurvey_response "
        + "where mturk_id = '" + mturkId + "'");
    return ((BigInteger) query.uniqueResult()).longValue();
  }
  
  public long getMaxBundleId() {
    Query query = getSession().createSQLQuery("select ifnull(max(scenario_bundle_id), 0) "
        + "from turker_postsurvey_response");
    return ((BigInteger) query.uniqueResult()).longValue();
  }

}
