<div class="divide10"></div>
<div class="container">
	<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">Exam - $examTitle$</li>
	</ol>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">$examTitle$</div>
		<div class="panel-body" >
			<ul>
				<li>Vendor: $exam.vendorName$</li>
				<li>Description: $exam.examDesc$</li>
				<li>Number of questions: $exam.questions$</li>
				<li>Passing scores: $exam.passingScore$</li>
				<li>Question format: $exam.questionFormat$</li>
				<li>Prerequisites: $exam.prerequisite$</li>
			</ul>
			<div class="text-center">
				<div class="btn-toolbar">

					<button id="prev" type="button" class="btn btn-success"
						onclick="window.location.href='$context$test/$examName$?question=0&testtype=1&selected=&dir='">
						<span class="glyphicon glyphicon-cog"></span>&nbsp;Simulation Test
					</button>

					<button id="next" type="button" class="btn btn-success customwidth"
						onclick="window.location.href='$context$test/$examName$?question=0&testtype=2&selected=&dir='">
						<span class="glyphicon glyphicon-star"></span>&nbsp;Practice Test
					</button>

				</div>
			</div>
		</div>
	</div>
</div>