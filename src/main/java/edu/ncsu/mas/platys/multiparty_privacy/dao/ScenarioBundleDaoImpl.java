package edu.ncsu.mas.platys.multiparty_privacy.dao;

import java.math.BigInteger;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.ncsu.mas.platys.multiparty_privacy.model.ScenarioBundle;

@Repository("scenarioBundleDao")
public class ScenarioBundleDaoImpl extends AbstractDao<Integer, ScenarioBundle> implements
    ScenarioBundleDao {

  public ScenarioBundle findById(int id) {
    return getByKey(id);
  }

  public long getCount() {
    Query query = getSession().createSQLQuery("select count(id) from scenario_bundle");
    return ((BigInteger) query.uniqueResult()).longValue();
  }
}
