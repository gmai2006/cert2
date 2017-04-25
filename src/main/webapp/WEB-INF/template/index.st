<div class="jumbotron">
	<h2 class="custom-header">
		Prepare for Certifications<br /> By Taking FREE Online Exams
	</h2>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8 custom-center">
			<form method="POST" action="$context$search/" id="searchForm">

<input type="hidden" name="movetype" value="0">
<input type="hidden" name="pagenumber" value="$pagenumber$">
<input type="hidden" name="rownum" value="10">
<input type="hidden" id="hasrecord" value="$hasrecord$">
<input type="hidden" id="invalidlogin" value="$invalidlogin$">
<input type="hidden" id="username" value="$username$">
<input type="hidden" id="emailaddress" name="emailaddress" value="$email$">
		
				<div class="input-group">
					<input type="text" name="search" class="form-control"
						placeholder="MS certification.." value="$searchstring$"> 
						<span class="input-group-addon" onclick="searchExam()">Search</span>
				</div>
			</form>
		</div>
	</div>
</div>

$if(action)$
<div class="text-center">
<div role="alertdialog" class="alert alert-info">
    $message$
    <button class="close" data-dismiss="alert" aria-label="close">OK</button>  
</div>
</div>
<!--<div class="alert alert-info" role="alertdialog">$message$</div>-->
$endif$
<div class="row">
	<div class="col-md-12">
		<h3 class="header_1 wrap_3 color_3">Popular Certifications</h3>
	</div>
</div>

$sub/searchresult()$

<div class="row">
		<div class="col-md-12">
			<h3 class="header_1 wrap_3 color_3">Features</h3>
		</div>
	</div>

	<div class="row ">
		<div class="col-md-4 col-sm-6 margin20">
			<div class="box_1">
				<div class="icon_1"></div>
				<h3 class="text_2 color_3 maxheight1">
					<a href="software.xhtml">More than 1000 certifications</a>
				</h3>
				<p class="text_3 color_3 maxheight">Questions and answers with explanations.</p>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 margin20">
			<div class="box_1">
				<div class="icon_2"></div>
				<h3 class="text_2 color_3 maxheight1">
					<a href="data.xhtml">Exam result with analytics</a>
				</h3>
				<p class="text_3 color_3 maxheight">User testing feedback with analytics results.</p>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 margin20">
			<div class="box_1">
				<div class="icon_3"></div>
				<h3 class="text_2 color_3 maxheight1">
					<a href="talent.xhtml">Comments</a>
				</h3>
				<p class="text_3 color_3 maxheight">Comments provide students tips and tricks.</p>
			</div>
		</div>
	</div>
<script>
function searchExam() {

	document.getElementById('searchForm').rownum.value = 10;
	document.getElementById('searchForm').submit();
}

</script>