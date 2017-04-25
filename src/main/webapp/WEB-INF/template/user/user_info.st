<div class="panel-body text_3 color_0">
	<table class="table">						
		<tr>
			<td>
				<div class="panel-body text_3 color_0 ">
					<table class="table table-condensed table-bordered">			
						<tr>
							<td><button class="btn btn-primary" onclick="displayUserInfo();">User Info</button></td>	
						</tr>
						<tr>				
							<td><button class="btn btn-primary" onclick="displayExamInfo();">My Exam</button></td>				
						</tr>
					</table>	
				</div>
			</td>		
				<td>
					
					<div class="panel-body text_3 color_0 ">
						<form class="form-horizontal"  action="$context$register/" name="userinfo" id="userinfo"> 
							<input type="hidden" name="useraction" value="updateuser" id="useraction">	
							<input type="hidden" id="invalidlogin" value="$invalidlogin$">
							<input type="hidden" id="username" value="$username$">
							<input type="hidden" id="pw" value="$password$">
							<input type="hidden" id="userid2" name = "userid2" value="$username$">
							<input type="hidden" id="emailaddress" name="emailaddress" value="$email$">
							
  						<div class="panel-body">
 							<input type="hidden" name="useraction" value="insertuser">
  							<div class="form-group">
    							<label for="name" class="col-sm-2 control-label">Name</label>
   								<div class="col-sm-10">
      								<input type="text" class="form-control" id="userid1" name = "userid1" $if(isDp)$ value="$username$" $endif$ readonly>
    							</div>
  							</div>

	    					<div class="form-group">
	    						<label for="password" class="col-sm-2 control-label">Password</label>
	    						<div class="col-sm-10">
	      							<input type="password" class="form-control" id="password" name="password" $if(isDp)$ value="$password$" $endif$ >
	    						</div>
	  						</div>	
						</div>	
								
							<div class="navbar-btn" style="text-align:center;">
    							<div class="form-group">    						
      								<button type="button" id="saveinfo" class="btn btn-primary customwidth" onclick="checkInfo();">Save</button>      						
  								</div>  
  							</div>
  						
  												
  					</form>
				</td>	
			</tr>
		</table>	
</div>
<script type="text/javascript">
	function displayUserInfo() {
		document.userinfo.userid2.value = document.userinfo.username.value
		document.userinfo.userid1.value = document.userinfo.username.value
		document.userinfo.password.value = document.userinfo.pw.value
		document.getElementById("saveinfo").disabled = false;
	}

	function displayExamInfo() {
		document.getElementById('userinfo').action="$context$examinfo/";
		document.getElementById('useraction').value = "listexam";
		document.getElementById('userinfo').submit(); 
	}
	function checkInfo() {
		var uid = document.getElementById("userid2").value;
		if (uid == "") {
			alert("User id cannot be empty.");
		}else{
			document.getElementById("saveinfo").disabled = true;
			document.getElementById('userinfo').submit(); 
		}
	}
</script>