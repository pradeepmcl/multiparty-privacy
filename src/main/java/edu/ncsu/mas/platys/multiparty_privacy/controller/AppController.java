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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;
import edu.ncsu.mas.platys.multiparty_privacy.model.ScenarioBundle;
import edu.ncsu.mas.platys.multiparty_privacy.model.Turker;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPostsurveyResponse;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPresurveyResponse;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerPicturesurveyResponse;
import edu.ncsu.mas.platys.multiparty_privacy.service.ScenarioBundleService;
import edu.ncsu.mas.platys.multiparty_privacy.service.ScenarioService;
import edu.ncsu.mas.platys.multiparty_privacy.service.TurkerPostsurveyResponseService;
import edu.ncsu.mas.platys.multiparty_privacy.service.TurkerPresurveyResponseService;
import edu.ncsu.mas.platys.multiparty_privacy.service.TurkerPicturesurveyResponseService;
import edu.ncsu.mas.platys.multiparty_privacy.util.RandomCodeGenerator;

@Controller
@RequestMapping("/")
public class AppController {

  @Autowired
  ScenarioService scenarioService;

  @Autowired
  ScenarioBundleService scenarioBundleService;

  @Autowired
  TurkerPicturesurveyResponseService pictureResponseService;
  
  @Autowired
  TurkerPresurveyResponseService presurveyResponseService;

  @Autowired
  TurkerPostsurveyResponseService postsurveyResponseService;
  
  @Autowired
  MessageSource messageSource;

  // Changing this variable requires that the db be updated accordingly
  private static final int MAX_SCENARIOS = 5;
  
  private static final String PAGE_SIGNIN = "signin";
  private static final String PAGE_SIGNIN_FAILURE = "signin_failure"; // TODO
  private static final String PAGE_PRESURVEY = "presurvey";
  private static final String PAGE_REDIRECT_PRESURVEY = "redirect:presurvey";
  private static final String PAGE_QUESTIONNAIRE = "questionnaire";
  private static final String PAGE_REDIRECT_QUESTIONNAIRE = "redirect:questionnaire";
  private static final String PAGE_POSTSURVEY = "postsurvey";
  private static final String PAGE_REDIRECT_POSTSURVEY = "redirect:postsurvey";
  private static final String PAGE_SUCCESS = "success";
  private static final String PAGE_REDIRECT_SUCCESS = "redirect:success";
  
  private static final String ATTR_TURKER = "turker";
  private static final String ATTR_MTURK_ID = "mturkId";
  private static final String ATTR_TURKER_PRESURVEY_RESPONSE = "presurveyResponse";
  private static final String ATTR_TURKER_RESPONSE = "picturesurveyResponse";
  private static final String ATTR_TURKER_POSTSURVEY_RESPONSE = "postsurveyResponse";
  private static final String ATTR_SCENARIO = "scenario";
  private static final String ATTR_COMPLETION_CODE =  "completionCode";
  
  Random rand = new Random();
  
  RandomCodeGenerator randCodeGen = new RandomCodeGenerator(8);

  @RequestMapping(value = { "/", "/" + PAGE_SIGNIN }, method = RequestMethod.GET)
  public String showSignIn(ModelMap model) {
    model.addAttribute(ATTR_TURKER, new Turker());
    return PAGE_SIGNIN;
  }

