<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link href="resources/css/navbar.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<!-- <script src="../../assets/js/ie-emulation-modes-warning.js"></script> -->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
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
						<li class="active"><a href="./">MTurk ID: ${mturkID}</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</nav>



		<!-- Main component for a primary marketing message or call to action -->
		<div class="jumbotron lead">
		  <h3>Task</h3>
			<p>Read the picture and its description below and answer the
				following questionnaire...</p>
		</div>

    <div class="jumbotron lead">
			<h3>Picture</h3>
		  <p class="lead">${scenario.image.description}</p>
			<p class="lead">${imageOwner} owns the camera in which the picture
				was taken...</p>
			<img src="resources/images/${scenario.image.name}"
        class="img-responsive center-block" alt="Responsive image">
				
		</div>
				
		<div class="jumbotron lead">
		  <h3>Privacy Policies</h3>
			<p>Now, ${imageUploader} decides to upload the picture to
				Facebook. A, B, C argue about an appropriate privacy policy for the
				picture. The following are their arguments...</p>

			<ul class="list-group">
				<li class="list-group-item">
          <b>A: </b> ${scenario.policyA.description}. ${scenario.argumentA.description}
        </li>
        <li class="list-group-item">
          <b>B: </b> ${scenario.policyB.description}. ${scenario.argumentB.description}
        </li>
        <li class="list-group-item">
          <b>C: </b> ${scenario.policyC.description}. ${scenario.argumentC.description}
        </li>
			</ul>
		</div>

    <div class="jumbotron lead">
			<h3>Questionnaire</h3>
			<ol>
				<li>
				  <h3>What privacy policy do you think should be applied to the picture?</h3>
				  <div class="form-horizontal"> 
					  <div class="radio">
							<label> <input type="radio" name="policyRadios"
								id="policyRadiosA" value="policyA">${scenario.policyA.description}
							</label>
						</div>
						<c:if test="${scenario.policyB.description != scenario.policyA.description}">
							<div class="radio">
								<label> <input type="radio" name="policyRadios"
									id="policyRadiosB" value="policyB">${scenario.policyB.description}
								</label>
							</div>
						</c:if>
						<c:if test="${scenario.policyC.description != scenario.policyA.description && 
						    scenario.policyC.description != scenario.policyB.description}">
							<div class="radio">
								<label> <input type="radio" name="policyRadios"
									id="policyRadiosC" value="policyC">${scenario.policyC.description}
								</label>
							</div>
						</c:if>
						<div class="form-group">
					    <div class="col-sm-2 radio">
						    <label> 
						      <input type="radio" name="policyRadios" id="policyRadiosOther" 
						        value="policyOther">Other policy:
						    </label>
					    </div>
					    <div class="col-sm-10">
					      <input type="text" class="form-control" placeholder="Enter the other policy">
					    </div>
					  </div>
					</div>
				</li>

				<li>
				  <h3>Why did you choose the above policy?</h3> 
				  <textarea class="form-control" rows="3"
						placeholder="Enter a justification as to why you think the above policy is appropriate for the given picture and context"></textarea>
				</li>
			</ol>
			<div class="text-center"> 
				<input type="submit" class="btn btn-primary btn-lg" value="Submit Responses"/>
			</div>
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
