package edu.ncsu.mas.platys.multiparty_privacy.dao;

import java.math.BigInteger;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;

@Repository("scenarioDao")
public class ScenarioDaoImpl extends AbstractDao<Integer, Scenario>implements ScenarioDao {

  public Scenario findById(int id) {
    return getByKey(id);
  }

  public long getCount() {
    Query query = getSession().createSQLQuery("select count(id) from scenario");
    return ((BigInteger) query.uniqueResult()).longValue();
  }
}