  @RequestMapping(value = { "/", "/" + PAGE_SIGNIN }, method = RequestMethod.POST)
  public String processSignIn(@ModelAttribute(ATTR_TURKER) Turker turker,
      final RedirectAttributes redirectAttributes) {
    if (isMturkIDValid(turker.getMturkId())) {
      redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, turker.getMturkId());
      return PAGE_REDIRECT_PRESURVEY;
    } else {
      return PAGE_SIGNIN_FAILURE;
    }
  }

  @RequestMapping(value = { "/" + PAGE_PRESURVEY }, method = RequestMethod.GET)
  public String showPresurvey(@ModelAttribute(ATTR_MTURK_ID) String mturkId, BindingResult result,
      ModelMap model) {
    TurkerPresurveyResponse presurveyResponse = new TurkerPresurveyResponse();
    presurveyResponse.setMturkId(mturkId);
    model.addAttribute(ATTR_TURKER_PRESURVEY_RESPONSE, presurveyResponse);
    return PAGE_PRESURVEY;
  }
  
  @RequestMapping(value = { "/" + PAGE_PRESURVEY }, method = RequestMethod.POST)
  public String processPresurveyResponse(
      @ModelAttribute(ATTR_TURKER_PRESURVEY_RESPONSE) TurkerPresurveyResponse presurveyResponse,
      BindingResult result, ModelMap model, final RedirectAttributes redirectAttributes) {
    if (isTurkerPresurveyResponseValid(presurveyResponse, result, model)) {
      presurveyResponse.setResponseTime(LocalDateTime.now());
      presurveyResponseService.saveResponse(presurveyResponse);
      
      redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, presurveyResponse.getMturkId());
      return PAGE_REDIRECT_QUESTIONNAIRE;
    } else {
      // Page has errors
      return PAGE_PRESURVEY;
    }
  }

  @RequestMapping(value = { "/" + PAGE_QUESTIONNAIRE }, method = RequestMethod.GET)
  public String showQuestionnaire(@ModelAttribute(ATTR_MTURK_ID) String mturkId,
      BindingResult result, ModelMap model) {
    ScenarioBundle bundle = getScenarioBundle();
    String[] scenarios = bundle.getScenariosCsv().split(",");
    int scenarioId = Integer.parseInt(scenarios[0]);
    Scenario scenario = scenarioService.findById(scenarioId);
    model.addAttribute(ATTR_SCENARIO, scenario);

    TurkerPicturesurveyResponse turkerResponse = new TurkerPicturesurveyResponse();
    turkerResponse.setMturkId(mturkId);
    turkerResponse.setScenarioBundleId(bundle.getId());
    turkerResponse.setScenarioBundleIndex(0);
    turkerResponse.setScenariosCsv(bundle.getScenariosCsv());
    turkerResponse.setScenarioId(scenarioId);
    model.addAttribute(ATTR_TURKER_RESPONSE, turkerResponse);

    return PAGE_QUESTIONNAIRE;
  }
  
  @RequestMapping(value = { "/" + PAGE_QUESTIONNAIRE }, method = RequestMethod.POST)
  public String processQuestionnaireResponse(
      @ModelAttribute(ATTR_TURKER_RESPONSE) TurkerPicturesurveyResponse turkerResponse, BindingResult result,
      ModelMap model, final RedirectAttributes redirectAttributes) {
    if (isTurkerResponseValid(turkerResponse, result, model)) {
      // A valid response was submitted
      turkerResponse.setResponseTime(LocalDateTime.now());
      pictureResponseService.saveResponse(turkerResponse);

      int scenarioIndex = turkerResponse.getScenarioBundleIndex() + 1;
      if (scenarioIndex < MAX_SCENARIOS) {
        // Show next scenario
        String scenarioCsv = turkerResponse.getScenariosCsv();
        String[] scenarios = scenarioCsv.split(",");
        int scenarioId = Integer.parseInt(scenarios[scenarioIndex]);
        Scenario scenario = scenarioService.findById(scenarioId);
        model.addAttribute(ATTR_SCENARIO, scenario);
        
        turkerResponse.resetResponse();
        turkerResponse.setScenarioId(scenarioId);
        turkerResponse.setScenarioBundleIndex(scenarioIndex);
        model.addAttribute(ATTR_TURKER_RESPONSE, turkerResponse);
        
        return PAGE_QUESTIONNAIRE;
      } else {
        // Last response
        redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, turkerResponse.getMturkId());
        return PAGE_REDIRECT_POSTSURVEY;
      }
    } else {
      // An invalid response was submitted, go back
      Scenario scenario = scenarioService.findById(turkerResponse.getScenarioId());
      model.addAttribute(ATTR_TURKER_RESPONSE, turkerResponse);
      model.addAttribute(ATTR_SCENARIO, scenario);
      return PAGE_QUESTIONNAIRE;
    }
  }

  @RequestMapping(value = { "/" + PAGE_POSTSURVEY }, method = RequestMethod.GET)
  public String showPostsurvey(@ModelAttribute(ATTR_MTURK_ID) String mturkId, BindingResult result,
      ModelMap model) {
    TurkerPostsurveyResponse postsurveyResponse = new TurkerPostsurveyResponse();
    postsurveyResponse.setMturkId(mturkId);
    model.addAttribute(ATTR_TURKER_POSTSURVEY_RESPONSE, postsurveyResponse);
    return PAGE_POSTSURVEY;
  }
    
  @RequestMapping(value = { "/" + PAGE_POSTSURVEY }, method = RequestMethod.POST)
  public String processPostsurveyResponse(
      @ModelAttribute(ATTR_TURKER_POSTSURVEY_RESPONSE) TurkerPostsurveyResponse postsurveyResponse,
      BindingResult result, ModelMap model, final RedirectAttributes redirectAttributes) {
    if (isTurkerPostsurveyResponseValid(postsurveyResponse, result, model)) {
      postsurveyResponse.setResponseTime(LocalDateTime.now());
      postsurveyResponse.setCompletionCode(randCodeGen.nextString());
      postsurveyResponseService.saveResponse(postsurveyResponse);

      redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, postsurveyResponse.getMturkId());
      redirectAttributes.addFlashAttribute(ATTR_COMPLETION_CODE, randCodeGen.nextString());
      return PAGE_REDIRECT_SUCCESS;
    } else {
      // Page has errors
      return PAGE_POSTSURVEY;
    }
  }

  @RequestMapping(value = { "/" + PAGE_SUCCESS }, method = RequestMethod.GET)
  public String showSuccess(@ModelAttribute(ATTR_MTURK_ID) String mturkId,
      @ModelAttribute(ATTR_COMPLETION_CODE) String completionCode, BindingResult result,
      ModelMap model) {
    model.addAttribute(ATTR_MTURK_ID, mturkId);
    model.addAttribute(ATTR_COMPLETION_CODE, completionCode);
    return PAGE_SUCCESS;
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
  
  private boolean isMturkIDValid(String mturkID) {
    if (mturkID != null && !mturkID.isEmpty()) {
      // TODO: Check that the user does not exceed permitted number of HIT
      // responses.
      return true;
    }
    return false;
  }

  private boolean isTurkerPresurveyResponseValid(TurkerPresurveyResponse presurveyResponse,
      BindingResult result, ModelMap model) {
    if (presurveyResponse.getGender() == null || presurveyResponse.getAge() == null
        || presurveyResponse.getEducation() == null) {
      FieldError error = new FieldError("presurveyResponse", "education", messageSource.getMessage(
          "mandatory.answers", new String[] { "above" }, Locale.getDefault()));
      result.addError(error);
      return false;
    }
    return true;
  }

  private boolean isTurkerResponseValid(TurkerPicturesurveyResponse turkerResponse, BindingResult result,
      ModelMap model) {
    boolean isValid = true;

    isValid = validateTurkerResponseForImageQuestions(turkerResponse, result, isValid);
    isValid = validateTurkerResponseForCase(turkerResponse, result, "case1", isValid);
    isValid = validateTurkerResponseForCase(turkerResponse, result, "case2", isValid);

    return isValid;
  }

  private boolean validateTurkerResponseForImageQuestions(TurkerPicturesurveyResponse turkerResponse,
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

  private boolean validateTurkerResponseForCase(TurkerPicturesurveyResponse turkerResponse,
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
  
  private boolean isTurkerPostsurveyResponseValid(TurkerPostsurveyResponse postsurveyResponse,
      BindingResult result, ModelMap model) {
    return true; // TODO
  }

}
