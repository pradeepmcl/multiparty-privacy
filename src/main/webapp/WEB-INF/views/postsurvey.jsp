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
      <li><a href="#">Contact</a></li>
     </ul>
     <ul class="nav navbar-nav navbar-right">
      <li class="active"><a href="./">MTurk ID: ${postsurveyResponse.mturkId}</a></li>
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
  <div class="jumbotron lead">
   <p>This is a post-survey</p>
  </div>

  <form:form method="POST" modelAttribute="postsurveyResponse">
   <form:input type="hidden" path="mturkId" id="mturkId" />
   <form:input type="hidden" path="scenarioBundleId" id="scenarioBundleId" />
   
   <h3>Post-Study Survey</h3>
   <div class="jumbotron lead">
    <ol>
     <li>
      <h3>TODO</h3>
     </li>
    </ol>
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
