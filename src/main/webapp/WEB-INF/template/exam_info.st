
<div class="panel-body text_3 color_0 ">
	<form class="form-horizontal" action="$context$examdetail/"
		name="examdetail" id="examdetail">
		<input type="hidden" name="examName" value="" id="examName"> <input
			type="hidden" name="sessionId" value="" id="sessionId">

		<table class="table  table-bordered">
			<thead>
				<tr>
					<th>Exam name</th>
					<th># Questions</th>
					<th># Corrected Questions</th>
					<th># Incorrect Questions</th>
				</tr>
			</thead>
			$exams:{n|
			<tr>
				<td><a href="#"
					onclick="displayExamDetails('$n.examName$','$n.sessionId$');">$n.examName$</a></td>
				<td>$n.questionNumber$</td>
				<td>$n.correctNumber$</td>
				<td>$n.incorrectNumber$</td>
			</tr>
			}$
		</table>
	</form>
</div>

<script type="text/javascript">
var displayExamDetails= function (examName, sessionId) {	
		document.getElementById('examdetail').action="$context$examdetail/";
		document.getElementById('examName').value = examName;
		document.getElementById('sessionId').value = sessionId;
		document.getElementById('examdetail').submit(); 
	}	
</script>