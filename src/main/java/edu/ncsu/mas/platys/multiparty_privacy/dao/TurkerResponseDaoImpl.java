package edu.ncsu.mas.platys.multiparty_privacy.dao;

import org.springframework.stereotype.Repository;

import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerResponse;

@Repository("turkerResponseDao")
public class TurkerResponseDaoImpl extends AbstractDao<Integer, TurkerResponse> implements
    TurkerResponseDao {

  public void saveResponse(TurkerResponse response) {
    persist(response);
  }

}
