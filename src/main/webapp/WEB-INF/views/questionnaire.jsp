<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<link href="resources/css/navbar.css" rel="stylesheet">

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
      <li><a href="#">Contact</a></li>
     </ul>
     <ul class="nav navbar-nav navbar-right">
      <li class="active"><a href="./">MTurk ID:
        ${turker_response.mturkId}</a></li>
     </ul>
    </div>
    <!--/.nav-collapse -->
   </div>
   <!--/.container-fluid -->
  </nav>

  <div class="jumbotron lead">
   <h3>Task</h3>
   <p>Read the picture and its description below and answer the
    following questionnaire.</p>
  </div>

  <div class="jumbotron lead">
   <h3>Picture</h3>
   <p>${scenario.image.imageDescription}</p>
   <img src="resources/images/${scenario.image.name}"
    class="img-responsive center-block" alt="Responsive image">
    <br>
    <p>${scenario.image.sharingDescription}</p>
  </div>

  <!-- <div class="jumbotron lead">
   <h3>Picture sharing</h3>
   <ul class="list-group">
    <li class="list-group-item"><b>A: </b>
     ${scenario.policyA.description}. ${scenario.argumentA.description}</li>
    <li class="list-group-item"><b>B: </b>
     ${scenario.policyB.description}. ${scenario.argumentB.description}</li>
    <li class="list-group-item"><b>C: </b>
     ${scenario.policyC.description}. ${scenario.argumentC.description}</li>
   </ul>
  </div> -->

  <div class="jumbotron lead">
   <h3>Questionnaire</h3>

   <form:form method="POST" modelAttribute="turker_response">
    <form:input type="hidden" path="mturkId" id="mturkId" />
    <form:input type="hidden" path="scenarioId" id="scenarioId" />
    <ol>
     <li>
      <h3>What privacy policy do you think ${scenario.image.sharer}
       should apply to the photograph?</h3>
      <div class="form-horizontal">
       <div class="radio">
        <label> <form:radiobutton path="policy"
          value="a" /> ${scenario.policyA.description}
        </label>
       </div>
       <c:if
        test="${scenario.policyB.description != scenario.policyA.description}">
        <div class="radio">
         <label> <form:radiobutton path="policy"
           value="b" /> ${scenario.policyB.description}
         </label>
        </div>
       </c:if>
       <c:if
        test="${scenario.policyC.description != scenario.policyA.description && 
						    scenario.policyC.description != scenario.policyB.description}">
        <div class="radio">
         <label> <form:radiobutton path="policy"
           value="c" /> ${scenario.policyC.description}
         </label>
        </div>
       </c:if>
       <div class="form-group">
        <div class="col-sm-3 radio">
         <label> <form:radiobutton path="policy"
           value="other" /> Other policy:
         </label>
        </div>
        <div class="col-sm-9">
         <form:input type="text" path="policyOther" class="form-control"
          placeholder="Enter the other policy" />
        </div>
       </div>
       <div class="has-error">
        <form:errors path="policy" class="help-inline" />
        <form:errors path="policyOther" class="help-inline" />
       </div>
      </div>
     </li>

     <li>
      <h3>Why did you choose the above policy?</h3> 
      <form:textarea path="policyJustification" class="form-control" rows="3"
       placeholder="Enter a justification as to why you think the above policy is appropriate for the given picture and context" />
       <div class="has-error">
        <form:errors path="policyJustification" class="help-inline" />
       </div>
     </li>
    </ol>
    <div class="text-center">
     <button type="submit" class="btn btn-primary btn-lg">Submit Responses</button>
    </div>
   </form:form>
  </div>

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
