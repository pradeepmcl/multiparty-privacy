package edu.ncsu.mas.platys.multiparty_privacy.controller;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.ncsu.mas.platys.multiparty_privacy.model.Employee;
import edu.ncsu.mas.platys.multiparty_privacy.model.Scenario;
import edu.ncsu.mas.platys.multiparty_privacy.model.TurkerResponse;
import edu.ncsu.mas.platys.multiparty_privacy.service.EmployeeService;
import edu.ncsu.mas.platys.multiparty_privacy.service.ScenarioService;

@Controller
@RequestMapping("/")
public class AppController {

  @Autowired
  EmployeeService employeeService;

  @Autowired
  ScenarioService scenarioService;

  @Autowired
  MessageSource messageSource;

  Random rand = new Random();

  /*
   * This method will list all existing employees.
   */
  @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
  public String listEmployees(ModelMap model) {

    List<Employee> employees = employeeService.findAllEmployees();
    model.addAttribute("employees", employees);
    return "allemployees";
  }

  /*
   * This method will provide the medium to add a new employee.
   */
  @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
  public String newEmployee(ModelMap model) {
    Employee employee = new Employee();
    model.addAttribute("employee", employee);
    model.addAttribute("edit", false);
    return "registration";
  }

  /*
   * This method will be called on form submission, handling POST request for
   * saving employee in database. It also validates the user input
   */
  @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
  public String saveEmployee(@Valid Employee employee, BindingResult result, ModelMap model) {

    if (result.hasErrors()) {
      return "registration";
    }

    /*
     * Preferred way to achieve uniqueness of field [ssn] should be implementing
     * custom @Unique annotation and applying it on field [ssn] of Model class
     * [Employee].
     * 
     * Below mentioned peace of code [if block] is to demonstrate that you can
     * fill custom errors outside the validation framework as well while still
     * using internationalized messages.
     */
    if (!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
      FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage(
          "non.unique.ssn", new String[] { employee.getSsn() }, Locale.getDefault()));
      result.addError(ssnError);
      return "registration";
    }

    employeeService.saveEmployee(employee);

    model.addAttribute("success", "Employee " + employee.getName() + " registered successfully");
    return "success";
  }

  /*
   * This method will provide the medium to update an existing employee.
   */
  @RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
  public String editEmployee(@PathVariable String ssn, ModelMap model) {
    Employee employee = employeeService.findEmployeeBySsn(ssn);
    model.addAttribute("employee", employee);
    model.addAttribute("edit", true);
    return "registration";
  }

  /*
   * This method will be called on form submission, handling POST request for
   * updating employee in database. It also validates the user input
   */
  @RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
  public String updateEmployee(@Valid Employee employee, BindingResult result, ModelMap model,
      @PathVariable String ssn) {

    if (result.hasErrors()) {
      return "registration";
    }

    if (!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())) {
      FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage(
          "non.unique.ssn", new String[] { employee.getSsn() }, Locale.getDefault()));
      result.addError(ssnError);
      return "registration";
    }

    employeeService.updateEmployee(employee);

    model.addAttribute("success", "Employee " + employee.getName() + " updated successfully");
    return "success";
  }

  /*
   * This method will delete an employee by it's SSN value.
   */
  @RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
  public String deleteEmployee(@PathVariable String ssn) {
    employeeService.deleteEmployeeBySsn(ssn);
    return "redirect:/list";
  }

  /*
   * This method shows the signin page.
   */
  @RequestMapping(value = { "/", "/signin" }, method = RequestMethod.GET)
  public String showSignIn(ModelMap model) {
    model.addAttribute("turker_response", new TurkerResponse());
    return "signin";
  }

  /*
   * This method validates the signin ID amd loads the questionnaire page.
   */
  @RequestMapping(value = { "/questionnaire" }, method = RequestMethod.POST)
  public String showQuestionnaire(
      @ModelAttribute("turker_response") @Valid TurkerResponse turkerResponse,
      BindingResult result, ModelMap model) {
    System.out.println("Here" + turkerResponse.getMturkId() + ", "
        + turkerResponse.getCompletionCode() + ", " + turkerResponse.getPolicyResponse());
    if (isMturkIDValid(turkerResponse.getMturkId())) {
      if (turkerResponse.getCompletionCode() != null) {
        turkerResponse.setResponseTime(new LocalDate());
        /*if (result.hasErrors()) {
          List<FieldError> errors = result.getFieldErrors();
          for (FieldError error : errors) {
            System.out.println(error.toString());
          }*/
        if (turkerResponse.getPolicyResponse() == null) {
          FieldError policyResponseError = new FieldError("turkerResponse", "policyResponse",
              messageSource.getMessage("mandatory.answer", null, Locale.getDefault()));
          result.addError(policyResponseError);
          model.addAttribute("turker_response", turkerResponse);
          return "questionnaire";
        } else {
          turkerResponse.setCompletionCode("COMPLETE" + turkerResponse.getPolicyResponse());
          model.addAttribute("turker_response", turkerResponse);
          return "success" + turkerResponse.getCompletionCode(); // TODO: Add this page
        }
      } else {
        Scenario scenario;
        if (turkerResponse.getScenario() == null) {
          scenario = getARandomScenario();
        } else {
          scenario = scenarioService.findById(turkerResponse.getScenario().getId());
        }
        turkerResponse.setScenario(scenario);
        model.addAttribute("turker_response", turkerResponse);
        // TODO: imageUploader should eventually be part of turker_response
        model.addAttribute("imageUploader", "B");
        return "questionnaire";
      }
    } else {
      return "signin";
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

  @SuppressWarnings("unused")
  private boolean isTurkerResponseValid(TurkerResponse turkerResponse) {
    if (turkerResponse.getPolicyResponse() != null && !turkerResponse.getPolicyResponse().isEmpty()) {
      return true;
    }
    return false;
  }
}
