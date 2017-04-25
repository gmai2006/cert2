<div id="header-top" class="hidden-xs">
	<div class="container">
		<div class="top-bar">
			<div class="pull-left sample-1right">
				<a><i
					class="fa fa-envelope"></i> Mail us: <span class="colored-text">support@itcertification2.com</span>
				</a>
			</div>
			<div class="pull-right sample-1right">
				<ul class="list-inline custom-list">
					$if (userId)$ 
					<li><a href="$context$userinfo/"><span id="loginname">$userId$</span></a> </li>
					<li><a href="$context$signout.asp"><i
					class="fa fa-sign-out"></i> Sign out</a></li>
					$endif$
				</ul>
			</div>
		</div>
	</div>
</div>