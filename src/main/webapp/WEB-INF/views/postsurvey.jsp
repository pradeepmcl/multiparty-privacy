<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="resources/favicon.ico">

<title>Multiparty Privacy Study</title>

<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="resources/css/multiparty-privacy.css" rel="stylesheet">

</head>

<body>
 <div class="container">
  <!-- Static navbar -->
  <nav class="navbar navbar-default">
   <div class="container-fluid">
    <div class="navbar-header">
     <button type="button" class="navbar-toggle collapsed"
      data-toggle="collapse" data-target="#navbar" aria-expanded="false"
      aria-controls="navbar">
      <span class="sr-only">Toggle navigation</span> <span
       class="icon-bar"></span> <span class="icon-bar"></span> <span
       class="icon-bar"></span>
     </button>
     <a class="navbar-brand" href="#">Multiparty Privacy Study</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
     <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      <!-- <li><a href="#">Contact</a></li> -->
     </ul>
     <ul class="nav navbar-nav navbar-right">
      <li class="active"><a href="#">MTurk ID: ${postsurveyResponse.mturkId}</a></li>
     </ul>
    </div>
    <!--/.nav-collapse -->
   </div>
   <!--/.container-fluid -->
  </nav>

  <!-- progressbar -->
  <ul id="progressbar">
   <li class="active">Pre-Survey</li>
   <c:forEach begin="1" end="5" varStatus="loop">
    <li class="active">Picture ${loop.index}</li>
   </c:forEach>
   <li class="active">Post-Survey</li>
  </ul>

  <!-- Main component for a primary marketing message or call to action -->
  <form:form method="POST" modelAttribute="postsurveyResponse">
   <form:input type="hidden" path="mturkId" id="mturkId" />
   <form:input type="hidden" path="scenarioBundleId" id="scenarioBundleId" />
   
   <h3>Post-Study Survey</h3>
   <div class="jumbotron lead">
    <p>
     <b>Multiparty privacy conflict</b> refers to a scenario in which a
     user (sharer) shares information concerning multiple stakeholders
     on social media, but one of the stakeholders involved in the
     scenario is not satisfied with the privacy policy the sharer
     applied for the information. Some of the scenarios we showed you
     earlier may have involved conflicts. Imagine similar scenarios
     (involving any type of information) to answer the following
     questions.
    </p>

    <ol>
     <li>
      <h3>Have you ever shared information concerning multiple
       stakeholders on social media (e.g., a picture showing you and
       others)?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="sharingExperience"
         value="yes" /> Yes
        </label> 
       </div>
       <div class="radio">
        <label> <form:radiobutton path="sharingExperience"
          value="no" /> No
        </label>
       </div>
      </div>
     </li>

     <li>
      <h3>Have you ever been involved in a multiparty privacy
       conflict?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="conflictExperience"
          value="yes" /> Yes
        </label> 
       </div>
       <div class="radio">
        <label> <form:radiobutton path="conflictExperience"
          value="no" /> No
        </label>
       </div>
       <div class="radio"> 
        <label> <form:radiobutton path="conflictExperience"
          value="not_sure" /> Not sure
        </label>
       </div>
      </div>
     </li>

     <li>
      <h3>If you were involved in a multiparty privacy conflict,
       how did the conflict start?</h3>
      <p>Note: Choose both if you were involved in both such
       conflicts; neither if you were not involved in a conflict.</p>
      <div class="form-horizontal">
       <div class="checkbox">
        <label> <form:checkbox
          path="conflictExperienceTypeArray" value="self" /> I shared
         information concerning another user, which led to a conflict
        </label>
       </div>
       <div class="checkbox">
        <label> <form:checkbox
          path="conflictExperienceTypeArray" value="other" /> Some user
         shared information concerning me, which led to a conflict
        </label>
       </div>
      </div> 
     </li>
     
     <li>
      <h3>How important do you think the following factors are in
       choosing an appropriate privacy policy for sharing information
       concerning multiple stakeholders on social media?</h3>
      <p>Scale: 1 (not important at all) to 5 (extremely important)</p>
      <p>Note: Here, a preference refers to a stakeholders preferred
       privacy policy and an argument refers to the stakeholder's
       justification for the corresponding preference. An appropriate
       privacy policy is one on which all stakeholders (including the
       sharer) are likely to agree on.</p>
      <div class="form-horizontal">
       <c:set var="factor"
        value="${fn:split('relationshipImportance,sensitivityImportance,sentimentImportance,preferenceImportance,argumentImportance', ',')}" />

       <c:set var="factorDesc"
        value="${fn:split('<b>Relationship</b> between stakeholders,<b>Sensitivity</b> of the information shared,<b>Sentiment</b> of the information shared,<b>Preferences</b> of the stakeholders,<b>Arguments</b> by the stakeholders', ',')}" />

       <c:forEach items="${factor}" var="factor" varStatus="loop">
        <div class="form-group radio">
         <div class="col-sm-5">
          <label>${factorDesc[loop.index]}:</label>
         </div>
         <div class="col-sm-7">
          <label> <form:radiobutton path="${factor}" value="1" />
           1
          </label> <label> <form:radiobutton path="${factor}" value="2" />
           2
          </label> <label> <form:radiobutton path="${factor}" value="3" />
           3
          </label> <label> <form:radiobutton path="${factor}" value="4" />
           4
          </label> <label> <form:radiobutton path="${factor}" value="5" />
           5
          </label>
         </div>
        </div>
       </c:forEach>             
      </div>
     </li>
     
     <li>
      <h3>How confident will you be in choosing an appropriate
       privacy policy for sharing information concerning multiple
       stakeholders on social media in the following cases?</h3>
      <p>Scale: 1 (not confident at all) to 5 (extremely confident)</p>
      <div class="form-horizontal">
       <c:set var="factor"
        value="${fn:split('noPreferenceConfidence,preferenceConfidence,preferenceArgumentConfidence', ',')}" />

       <c:set var="factorDesc"
        value="${fn:split('You <b>do not know</b> stakeholders preferences or arguments;You know stakeholders <b>prefernces</b>, but not their arguments;You know stakeholders <b>prefernces</b> and <b>arguments</b>', ';')}" />

       <c:forEach items="${factor}" var="factor" varStatus="loop">
       <div class="form-group radio">
        <div class="col-sm-8">
         <label>${factorDesc[loop.index]}:</label>
        </div>
        <div class="col-sm-4">
         <label> <form:radiobutton path="${factor}" value="1" />
          1
         </label> <label> <form:radiobutton path="${factor}" value="2" />
          2
         </label> <label> <form:radiobutton path="${factor}" value="3" />
          3
         </label> <label> <form:radiobutton path="${factor}" value="4" />
          4
         </label> <label> <form:radiobutton path="${factor}" value="5" />
          5
         </label>
        </div>
        </div>
       </c:forEach>             
      </div>
     </li>
    </ol>
    
    <div class="has-error">
     <form:errors path="preferenceArgumentConfidence" class="help-inline" />
    </div>
    
   </div>      
   
   <div class="text-center">
    <button type="submit" class="btn btn-primary btn-lg">Submit
     Responses</button>
    <p>
     <br> <b>Note:</b> After submitting the responses, you cannot edit
     them again.
    </p>
   </div>
  </form:form>

 </div>
 <!-- /container -->

 <!-- Bootstrap core JavaScript
    ================================================== -->
 <!-- Placed at the end of the document so the pages load faster -->
 <script
  src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
 <script src="resources/js/bootstrap.min.js"></script>
 <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
 <!-- <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script> -->
</body>
</html>
