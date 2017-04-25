goog.require('goog.events');
    goog.require('goog.events.EventType');
    goog.require('goog.dom');
    goog.require('goog.Timer');
    goog.require('goog.i18n.DateTimeFormat');
    goog.require('goog.string.format');
    goog.require('goog.net.XhrIo');
function updateContent(question, dir) {
		var test = getSelectedAnswers("answers");

		var queryString = "$prefix$question/$examName$?question=" + question
				+ "&selected=" + test + "&dir=" + dir;
		goog.net.XhrIo
				.send(
						queryString,
						function(completedEvent) {
							var xhr = completedEvent.target;
							var json = xhr.getResponseJson();
							if (typeof (Storage) !== "undefined") {
								sessionStorage.setItem("json", JSON
										.stringify(json))
								var state = sessionStorage.getItem("show")
								if (null == state) {
									sessionStorage.setItem("show", 0)
								}
							} else {
								alert("No storage avail")
							}
							var currentbutton = goog.dom.getElement("current");
							currentbutton.innerHTML = "Question "
									+ json.value

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
								if (null == json.questions[i].type || json.questions[i].type === 0) {
									content.innerHTML += json.questions[i].text;
								} else {
									content.innerHTML += "<br/><img src='/images/" + json.examName + "/" + json.questions[i].text + "' width='90%'/>";
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

			if (1 == state && 1 == json.answers[i].corrected) {
				radio.setAttribute("checked", true)
			}
			li.appendChild(radio)
			li.innerHTML += "  " + json.answers[i].text
		}
	}