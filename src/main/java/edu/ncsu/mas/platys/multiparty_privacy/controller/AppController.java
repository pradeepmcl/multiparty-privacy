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
import edu.ncsu.mas.platys.multiparty_privacy.model.ScenarioBundle;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerResponse;
import edu.ncsu.mas.platys.multiparty_privacy.service.ScenarioBundleService;
import edu.ncsu.mas.platys.multiparty_privacy.service.ScenarioService;
import edu.ncsu.mas.platys.multiparty_privacy.service.TurkerResponseService;
import edu.ncsu.mas.platys.multiparty_privacy.util.RandomCodeGenerator;

@Controller
@RequestMapping("/")
public class AppController {

  @Autowired
  ScenarioService scenarioService;

  @Autowired
  ScenarioBundleService scenarioBundleService;

  @Autowired
  TurkerResponseService turkerResponseService;

  @Autowired
  MessageSource messageSource;

  // Changing this variable requires that the db be updated accordingly
  private static final int MAX_SCENARIOS = 5;
  
  private static final String PAGE_SIGNIN = "signin";
  private static final String PAGE_SIGNIN_FAILURE = "signin_failure"; // TODO
  private static final String PAGE_QUESTIONNAIRE = "questionnaire";
  private static final String PAGE_SUCCESS = "success";
  
  private static final String ATTR_SCENARIO = "scenario";
  private static final String ATTR_TURKER_RESPONSE = "turkerResponse"; //TODO Rename
  private static final String ATTR_MTURK_ID = "mturkId";
  private static final String ATTR_COMPLETION_CODE =  "completionCode";
  
  Random rand = new Random();
  
  RandomCodeGenerator randCodeGen = new RandomCodeGenerator(8);

  /*
   * This method shows the signin page.
   */
  @RequestMapping(value = { "/", "/" + PAGE_SIGNIN }, method = RequestMethod.GET)
  public String showSignIn(ModelMap model) {
    model.addAttribute(ATTR_TURKER_RESPONSE, new TurkerResponse());
    return PAGE_SIGNIN;
  }

