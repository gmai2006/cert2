           
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var objd = document.getElementById('dung');
        var d = parseInt(objd.value);
        var objs = document.getElementById('sai');
        var s = parseInt(objs.value);
        var m = 100 - d - s;

        var data = google.visualization.arrayToDataTable([
          ['Questions'           , 'Percent'],
          ['Correct Questions',         d],
          ['Incorrect Questions'  ,     s],
          [''  ,     m]
        ]);

        var options = {
          title: 'Exam Information'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>

	
	<div class="panel panel-default">
    <div id="piechart" style="width: 600px; height: 350px;"></div>
    <form class="form-horizontal"  action="" name="frm1" id="frm1"> 
              <input type="hidden" name="dung" value="$correct$" id="dung">  
              <input type="hidden" name="sai" value="$incorrect$" id="sai">  
    </form>   
 <div  panel-body pre-scrollable contentpanel >
 <h4> Incorrect Questions</h4>
    <table class="table">           
      <tr>        
        <td>
          
        <div >

          <form class="form-horizontal"  action="$context$examinfo/" name="examinfo" id="examinfo"> 
            <input type="hidden" name="useraction" value="listexaminfo" id="useraction">  
            <input type="hidden" name="examName" value="" id="examName">  
          <table class="table  table-bordered">
            <thead>
              <tr>
                <th width="15%">Question #</th>
                <th>Details</th>
                
              </tr>
            </thead>      
            $questions:{n|
              <tr>
                <td><a href="#" onclick="displayQuestion('$n.examName$', '$n.questionId$');">$n.questionId$</a></td>
                <td><a href="#" onclick="displayQuestion('$n.examName$', '$n.questionId$');">$n.text$</a></td>
              </tr>
            }$    
          </table>  
        </div>
  

        </td> 
      </tr>
    </table>  
</div>

<div class="panel-body pre-scrollable contentpanel" style="height: 260px;">

    <h4>Question:</h4>
    <div id="content">$exhibit$</div>
    <h4>Answers:</h4>
    <div id="answers">
      <ol>
        $answers:{n|
        <li><input type="$choice$">$n.text$</li>}$
      </ol>
    </div>
  </div>
  </div>

<script type="text/javascript">
  function displayQuestion(examName, questionId) {        
    
    var queryString = "$context$simulationtest/?question="+ questionId + "&examName=" + examName+"&useraction="+"listanswer";
       
    goog.net.XhrIo.send(queryString,
        function(completedEvent) {
          var xhr = completedEvent.target;          
          var json = xhr.getResponseJson();
          // alert('-------------2');
          if (typeof (Storage) !== "undefined") {
            sessionStorage.setItem("json", JSON.stringify(json))
            var state = sessionStorage.getItem("show")
            if (null == state) {
              sessionStorage.setItem("show", 0)
            }
          } else {
            alert("No storage avail")
          }
          

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
          rebuildAnswers(json, state);
        });
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
      if ( 1 == json.answers[i].corrected) {
        radio.setAttribute("checked", true)
      }
      li.appendChild(radio)
      li.innerHTML += "  " + json.answers[i].text
    }
  }
  </script>