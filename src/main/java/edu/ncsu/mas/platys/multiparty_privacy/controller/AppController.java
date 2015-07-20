package edu.ncsu.mas.platys.multiparty_privacy.controller;

import java.util.Locale;
import java.util.Random;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerResponse;
import edu.ncsu.mas.platys.multiparty_privacy.service.ScenarioService;
import edu.ncsu.mas.platys.multiparty_privacy.service.TurkerResponseService;
import edu.ncsu.mas.platys.multiparty_privacy.util.RandomCodeGenerator;

@Controller
@RequestMapping("/")
public class AppController {

  @Autowired
  ScenarioService scenarioService;
  
  @Autowired
  TurkerResponseService turkerResponseService;

  @Autowired
  MessageSource messageSource;

  Random rand = new Random();
  
  RandomCodeGenerator randCodeGen = new RandomCodeGenerator(8);

  /*
   * This method shows the signin page.
   */
  @RequestMapping(value = { "/", "/signin" }, method = RequestMethod.GET)
  public String showSignIn(ModelMap model) {
    model.addAttribute("turker_response", new TurkerResponse());
    return "signin";
  }

  /*
   * This method validates the signin ID and loads the questionnaire page.
   */
  @RequestMapping(value = { "/questionnaire" }, method = RequestMethod.POST)
  public String showQuestionnaire(@ModelAttribute("scenario") Scenario scenario,
      @ModelAttribute("turker_response") TurkerResponse turkerResponse, BindingResult result,
      ModelMap model) {
    if (isMturkIDValid(turkerResponse.getMturkId())) {
      // The page is being loaded for the first time
      if (turkerResponse.getScenarioId() == 0) { // 0 is the default value
        scenario = getARandomScenario();
        turkerResponse.setScenarioId(scenario.getId());
        model.addAttribute("scenario", scenario);
        return "questionnaire";
      } else { // The page was submitted
        if (isTurkerResponseValid(turkerResponse, result, model)) {
          turkerResponse.setResponseTime(LocalDateTime.now());
          turkerResponse.setCompletionCode(randCodeGen.nextString());
          
          turkerResponseService.saveResponse(turkerResponse);

          model.addAttribute("mturkId", turkerResponse.getMturkId());
          model.addAttribute("completionCode", turkerResponse.getCompletionCode());
          return "success";
        } else { // Invalid response, go back
          scenario = scenarioService.findById(turkerResponse.getScenarioId());
          model.addAttribute("scenario", scenario);
          return "questionnaire";
        }
      }
    } else {
      return "signin_failure"; // TODO: This page does not exist
    }
  }

  private boolean isMturkIDValid(String mturkID) {
    if (mturkID != null && !mturkID.isEmpty()) {
      // TODO: Check that the user does not exceed permitted number of HIT
      // responses.
      return true;
    }
    return false;
  }

  private Scenario getARandomScenario() {
    long scenarioCount = scenarioService.getCount();
    int randomScenario = randInt(1, (int) scenarioCount);
    Scenario scenario = scenarioService.findById(randomScenario);
    return scenario;
  }

  /**
   * Returns a pseudo-random number between min and max, inclusive. The
   * difference between min and max can be at most
   * <code>Integer.MAX_VALUE - 1</code>.
   */
  private int randInt(int min, int max) {
    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
  }

  // This is some reusable code
  /*
   * if (result.hasErrors()) {
   * List<FieldError> errors = result.getFieldErrors();
   * for (FieldError error : errors) {
   * System.out.println(error.toString());
   * }
   */
  
  // TODO: I am not sure if this is the standard way of validating
  private boolean isTurkerResponseValid(TurkerResponse turkerResponse, BindingResult result,
      ModelMap model) {
    boolean isValid = true;
    
    if (turkerResponse.getPolicy() == null) {
      FieldError error = new FieldError("turkerResponse", "policy", messageSource.getMessage(
          "mandatory.answer", null, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    } else if (turkerResponse.getPolicy().equals("other")
        && (turkerResponse.getPolicyOther() == null || turkerResponse.getPolicyOther().isEmpty())) {
      FieldError error = new FieldError("turkerResponse", "policyOther", messageSource.getMessage(
          "mandatory.if.answer", new String[] { "other policy", "in the text box next to it" },
          Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }
    
    if (turkerResponse.getPolicyJustification() == null
        || turkerResponse.getPolicyJustification().isEmpty()) {
      FieldError error = new FieldError("turkerResponse", "policyJustification",
          messageSource.getMessage("mandatory.answer", null, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }
    
    model.addAttribute("turker_response", turkerResponse);
    return isValid;
  }
}
