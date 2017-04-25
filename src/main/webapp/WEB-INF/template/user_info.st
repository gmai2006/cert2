<div class="panel-body text_3 color_0 ">
		<form class="form-horizontal"  action="$context$userinfo/" name="userinfo" id="userinfo">
		<input type="hidden" name="action" value="true" />

					<div class="form-group">
							<label for="name" class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
  								<input type="text" class="form-control" id="userid1" name = "userId" value="$username$" readonly>
							</div>
					</div>

 					<div class="form-group">
 						<label for="password" class="col-sm-2 control-label">Password</label>
 						<div class="col-sm-10">
   							<input type="password" class="form-control" id="password" name="password"  value="$password$"  >
 						</div>
					</div>	
				
	<div class="navbar-btn">
					<div class="form-group">    						
								<button type="button" id="saveinfo" class="btn btn-primary pull-right" >Save</button>      						
						</div>  
					</div>
	
			</form>
</div>
