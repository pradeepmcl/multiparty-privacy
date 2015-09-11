package edu.ncsu.mas.platys.multiparty_privacy.controller;

import java.util.Locale;

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
  private static final String PAGE_SIGNIN_FAILURE = "signin_failure";
  private static final String PAGE_REDIRECT_SIGNIN_FAILURE = "redirect:signin_failure";
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
  private static final String ATTR_SIGN_FAILURE_REASON = "signinFailureReason";
  private static final String ATTR_PRESURVEY_RESPONSE = "presurveyResponse";
  private static final String ATTR_PICTURESURVEY_RESPONSE = "picturesurveyResponse";
  private static final String ATTR_POSTSURVEY_RESPONSE = "postsurveyResponse";
  private static final String ATTR_SCENARIO = "scenario";
  private static final String ATTR_SCENARIO_BUNDLE_ID =  "scenarioBundleId";
  private static final String ATTR_COMPLETION_CODE =  "completionCode";
  
  private static final int MTURK_ID_VALID = 0;
  private static final int MTURK_ID_INVALID = 1;
  private static final int MTURK_ID_COMPLETED = 2;
  
  private static final int MIN_JUSTIFICATION_LENGTH = 25;
  
  private int nextBundleId = 1;
  
  private final RandomCodeGenerator randCodeGen = new RandomCodeGenerator(8);

  @RequestMapping(value = { "/", "/" + PAGE_SIGNIN }, method = RequestMethod.GET)
  public String showSignIn(ModelMap model) {
    model.addAttribute(ATTR_TURKER, new Turker());
    return PAGE_SIGNIN;
  }

  @RequestMapping(value = { "/", "/" + PAGE_SIGNIN }, method = RequestMethod.POST)
  public String processSignIn(@ModelAttribute(ATTR_TURKER) Turker turker,
      final RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, turker.getMturkId());
    switch (isMturkIDValid(turker.getMturkId())) {
    case MTURK_ID_VALID:
      return PAGE_REDIRECT_PRESURVEY;
    case MTURK_ID_COMPLETED:
      redirectAttributes.addFlashAttribute(ATTR_SIGN_FAILURE_REASON,
          "You have already submitted a response in this batch "
              + "(you can only submit one response per batch).");
      return PAGE_REDIRECT_SIGNIN_FAILURE;
    case MTURK_ID_INVALID:
      redirectAttributes.addFlashAttribute(ATTR_SIGN_FAILURE_REASON, "Your MTurk ID is invalid.");
      return PAGE_REDIRECT_SIGNIN_FAILURE;
    default:
      redirectAttributes.addFlashAttribute(ATTR_SIGN_FAILURE_REASON, "An unknown error occurred.");
      return PAGE_REDIRECT_SIGNIN_FAILURE;
    }
  }

  @RequestMapping(value = { "/" + PAGE_SIGNIN_FAILURE }, method = RequestMethod.GET)
  public String showSigninFailure(@ModelAttribute(ATTR_MTURK_ID) String mturkId,
      @ModelAttribute(ATTR_SIGN_FAILURE_REASON) String signinFailureReason, BindingResult result,
      ModelMap model) {
    model.addAttribute(ATTR_MTURK_ID, mturkId);
    model.addAttribute(ATTR_SIGN_FAILURE_REASON, signinFailureReason);
    return PAGE_SIGNIN_FAILURE;
  }

  @RequestMapping(value = { "/" + PAGE_PRESURVEY }, method = RequestMethod.GET)
  public String showPresurvey(@ModelAttribute(ATTR_MTURK_ID) String mturkId, BindingResult result,
      ModelMap model) {
    TurkerPresurveyResponse presurveyResponse = new TurkerPresurveyResponse();
    presurveyResponse.setMturkId(mturkId);
    model.addAttribute(ATTR_PRESURVEY_RESPONSE, presurveyResponse);
    return PAGE_PRESURVEY;
  }
  
  @RequestMapping(value = { "/" + PAGE_PRESURVEY }, method = RequestMethod.POST)
  public String processPresurveyResponse(
      @ModelAttribute(ATTR_PRESURVEY_RESPONSE) TurkerPresurveyResponse presurveyResponse,
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
    model.addAttribute(ATTR_PICTURESURVEY_RESPONSE, turkerResponse);

    return PAGE_QUESTIONNAIRE;
  }
  
  @RequestMapping(value = { "/" + PAGE_QUESTIONNAIRE }, method = RequestMethod.POST)
  public String processQuestionnaireResponse(
      @ModelAttribute(ATTR_PICTURESURVEY_RESPONSE) TurkerPicturesurveyResponse turkerResponse,
      BindingResult result, ModelMap model, final RedirectAttributes redirectAttributes) {
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
        model.addAttribute(ATTR_PICTURESURVEY_RESPONSE, turkerResponse);
        
        return PAGE_QUESTIONNAIRE;
      } else {
        // Last response
        redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, turkerResponse.getMturkId());
        redirectAttributes.addFlashAttribute(ATTR_SCENARIO_BUNDLE_ID,
            turkerResponse.getScenarioBundleId());
        return PAGE_REDIRECT_POSTSURVEY;
      }
    } else {
      // An invalid response was submitted, go back
      Scenario scenario = scenarioService.findById(turkerResponse.getScenarioId());
      model.addAttribute(ATTR_PICTURESURVEY_RESPONSE, turkerResponse);
      model.addAttribute(ATTR_SCENARIO, scenario);
      return PAGE_QUESTIONNAIRE;
    }
  }

  @RequestMapping(value = { "/" + PAGE_POSTSURVEY }, method = RequestMethod.GET)
  public String showPostsurvey(@ModelAttribute(ATTR_MTURK_ID) String mturkId,
      @ModelAttribute(ATTR_SCENARIO_BUNDLE_ID) int scenarioBundleId, BindingResult result,
      ModelMap model) {
    TurkerPostsurveyResponse postsurveyResponse = new TurkerPostsurveyResponse();
    postsurveyResponse.setMturkId(mturkId);
    postsurveyResponse.setScenarioBundleId(scenarioBundleId);
    model.addAttribute(ATTR_POSTSURVEY_RESPONSE, postsurveyResponse);
    return PAGE_POSTSURVEY;
  }
    
  // TODO Remove /test-postsurvey
  @RequestMapping(value = { "/" + PAGE_POSTSURVEY, "/test-postsurvey" }, method = RequestMethod.POST)
  public String processPostsurveyResponse(
      @ModelAttribute(ATTR_POSTSURVEY_RESPONSE) TurkerPostsurveyResponse postsurveyResponse,
      BindingResult result, ModelMap model, final RedirectAttributes redirectAttributes) {
    if (isTurkerPostsurveyResponseValid(postsurveyResponse, result, model)) {
      postsurveyResponse.setResponseTime(LocalDateTime.now());
      postsurveyResponse.setCompletionCode(randCodeGen.nextString());
      if (postsurveyResponse.getConflictExperienceTypeArray().length > 0) {
        StringBuffer conflictAllTypes = new StringBuffer();
        for (String conflictType : postsurveyResponse.getConflictExperienceTypeArray()) {
          conflictAllTypes.append(conflictType + ",");
        }
        postsurveyResponse.setConflictExperienceType(conflictAllTypes.replace(
            conflictAllTypes.length() - 1, conflictAllTypes.length(), "").toString());
      }
      postsurveyResponseService.saveResponse(postsurveyResponse);
      
      // Update complete count for the bundle. This is probably redundant,
      // though. Consider getting rid of the num_completed column.
      scenarioBundleService.incrementNumCompleted(postsurveyResponse.getScenarioBundleId());

      redirectAttributes.addFlashAttribute(ATTR_MTURK_ID, postsurveyResponse.getMturkId());
      redirectAttributes.addFlashAttribute(ATTR_COMPLETION_CODE,
          postsurveyResponse.getCompletionCode());
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

  // TODO: This method is for testing only
  @RequestMapping(value = { "/test-postsurvey" }, method = RequestMethod.GET)
  public String showPostsurveyForTesting(ModelMap model) {
    TurkerPostsurveyResponse postsurveyResponse = new TurkerPostsurveyResponse();
    postsurveyResponse.setMturkId("TestingID");
    postsurveyResponse.setScenarioBundleId(1);
    model.addAttribute(ATTR_POSTSURVEY_RESPONSE, postsurveyResponse);
    return PAGE_POSTSURVEY;
  }
  
  private ScenarioBundle getScenarioBundle() {
    long numBundles = scenarioBundleService.getCount();
    if (nextBundleId > numBundles) {
      nextBundleId = 1;
    }
    return scenarioBundleService.findById(nextBundleId++);
  }

  // This is some reusable code
  /*
   * if (result.hasErrors()) {
   * List<FieldError> errors = result.getFieldErrors();
   * for (FieldError error : errors) {
   * System.out.println(error.toString());
   * }
   */
  
  private int isMturkIDValid(String mturkID) {
    // Could not find much information on the Mturk ID specification. The length
    // 3 has been chosen intuitively.
    if (mturkID == null || mturkID.trim().length() <= 3) {
      return MTURK_ID_INVALID;
    } else if (mturkID.equals("pmuruka") || mturkID.equals("rlopezf")) {
      // Make exception for some IDs; we use these for testing
      return MTURK_ID_VALID;
    } else if (postsurveyResponseService.getResponseCount(mturkID) > 0) {
      return MTURK_ID_COMPLETED;
    }
    return MTURK_ID_VALID;
  }

  private boolean isTurkerPresurveyResponseValid(TurkerPresurveyResponse presurveyResponse,
      BindingResult result, ModelMap model) {
    if (presurveyResponse.getGender() == null || presurveyResponse.getAge() == null
        || presurveyResponse.getEducation() == null
        || presurveyResponse.getSocialmediaFrequency() == null
        || presurveyResponse.getSharingFrequency() == null) {
      FieldError error = new FieldError(ATTR_PRESURVEY_RESPONSE, "education",
          messageSource.getMessage("mandatory.answers", new String[] { "above" },
              Locale.getDefault()));
      result.addError(error);
      return false;
    }
    return true;
  }

  private boolean isTurkerResponseValid(TurkerPicturesurveyResponse picsurveyResponse,
      BindingResult result, ModelMap model) {
    boolean isValid = true;

    isValid = validateTurkerResponseForImageQuestions(picsurveyResponse, result, isValid);
    isValid = validateTurkerResponseForCase(picsurveyResponse, result, "case1", isValid);
    isValid = validateTurkerResponseForCase(picsurveyResponse, result, "case2", isValid);
    isValid = validateTurkerResponseForCase(picsurveyResponse, result, "case3", isValid);

    return isValid;
  }

  private boolean validateTurkerResponseForImageQuestions(
      TurkerPicturesurveyResponse picsurveyResponse, BindingResult result, boolean isValid) {
    if (picsurveyResponse.getImageSensitivity() == null
        || picsurveyResponse.getImageSentiment() == null
        || picsurveyResponse.getImageRelationship() == null
        || picsurveyResponse.getImagePeopleCount() == null) {
      FieldError error = new FieldError(ATTR_PICTURESURVEY_RESPONSE, "imagePeopleCount",
          messageSource.getMessage("mandatory.answers", new String[] { "above" },
              Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }
    return isValid;
  }

  private boolean validateTurkerResponseForCase(TurkerPicturesurveyResponse picsurveyResponse,
      BindingResult result, String _case, boolean isValid) {
    if (picsurveyResponse.getPolicy(_case) == null) {
      FieldError error = new FieldError(ATTR_PICTURESURVEY_RESPONSE, _case + "Policy",
          messageSource.getMessage("mandatory.answer", null, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    } else if (picsurveyResponse.getPolicy(_case).equals("other")
        && (picsurveyResponse.getPolicyOther(_case) == null || picsurveyResponse.getPolicyOther(
            _case).isEmpty())) {
      FieldError error = new FieldError(ATTR_PICTURESURVEY_RESPONSE, _case + "PolicyOther",
          messageSource.getMessage("mandatory.if.answer", new String[] { "other policy",
              "in the text box next to it" }, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }

    if (picsurveyResponse.getPolicyJustification(_case) == null
        || picsurveyResponse.getPolicyJustification(_case).isEmpty()) {
      FieldError error = new FieldError(ATTR_PICTURESURVEY_RESPONSE, _case + "PolicyJustification",
          messageSource.getMessage("mandatory.answer", null, Locale.getDefault()));
      result.addError(error);
      isValid = false;
    } else if (picsurveyResponse.getPolicyJustification(_case).trim().length() < MIN_JUSTIFICATION_LENGTH) {
      FieldError error = new FieldError(ATTR_PICTURESURVEY_RESPONSE, _case + "PolicyJustification",
          messageSource.getMessage("short.answer", new String[] { "25 characters" },
              Locale.getDefault()));
      result.addError(error);
      isValid = false;
    }

    return isValid;
  }
  
  private boolean isTurkerPostsurveyResponseValid(TurkerPostsurveyResponse postsurveyResponse,
      BindingResult result, ModelMap model) {
    // conflictExperienceType (checkbox) can be null
    if (postsurveyResponse.getSharingExperience() == null
        || postsurveyResponse.getConflictExperience() == null
        || postsurveyResponse.getRelationshipImportance() == null
        || postsurveyResponse.getSensitivityImportance() == null
        || postsurveyResponse.getSentimentImportance() == null
        || postsurveyResponse.getNoPreferenceConfidence() == null
        || postsurveyResponse.getPreferenceConfidence() == null
        || postsurveyResponse.getPreferenceArgumentConfidence() == null) {
      FieldError error = new FieldError(ATTR_POSTSURVEY_RESPONSE, "preferenceArgumentConfidence",
          messageSource.getMessage("mandatory.answers", new String[] { "above" },
              Locale.getDefault()));
      result.addError(error);
      return false;
    }
    return true;
  }
}
