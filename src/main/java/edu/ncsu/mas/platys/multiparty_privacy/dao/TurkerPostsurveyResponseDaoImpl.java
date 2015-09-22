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
    Query query1 = getSession().createSQLQuery("select count(id) from turker_postsurvey_response "
        + "where mturk_id = '" + mturkId + "'");
    long curCount = ((BigInteger) query1.uniqueResult()).longValue();
    
    Query query2 = getSession().createSQLQuery("select count(id) from previous_turker "
        + "where mturk_id = '" + mturkId + "'");
    long prevCount = ((BigInteger) query2.uniqueResult()).longValue();
    
    return prevCount + curCount;
  }
  
  public long getMaxBundleId(int minId) {
    Query query = getSession().createSQLQuery("select ifnull(max(scenario_bundle_id), 0) "
        + "from turker_postsurvey_response where id > " + minId);
    return ((BigInteger) query.uniqueResult()).longValue();
  }

}
