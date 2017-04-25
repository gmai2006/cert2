<div class="divide10"></div>
<div class="container">
	<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">IT News</li>
	</ol>
	<div class="panel panel-default">
		<div class="panel-heading">IT Certification news</div>
		<div class="panel-body">
			<ul>
				$result: { n |
				<li><a href="$n.right$" target="_blank">$n.left$ </a></li> }$
			</ul>
		</div>
	</div>
</div>
