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
     We refer to a <b>privacy conflict</b> as a scenario in which a user
     (sharer) shares information concerning multiple users (e.g., a
     picture showing sharer and other users) on social media, but at
     least one of the users involved in the scenario prefers a privacy
     policy different from the one sharer applied for the information
     shared. The scenarios we showed you earlier may have involved
     privacy conflicts. Imagine similar scenarios to answer the
     following questions.
    </p>

    <ol>
     <li>
      <h3>Have you ever shared information concerning multiple
       users on social media?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="sharingExperience"
         value="never" /> Never
        </label> 
       </div>
       <div class="radio">
        <label> <form:radiobutton path="sharingExperience"
         value="few" /> A few times (1&ndash;5)
        </label> 
       </div>
       <div class="radio">
        <label> <form:radiobutton path="sharingExperience"
          value="many" /> Many times (&gt;5)
        </label>
       </div>
       <div class="radio"> 
        <label> <form:radiobutton path="sharingExperience"
          value="not_sure" /> Not sure
        </label>
       </div>
       
      </div>
     </li>

     <li>
      <h3>Have you ever been involved in a privacy conflict?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="conflictExperience"
          value="never" /> Never
        </label> 
       </div>
       <div class="radio">
        <label> <form:radiobutton path="conflictExperience"
          value="few" /> A few times (1&ndash;5)
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="conflictExperience"
          value="many" /> Many times (&gt;5)
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
      <h3>If you were involved in a privacy conflict, how did the
       conflict start?</h3>
      <p>Note: Choose both if you were involved in both such
       conflicts; neither if you were not involved in a conflict.</p>
      <div class="form-horizontal">
       <div class="checkbox">
        <label> <form:checkbox
          path="conflictExperienceTypeArray" value="self" /> I shared
         information concerning another user and that led to a conflict
        </label>
       </div>
       <div class="checkbox">
        <label> <form:checkbox
          path="conflictExperienceTypeArray" value="other" /> Some user
         shared information concerning me and that led to a conflict
        </label>
       </div>
      </div> 
     </li>
     
     <li>
      <h3>How important do you think the following factors are in
       choosing an appropriate privacy policy for sharing information
       concerning multiple users on social media?</h3>
      <p>Scale: 1 (not important at all) to 5 (extremely important)</p>
      <p>Note: An appropriate privacy policy is one which all
       users (including the sharer) are likely to agree on.</p>

      <div class="form-horizontal">
       <c:set var="factor"
        value="${fn:split('relationshipImportance,sensitivityImportance,sentimentImportance', ',')}" />

       <c:set var="factorDesc"
        value="${fn:split('<b>Relationship</b> between stakeholders,<b>Sensitivity</b> of the information shared,<b>Sentiment</b> of the information shared', ',')}" />

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
       users on social media in the following cases?</h3>
      <p>Scale: 1 (not confident at all) to 5 (extremely confident)</p>
      <p>Note: Here, a preference refers to a user's preferred
       privacy policy and an argument refers to the user's justification
       for the corresponding preference.</p>

      <div class="form-horizontal">
       <c:set var="factor"
        value="${fn:split('noPreferenceConfidence,preferenceConfidence,preferenceArgumentConfidence', ',')}" />

       <c:set var="factorDesc"
        value="${fn:split('You <b>do not know</b> stakeholders preferences or arguments;You know users <b>preferences</b>, but not their arguments;You know users <b>preferences</b> and <b>arguments</b>', ';')}" />

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
     
     <li>
      <h3>Apart from the attributes above (relationship,
       sensitivity, sentiment, preferences, and arguments), what other
       attributes do you think can help in deciding an appropriate
       privacy policy to resolve a privacy conflict?</h3> 
      <form:textarea
       path="additionalAttributes" class="form-control" rows="3"
       placeholder="Additional attributes (optional)" />
     </li>
     
     <li>
      <h3>We may conduct additional studies on MTurk as follow-ups
       to this study. If you want to be notified when we post such
       studies, please provide your email below.</h3> 
       <form:input type="text" path="email" class="form-control"
       placeholder="Email (optional)" />
     </li>
     
     <li>
      <h3>Do you have any other comments about the study?</h3> 
       <form:textarea
       path="otherComments" class="form-control" rows="3"
       placeholder="Other comments (optional)" />
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