  /*
   * This method validates the signin ID and loads the questionnaire page.
   */
  @RequestMapping(value = { "/" + PAGE_QUESTIONNAIRE }, method = RequestMethod.POST)
  public String showQuestionnaire(@ModelAttribute(ATTR_SCENARIO) Scenario scenario,
      @ModelAttribute(ATTR_TURKER_RESPONSE) TurkerResponse turkerResponse, BindingResult result,
      ModelMap model) {
    if (isMturkIDValid(turkerResponse.getMturkId())) {
      if (turkerResponse.getScenarioBundleId() == 0) {
        // The page is being loaded for the first time (0 is the default value)
        ScenarioBundle bundle = getScenarioBundle();        
        String[] scenarios = bundle.getScenariosCsv().split(",");
        int scenarioId =  Integer.parseInt(scenarios[0]);
        scenario = scenarioService.findById(scenarioId);
        
        turkerResponse.setScenarioBundleId(bundle.getId());
        turkerResponse.setScenarioBundleIndex(0);
        turkerResponse.setScenariosCsv(bundle.getScenariosCsv());
        turkerResponse.setScenarioId(scenarioId);
        
        model.addAttribute(ATTR_TURKER_RESPONSE, turkerResponse);
        model.addAttribute(ATTR_SCENARIO, scenario);
        return PAGE_QUESTIONNAIRE;
      } else {
        // A response was submitted
        if (isTurkerResponseValid(turkerResponse, result, model)) {
          turkerResponse.setResponseTime(LocalDateTime.now());
          String pageToReturn = PAGE_QUESTIONNAIRE;
          int scenarioIndex = turkerResponse.getScenarioBundleIndex() + 1;
          if (scenarioIndex >= MAX_SCENARIOS) {
            // Last response
            turkerResponse.setCompletionCode(randCodeGen.nextString());
            turkerResponseService.saveResponse(turkerResponse);
            
            model.addAttribute(ATTR_MTURK_ID, turkerResponse.getMturkId());
            model.addAttribute(ATTR_COMPLETION_CODE, turkerResponse.getCompletionCode());
            pageToReturn = PAGE_SUCCESS;
          } else {
            // Show next scenario
            turkerResponseService.saveResponse(turkerResponse);
            
            String scenarioCsv = turkerResponse.getScenariosCsv();
            String[] scenarios = scenarioCsv.split(",");
            int scenarioId = Integer.parseInt(scenarios[scenarioIndex]);
            scenario = scenarioService.findById(scenarioId);
            
            turkerResponse.resetResponse();
            turkerResponse.setScenarioId(scenarioId);
            turkerResponse.setScenarioBundleIndex(scenarioIndex);
            
            model.addAttribute(ATTR_TURKER_RESPONSE, turkerResponse);
            model.addAttribute(ATTR_SCENARIO, scenario);
            model.addAttribute(ATTR_MTURK_ID, turkerResponse.getMturkId());
          }
          
          return pageToReturn;
        } else {
          // An invalid response was submitted, go back
          scenario = scenarioService.findById(turkerResponse.getScenarioId());
          
          model.addAttribute(ATTR_TURKER_RESPONSE, turkerResponse);
          model.addAttribute(ATTR_SCENARIO, scenario);
          return PAGE_QUESTIONNAIRE;
        }
      }     
    } else {
      return PAGE_SIGNIN_FAILURE; // TODO: This page does not exist
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

  // TODO
  private ScenarioBundle getScenarioBundle() {
    return scenarioBundleService.findById(1);
  }
  

  /*
  private Scenario getARandomScenario() {
    long scenarioCount = scenarioService.getCount();
    int randomScenario = randInt(1, (int) scenarioCount);
    Scenario scenario = scenarioService.findById(randomScenario);
    return scenario;
  }*/

  /**
   * Returns a pseudo-random number between min and max, inclusive. The
   * difference between min and max can be at most
   * <code>Integer.MAX_VALUE - 1</code>.
   */
  /*
  private int randInt(int min, int max) {
    // nextInt is exclusive of the top value; add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
  }*/

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

    isValid = validateTurkerResponseForImageQuestions(turkerResponse, result, isValid);
    isValid = validateTurkerResponseForCase(turkerResponse, result, "case1", isValid);
    isValid = validateTurkerResponseForCase(turkerResponse, result, "case2", isValid);

    return isValid;
  }

  private boolean validateTurkerResponseForImageQuestions(TurkerResponse turkerResponse,
      BindingResult result, boolean isValid) {
    if (turkerResponse.getImageSensitivity() == null || turkerResponse.getImageSentiment() == null
        || turkerResponse.getImageRelationship() == null
        || turkerResponse.getImagePeopleCount() == null) {
      FieldError error = new FieldError("turkerResponse", "imagePeopleCount",
          messageSource.getMessage("mandatory.answers", new String[] { "above" },
              Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }
    return isValid;
  }

  private boolean validateTurkerResponseForCase(TurkerResponse turkerResponse,
      BindingResult result, String _case, boolean isValid) {
    if (turkerResponse.getPolicy(_case) == null) {
      FieldError error = new FieldError("turkerResponse", _case + "Policy",
          messageSource.getMessage("mandatory.answer", null, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    } else if (turkerResponse.getPolicy(_case).equals("other")
        && (turkerResponse.getPolicyOther(_case) == null || turkerResponse.getPolicyOther(_case)
            .isEmpty())) {
      FieldError error = new FieldError("turkerResponse", _case + "PolicyOther",
          messageSource.getMessage("mandatory.if.answer", new String[] { "other policy",
              "in the text box next to it" }, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }

    if (turkerResponse.getPolicyJustification(_case) == null
        || turkerResponse.getPolicyJustification(_case).isEmpty()) {
      FieldError error = new FieldError("turkerResponse", _case + "PolicyJustification",
          messageSource.getMessage("mandatory.answer", null, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }
    return isValid;
  }
}
