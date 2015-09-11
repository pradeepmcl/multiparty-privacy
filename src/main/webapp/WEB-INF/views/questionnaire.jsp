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

<link href="resources/css/bootstrap.min.css" rel="stylesheet">
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
      <li class="active"><a href="#">MTurk ID:
        ${picturesurveyResponse.mturkId}</a></li>
     </ul>
    </div>
    <!--/.nav-collapse -->
   </div>
   <!--/.container-fluid -->
  </nav>
  
  <!-- progressbar -->
  <ul id="progressbar">
   <li class="active">Pre-Survey</li>
   <c:forEach begin="0"
    end="${picturesurveyResponse.scenarioBundleIndex}" varStatus="loop">
    <li class="active">Picture ${loop.index + 1}</li>
   </c:forEach>
   <c:forEach begin="${picturesurveyResponse.scenarioBundleIndex + 1}"
    end="4" varStatus="loop">
    <li>Picture ${loop.index + 1}</li>
   </c:forEach>
   <li>Post-Survey</li>
  </ul>

  <h3>Task</h3>
  <div class="jumbotron lead">
   <p>Read the picture and its description below and answer four
    questionnaires that follow. Please answer the questionnaires sequentially.</p>
  </div>

  <h3>Picture and description</h3>
  <div class="jumbotron lead">
   <p>${scenario.image.imageDescription}</p>
   <img src="resources/images/${scenario.image.name}"
    class="img-responsive center-block" alt="Responsive image">
  </div>
  
  <c:set var="stakeholders" value="${fn:split(scenario.image.stakeholders,',')}"/>
  
  <form:form method="POST" modelAttribute="picturesurveyResponse">
   <form:input type="hidden" path="mturkId" id="mturkId" />
   <form:input type="hidden" path="scenarioBundleId" id="scenarioBundleId" />
   <form:input type="hidden" path="scenarioBundleIndex" id="scenarioBundleIndex" />
   <form:input type="hidden" path="scenariosCsv" id="scenariosCsv" />
   <form:input type="hidden" path="scenarioId" id="scenarioId" />
   
   <h3>Questions about the picture</h3>
   <div class="jumbotron lead">
    <p>Read the picture and its description carefully to answer the following questions.</p>
    <ol>
     <li>
      <h3>How sensitive is the picture?</h3>
      <p>Scale: 1 (not sensitive at all) to 5 (extremely sensitive)</p>
      <div class="form-group radio">
       <label> 
        <form:radiobutton path="imageSensitivity" value="1" /> 1
       </label> 
       <label> 
        <form:radiobutton path="imageSensitivity" value="2" /> 2
       </label> 
       <label> 
        <form:radiobutton path="imageSensitivity" value="3" /> 3
       </label> 
       <label> 
        <form:radiobutton path="imageSensitivity" value="4" /> 4
       </label> 
       <label> 
        <form:radiobutton path="imageSensitivity" value="5" /> 5
       </label>
      </div>
     </li>
     
     <li>
      <h3>What is the sentiment of the people in the picture?</h3>
      <p>Scale: 1 (extremely positive) to 5 (extremely negative)</p>
      <div class="form-group radio">
       <label> 
        <form:radiobutton path="imageSentiment" value="1" /> 1
       </label> 
       <label> 
        <form:radiobutton path="imageSentiment" value="2" /> 2
       </label> 
       <label> 
        <form:radiobutton path="imageSentiment" value="3" /> 3
       </label> 
       <label> 
        <form:radiobutton path="imageSentiment" value="4" /> 4
       </label> 
       <label> 
        <form:radiobutton path="imageSentiment" value="5" /> 5
       </label>
      </div>
     </li>
     
     <li>
      <h3>What is the relationship between ${stakeholders[0]},
       ${stakeholders[1]}, and ${stakeholders[2]}?</h3>
       <label class="radio-inline"> 
        <form:radiobutton path="imageRelationship" value="family" />
        Family
       </label> 
       <label class="radio-inline"> 
        <form:radiobutton path="imageRelationship" value="friends" /> 
        Friends
       </label> 
       <label class="radio-inline">
        <form:radiobutton path="imageRelationship" value="colleagues" />
        Colleagues
       </label>
     </li>

     <li>
      <h3>How many people are in the picture?</h3> 
       <label class="radio-inline"> 
        <form:radiobutton path="imagePeopleCount" value="three" />
        Three
       </label> 
       <label class="radio-inline"> 
        <form:radiobutton path="imagePeopleCount" value="five" /> 
        Five
       </label> 
       <label class="radio-inline">
        <form:radiobutton path="imagePeopleCount" value="ten_plus" />
        Ten or more
       </label>
     </li>
    </ol>
    <div class="has-error">
     <form:errors path="imagePeopleCount" class="help-inline" />
    </div>
   </div>
   
   <h3>Questions about sharing scenario 1</h3>
   <div class="jumbotron lead">
    <p>Imagine that ${scenario.image.sharingDescription}</p>
    <ol>
     <li>
      <h3>What privacy policy do you think ${scenario.image.sharer}
       should apply to the picture?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="case1Policy" value="a" />
         Share with all (anyone on or off social media can see the
         picture).
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="case1Policy" value="b" />
         Share with common friends (only common friends of
         ${stakeholders[0]}, ${stakeholders[1]}, and ${stakeholders[2]}
         can see the picture).
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="case1Policy" value="c" />
         Share among themselves (only ${stakeholders[0]},
         ${stakeholders[1]}, and ${stakeholders[2]} can see the picture)
        </label>
       </div>
       <div class="form-group">
        <div class="col-sm-3 radio">
         <label> <form:radiobutton path="case1Policy" value="other" />
          Other policy:
         </label>
        </div>
        <div class="col-sm-9">
         <form:input type="text" path="case1PolicyOther" class="form-control"
          placeholder="Enter the other policy" />
        </div>
       </div>
       <div class="has-error">
        <form:errors path="case1Policy" class="help-inline" />
        <form:errors path="case1PolicyOther" class="help-inline" />
       </div>
      </div>
     </li>

     <li>
      <h3>Why did you choose the above policy?</h3> <form:textarea
       path="case1PolicyJustification" class="form-control" rows="3"
       placeholder="Enter a justification as to why you think the above policy is appropriate for the given picture and context" />
      <div class="has-error">
       <form:errors path="case1PolicyJustification" class="help-inline" />
      </div>
     </li>
    </ol>
   </div>

   <h3>Questions about sharing scenario 2</h3>
   <div class="jumbotron lead">
    <p>Again, imagine that ${scenario.image.sharingDescription} Now,
     unlike the scenario above, ${stakeholders[0]}, ${stakeholders[1]},
     and ${stakeholders[2]} express their preferred privacy policy for
     the picture as follows.</p>
    <ul class="list-group">
     <li class="list-group-item"><b>${stakeholders[0]}: </b>
      ${scenario.policyA.description}</li>
     <li class="list-group-item"><b>${stakeholders[1]}: </b>
      ${scenario.policyB.description}</li>
     <li class="list-group-item"><b>${stakeholders[2]}: </b>
      ${scenario.policyC.description}</li>       
    </ul>
     
    <ol>
     <li>
      <h3>What privacy policy do you think ${scenario.image.sharer}
       should apply to the picture?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="case2Policy" value="a" />
         Share with all (anyone on or off social media can see the
         picture).
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="case2Policy" value="b" />
         Share with common friends (only common friends of
         ${stakeholders[0]}, ${stakeholders[1]}, and ${stakeholders[2]}
         can see the picture).
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="case2Policy" value="c" />
         Share among themselves (only ${stakeholders[0]},
         ${stakeholders[1]}, and ${stakeholders[2]} can see the picture)
        </label>
       </div>
       <div class="form-group">
        <div class="col-sm-3 radio">
         <label> <form:radiobutton path="case2Policy" value="other" />
          Other policy:
         </label>
        </div>
        <div class="col-sm-9">
         <form:input type="text" path="case2PolicyOther" class="form-control"
          placeholder="Enter the other policy" />
        </div>
       </div>
       <div class="has-error">
        <form:errors path="case2Policy" class="help-inline" />
        <form:errors path="case2PolicyOther" class="help-inline" />
       </div>
      </div>
     </li>

     <li>
      <h3>Why did you choose the above policy?</h3> <form:textarea
       path="case2PolicyJustification" class="form-control" rows="3"
       placeholder="Enter a justification as to why you think the above policy is appropriate for the given picture and context" />
      <div class="has-error">
       <form:errors path="case2PolicyJustification" class="help-inline" />
      </div>
     </li>
    </ol>
   </div>
      
   <h3>Questions about sharing scenario 3</h3>
   <div class="jumbotron lead">
    <p>Again, imagine that ${scenario.image.sharingDescription} Now,
     unlike the scenario above, ${stakeholders[0]}, ${stakeholders[1]},
     and ${stakeholders[2]} not only express a preffered privacy policy,
     but also provide a justification for their preference as follows.</p>
    <ul class="list-group">
     <li class="list-group-item"><b>${stakeholders[0]}: </b>
      ${scenario.argumentA.description} ${scenario.policyA.description}</li>
     <li class="list-group-item"><b>${stakeholders[1]}: </b>
      ${scenario.argumentB.description} ${scenario.policyB.description}</li>
     <li class="list-group-item"><b>${stakeholders[2]}: </b>
      ${scenario.argumentC.description} ${scenario.policyC.description}</li>       
    </ul>
     
    <ol>
     <li>
      <h3>What privacy policy do you think ${scenario.image.sharer}
       should apply to the picture?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="case3Policy" value="a" />
         Share with all (anyone on or off social media can see the
         picture).
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="case3Policy" value="b" />
         Share with common friends (only common friends of
         ${stakeholders[0]}, ${stakeholders[1]}, and ${stakeholders[2]}
         can see the picture).
        </label>
       </div>
       <div class="radio">
        <label> <form:radiobutton path="case3Policy" value="c" />
         Share among themselves (only ${stakeholders[0]},
         ${stakeholders[1]}, and ${stakeholders[2]} can see the picture)
        </label>
       </div>
       <div class="form-group">
        <div class="col-sm-3 radio">
         <label> <form:radiobutton path="case3Policy" value="other" />
          Other policy:
         </label>
        </div>
        <div class="col-sm-9">
         <form:input type="text" path="case3PolicyOther" class="form-control"
          placeholder="Enter the other policy" />
        </div>
       </div>
       <div class="has-error">
        <form:errors path="case3Policy" class="help-inline" />
        <form:errors path="case3PolicyOther" class="help-inline" />
       </div>
      </div>
     </li>

     <li>
      <h3>Why did you choose the above policy?</h3> <form:textarea
       path="case3PolicyJustification" class="form-control" rows="3"
       placeholder="Enter a justification as to why you think the above policy is appropriate for the given picture and context" />
      <div class="has-error">
       <form:errors path="case3PolicyJustification" class="help-inline" />
      </div>
     </li>
    </ol>
   </div>

   <div class="text-center">
    <button type="submit" class="btn btn-primary btn-lg">Submit
     Responses &raquo;</button>
    <p>
     <br> <b>Note:</b> After submitting the responses, you cannot
     edit them again.
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
 
 <script type="text/javascript">
  $('button:submit').click(function() {
    $('button:submit').attr("disabled", true);
    $('form').submit();
  });
 </script>
</body>
</html>
