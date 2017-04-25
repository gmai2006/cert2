<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Registration Form</h3>
 	</div>

 <form class="form-horizontal" method="POST" action="$context$register/" name="registerForm" id="registerForm" > 	
   	<div class="panel-body">
 		<input type="hidden" name="useraction" value="insertuser">
  		<div class="form-group">
    		<label for="name" class="col-sm-2 control-label">Name</label>
   			<div class="col-sm-10">
      			<input type="text" class="form-control" id="userid" name="userid" placeholder="Enter Name" onblur="" required>
    		</div>
  		</div>
   		<div class="form-group">
   			<label for="email" class="col-sm-2 control-label">Email</label>
 			<div class="col-sm-10">
   				<div class="input-group input-group-sm">
     				<span class="input-group-addon">@</span>
    				<input type="email" class="form-control" placeholder="Enter email" id="InputEmail" name="email" onblur="" required>
  				</div>
  			</div>
  			</div>
    	<div class="form-group">
    		<label for="password" class="col-sm-2 control-label">Password</label>
    		<div class="col-sm-10">
      			<input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
    		</div>
  		</div>
<!-- 
    	<div class="form-group"> 
    		<label class="col-sm-2 control-label">Address</label>
    		<div class="col-sm-10">
      			<input type="text" class="form-control" id="address" name="address">
    		</div>
  		</div>		
    	<div class="form-group">
    		<label class="col-sm-2 control-label">City</label>
    		<div class="col-sm-10">
      			<input type="text" class="form-control" id="city" name="city">
    		</div>
  		</div>	
    	<div class="form-group">
    		<label for="password" class="col-sm-2 control-label">State</label>
    		<div class="col-sm-10">
      			<input type="text" class="form-control" id="state" name="state">
    		</div>
  		</div>	
    	<div class="form-group">
    		<label for="password" class="col-sm-2 control-label">Phone</label>
    		<div class="col-sm-10">
      			<input type="text" class="form-control" id="phone" name="phone">
    		</div>
  		</div>	 
    	<div class="form-group">
    		<label for="password" class="col-sm-2 control-label">Country</label>
    		<div class="col-sm-10">
      			<input type="text" class="form-control" id="country" name="country">
    		</div>
  		</div>	 
     	<div class="form-group">
    		<label for="password" class="col-sm-2 control-label">Zipcode</label>
    		<div class="col-sm-10">
      			<input type="text" class="form-control" id="zipcode" name="zipcode">
    		</div>
  		</div> 		 		 		 			
		 -->
	</div>
	<div class="navbar-btn" style="overflow:hidden;text-align:center;">
    	<div class="form-group">
    		<div class="btn btn-primary customwidth">
      			<button type="submit" class="btn btn-primary customwidth" >Create Account</button>      			
    		</div>
  		</div>  
  	</div>
  	</form>
</div>

<script>

function validation() {  
    var em = document.registerForm.email.value;
    var uid = document.registerForm.userid.value;
    if (em == "" && uid == ""){
        alert("- User id and Email address cannot be empty.");
        document.getElementById('userid').focus();
    }else if (uid == "") {
        alert("User id cannot be empty.");
        document.getElementById('userid').focus();
    }else if (em == "") {
        alert("Email address cannot be empty.");
        document.getElementById('email').focus();
    }else {
        var queryString = "$context$login/?useraction="+"getemail"+ "&email=" + em+ "&uid="+uid;   
        //alert(queryString) ;
        goog.net.XhrIo.send(queryString, function(completedEvent) {    
            var xhr = completedEvent.target;      
            var json = xhr.getResponseJson();        
            if (json.ecount > 0 && json.ucount >0) {
              alert("- Email address \n - User Id \n has already existed! Please choose another one.");
              
            }else if (json.ecount > 0){
              alert("- Email address has already existed! Please choose another one.");
              
            }else if (json.ucount > 0){
              alert("- User id has already existed! Please choose another one.");
            } else{
              document.getElementById('registerForm').submit(); 
            }
        });             
    }          
}
</script>