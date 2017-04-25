<div class="navbar navbar-default navbar-static-top yamm sticky"
	role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.xhtml"> <!--  <img src="img/logo.png" alt="MaiSoftwareSolution">-->
				Certification Online Testing
			</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="$index$"><a href="$context$index.xhtml"><i class="fa fa-home"></i>&nbsp;Home</a></li>
				<li class="$about$"><a href="$context$FAQ.xhtml">FAQ</a></li>
				<li class="dropdown">
                       <a href="#" class="dropdown-toggle" data-toggle="dropdown">Certification <i class="fa fa-angle-down"></i></a>
                       <ul class="dropdown-menu" role="menu"> 
                        <li><a href="news.xhtml">News</a></li>
                        <li><a href="salaries.xhtml">Salaries</a></li>
                       </ul>
                   </li>
				<li class="$blog$"><a href="/jforum/forums/list.page">Forum</a></li>
				<li class="$blog$"><a href="index.xhtml">Search</a></li>
				<li class="$login$"><a href="login.xhtml"><i class="fa fa-lock"></i></a></li>
				<!--menu search li end here-->
				
				<!--menu login li end here-->
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	<!--container-->
</div>
<script>
function prepareLogin() {
		document.getElementById('loginForm').submit();
	}
</script>