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
<link href="resources/css/starter-template.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="../../assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
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
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Questionnaire</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">

		<div class="page-header">
			<h1>Scenario 1</h1>
			<p class="lead">${imageDescription}</p>
			<img src="resources/images/${imageName}"
				class="img-responsive center-block" alt="Responsive image">
			<ul class="lead">
				<li>A tells the other three that he’s going to share the photo
					with everybody because he thinks the photo is cool and he wants
					everybody to see it.
				<li>B agrees with A that the photo is cool. However, he prefers
					that only common friends can see the photo because it’s a photo
					where he appears and he doesn’t want unknown people to see him.
				<li>C says that the photo is cool. However, he prefers that
					only common friends can see the photo because it’s a photo where he
					appears and he doesn’t want unknown people to see him.
				<li>D says that the photo is cool. However, he prefers that
					only common friends can see the photo because it’s a photo where he
					appears and he doesn’t want unknown people to see him.
			</ul>

			<h2>What privacy policy do you think should be applied to the
				photo?</h2>
			<form>
				<div class="form-group">
					<div class="radio">
						<label> 
						<input type="radio" id="shareWithAll" name="policy" value="">
							Share with everybody (A)
						</label>
					</div>
				</div>
				<div class="form-group">
          <div class="radio">
            <label> 
            <input type="radio" id="shareWithCommon" name="policy" value="">
              Share with common friends only (B, C, and D)
            </label>
          </div>
        </div>
				<div class="form-group">
					<div class="radio">
						<label> <input
							type="radio" id="shareOther" name="policy" value="">
							Other:
						</label>
					</div>
				</div>
				<div class="form-group">
					<input placeholder="Enter other policy" type="text" class="form-control"
						id="otherPolicy" name="otherPolicy" maxlength="10"
						data-rule-required="true" contenteditable="false">
				</div>
			</form>
			<h2>Why?</h2>
			<textarea class="form-control" rows="3"></textarea>
		</div>

	</div>
	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>