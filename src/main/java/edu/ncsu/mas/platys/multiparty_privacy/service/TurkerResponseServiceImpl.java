package edu.ncsu.mas.platys.multiparty_privacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.mas.platys.multiparty_privacy.dao.TurkerResponseDao;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerResponse;

@Service("turkerResponseService")
@Transactional
public class TurkerResponseServiceImpl implements TurkerResponseService {

  @Autowired
  private TurkerResponseDao dao;

  @Override
  public void saveResponse(TurkerResponse response) {
    dao.saveResponse(response);
  }
  
}
