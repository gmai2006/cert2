<div class="divide10"></div>
<div class="container">
	<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">My Home</a></li>
		<li ><a href="$context$exam/$examName$">Exam - $examTitle$</a></li>
		<li class="active">Simulator Test</li>
	</ol>
	<div class="panel panel-default ">
	
	<div class="panel-body pre-scrollable contentpanel" style="height: 600px;">

		<h3>Question:</h3>
		<div id="content">$exhibit$</div>
		<h3>Answers:</h3>
		<div id="answers">
			<ol>
				$answers:{n|
				<li><input type="$choice$">$n.text$</li>}$
			</ol>
		</div>
	</div>
</div>
</div>
<div class="text-center">
	<div class="navbar-btn">
	<form name ="form1" action="" id ="form1">
	<input type="hidden" id="selectedAns" name="selectedAns">
	<input type="hidden" id="useraction" name="useraction">
	<input type="hidden" id="username" value="$username$">							
	<input type="hidden" id="emailaddress" name="emailaddress" value="$email$">

		<label id="lbTimer">Time remaining: </label>
<span id="countdown">$timer$</span>

		<button id="prev" type="button"
			class="btn btn-primary customwidth disabled"
			onclick="updateContent(0, 'p');">
			<span class="glyphicon glyphicon-circle-arrow-left"></span>&nbsp;Previous
		</button>
		<button id="current" type="button" class="btn btn-primary customwidth">Question
			1</button>
		<button id="next" type="button" class="btn btn-primary customwidth"
			onclick="updateContent(0, 'n');">
			Next&nbsp;<span class="glyphicon glyphicon-circle-arrow-right">

		</span>
		</button>
			
		</form>
		<!--<input type="checkbox" onclick="showNotShowAnswers();">&nbsp;Show
		answers -->
	</div>
</div>
<script>

	

	function updateContent(question, dir) {
       
		if (window.document.form1.prev.disabled == true) {
			window.document.form1.prev.disabled = false; 
		}


		var test = getSelectedAnswers("answers")
		var queryString = "$context$simulationtest/$examName$?question=" + question
				+ "&selected=" + test + "&dir=" + dir +"&useraction="+"doexam";

		goog.net.XhrIo.send(queryString,
				function(completedEvent) {
					var xhr = completedEvent.target;
					var json = xhr.getResponseJson();
					if (typeof (Storage) !== "undefined") {
						sessionStorage.setItem("json", JSON.stringify(json))
						var state = sessionStorage.getItem("show")
						if (null == state) {
							sessionStorage.setItem("show", 0)
						}
					} else {
						alert("No storage avail")
					}
					var currentbutton = goog.dom.getElement("current");
					currentbutton.innerHTML = "Question " + json.value

					var prevbutton = goog.dom.getElement("prev");
					var nextbutton = goog.dom.getElement("next");

					if ("b" == json.where) {
						prevbutton.setAttribute("class",
								"btn btn-primary customwidth disabled");
						nextbutton.setAttribute("class",
								"btn btn-primary customwidth");														
					} else if ("e" == json.where) {
						prevbutton.setAttribute("class",
								"btn btn-primary customwidth");
						nextbutton.setAttribute("class",
								"btn btn-primary customwidth disabled");						
					} else {
						prevbutton.setAttribute("class",
								"btn btn-primary customwidth");
						nextbutton.setAttribute("class",
								"btn btn-primary customwidth");								
					}

					nextbutton.setAttribute("onclick", "updateContent("
							+ json.current + ",'n');")
					prevbutton.setAttribute("onclick", "updateContent("
							+ json.current + ",'p');")

					var content = goog.dom.getElement("content");
					content.innerHTML = "";
					for (var i = 0; i < json.questions.length; i++) {
						if (null == json.questions[i].type
								|| json.questions[i].type === 0) {
							content.innerHTML += json.questions[i].text;
						} else {
							content.innerHTML += "<br/><img src='/images/"
									+ json.examName + "/"
									+ json.questions[i].text
									+ "' width='99%'/>";
						}
					}
					rebuildAnswers(json, state)
				});
	}

	function getSelectedAnswers(name) {
		var result = "";
		var answer = document.getElementById(name)
		var lo = answer.getElementsByTagName("ol");
		var list = lo[0].getElementsByTagName("li");

		for (var i = 0; i < list.length; i++) {
			var input = list[i].firstChild
			if (null != input) {
				if (input.checked) {
					result += "" + i + ","
				}
			}
		}

		return result
	}

	function showNotShowAnswers() {
		var jsonstr = sessionStorage.getItem("json")
		var json = JSON.parse(jsonstr)
		var state = sessionStorage.getItem("show")
		state ^= true
		sessionStorage.setItem("show", state)

		rebuildAnswers(json, state)
	}

	function rebuildAnswers(json, state) {
		var answers = goog.dom.getElement("answers");
		while (answers.firstChild) {
			answers.removeChild(answers.firstChild);
		}
		var inputtype = "radio"
		var ol = document.createElement("ol")
		answers.appendChild(ol)
		for (var i = 0; i < json.answers.length; i++) {
			var li = document.createElement("li")
			ol.appendChild(li)

			if ("1" == json.choice) {
				inputtype = "checkbox"
			} else {
				inputtype = "radio"
			}

			var radio = document.createElement("input")
			radio.setAttribute("type", inputtype);
			radio.setAttribute("name", "answers");

			if (json.sAnswer.indexOf(i) > -1){
				radio.setAttribute("checked", true);
			}
			li.appendChild(radio)
			li.innerHTML += "  " + json.answers[i].text
		}
	}
	
</script>