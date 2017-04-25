<div class="container">
	<ol class="breadcrumb">
		<li><a href="index.xhtml">Home</a></li>
		<li class="active">Sign in</li>
	</ol>
	<div class="row">
	$if(message)$
	<div class="alert $cssId$" role="alert">$message$</div>
	$endif$
		<div class="col-md-7">
			<h4>Sign In</h4>
			<hr />
			<form class="form-horizontal" role="form" name="loginform"
				action="request_password.asp" method="post">
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">Your
						UserID</label>
					<div class="col-xs-9">
						<input type="email" class="form-control" id="InputEmail"
							name="user" placeholder="Enter User ID" required>
					</div>
				</div>
				<input type="submit" name="submit" id="submit"
					value="Request Password" class="btn btn-primary pull-right">
			</form>
		</div>
		<!-- end of col 8 -->
	</div>
	<br />
</div>

<script>
	document.loginform.user.focus();
</script>
