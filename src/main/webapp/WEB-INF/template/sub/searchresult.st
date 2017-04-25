
<div class="panel panel-default">
	<div class="panel-body text_3 color_0">
		<table class="table table-condensed table-bordered">
			<thead>
				<tr>
					<th>Vendors</th>
					<th>Certification</th>
					<th>Online Test</th>
					<th>Study Guide (PDF)</th>
				</tr>
			</thead>
			$exams:{n|
			<tr>
				<td>$n.vendorName$</td>
				<td>$n.examName$</td>
				<td><a href="$context$exam/$n.examName$" class="btn btn-default" role="button">
						Start Online Testing </a></td>
				<td><a href="$context$download/download?id=$n.examKey$">Download-able Study Guide</a></td>
			</tr>
			}$
		</table>	
	</div>
	
</div>

<div class="text-center">
	<div class="navbar-btn">
<form method="POST" action="" id="form1">
$if(eFirst)$
	<button id="first" type="button" 	class="btn btn-primary customwidth"	
		onclick="document.getElementById('searchForm').movetype.value=0;listExam(); return false;">
			<span class="glyphicon glyphicon-circle-arrow-left"></span>&nbsp;First
	</button>
$endif$
$if(dFirst)$
		<button id="first" type="button" 	class="btn btn-primary customwidth disabled" 	onclick="">
			<span class="glyphicon glyphicon-circle-arrow-left"></span>&nbsp;First
		</button>
$endif$
$if(ePrev)$
	<button id="prev" type="button" 	class="btn btn-primary customwidth"	
		onclick="document.getElementById('searchForm').movetype.value=1;listExam(); return false;">
			<span class="glyphicon glyphicon-circle-arrow-left"></span>&nbsp;Previous
	</button>
$endif$
$if(dPrev)$
		<button id="prev" type="button" 	class="btn btn-primary customwidth disabled" 	onclick="">
			<span class="glyphicon glyphicon-circle-arrow-left"></span>&nbsp;Previous
		</button>
$endif$
$if(dOption)$
		<span>
		<select class="btn btn-primary btn-select-light" data-width="auto" id="rownum1" onchange="document.getElementById('searchForm').movetype.value=0;listExam();return false;">
			<option value="10" $if(e10)$ selected="selected" $endif$>10</option>
			<option value="20" $if(e20)$ selected="selected" $endif$>20</option>
			<option value="30" $if(e30)$ selected="selected"  $endif$>30</option>
			<option value="40" $if(e40)$ selected="selected"  $endif$>40</option>
			<option value="50" $if(e50)$ selected="selected"  $endif$>50</option>
		</select>
		</span>
$endif$		
$if(eNext)$		
		<button id="next" type="button" class="btn btn-primary customwidth"
			onclick="document.getElementById('searchForm').movetype.value=2;listExam(); return false;">
			Next&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
		</button>
$endif$			
$if(dNext)$	
		<button id="next" type="button" class="btn btn-primary customwidth disabled">
			Next&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
		</button>
$endif$		
$if(eLast)$		
		<button id="last" type="button" class="btn btn-primary customwidth"
			onclick="document.getElementById('searchForm').movetype.value=3;listExam(); return false;">
			Last&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
		</button>
$endif$			
$if(dNext)$	
		<button id="last" type="button" class="btn btn-primary customwidth disabled">
			Last&nbsp;<span class="glyphicon glyphicon-circle-arrow-right"></span>
		</button>
$endif$		
		</form>
	</div>	
</div>

<script>

function listExam(){

	document.getElementById('searchForm').rownum.value = document.getElementById("rownum1").value;
	document.getElementById('searchForm').submit();	
}

</script>