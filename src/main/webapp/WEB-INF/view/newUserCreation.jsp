<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />

<title>Claymotion</title>
<jsp:include page="resources.jsp"></jsp:include>
<style type="text/css">
#success_message {
	color: :green;
}
</style>
</head>
<body>
	<header>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
                    <strong>Email: </strong>adops@claymotion.in
                    &nbsp;&nbsp;
                    <strong>Support: </strong>+919818900229
                    &nbsp;&nbsp;
                    <strong>Copyright: </strong>http://claymotion.in
				</div>

			</div>
		</div>
	</header><section class="menu-section">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="navbar-collapse collapse ">
						<ul id="menu-top" class="nav navbar-nav navbar-right">
							<li><a href="getPartnerList">Dashboard</a></li>
							<li><a href="moveToCreateApp">Manage Apps</a></li>
							<li><a href="managePartner">Manage Partner</a></li>
							<li><a href="addPartnerIp">Map App-Ip</a></li>
							<li><a href="createUser" >Partner
									User</a></li>
									<li><a href="requestReport" >Report</a></li>
									<li><a href="logout">Logout</a></li>
						</ul>
					</div>
				</div>

			</div>
		</div>
	</section>
	<!-- MENU SECTION END-->
	<div class="content-wrapper">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h4 class="page-head-line">Insert Partner User Details</h4>

				</div>

			</div>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<c:if test="${password != null}">
    				<script type="text/javascript">
			var pwd=  '${password}';
			if(pwd != null){
				$(document).ready(function () {
					
					 $('#copyTarget').val(pwd);
				    $('#passwordShow').modal('show');

				});
			}
    				</script> 
						</c:if>
					<!--new User Detains -->
					<div align="center">
						<c:if test="${SuccessMsg != null}">
    				<p style="color:green;"><b>${SuccessMsg}</b></p> 
						</c:if>
					
						<c:if test="${ErrorMsg != null}">
    				<p style="color:red;"><b>${ErrorMsg}</b></p> 
						</c:if>
						</div>
					<form:form action="addUser" method="post" modelAttribute="user">
					<div class="form-group">
						<label><b> Select Partner</b></label>
						 <select class="form-control"
							name="partnerId" required>
							<option value="" >Please Select Partner Name</option>
							<c:forEach items="${PartnerList}" var="PartnerList">
								<option value="${PartnerList.partnerId }">${PartnerList.partnerName}
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label><b> User Email</b></label> <input type="email"  name="userEmail" id="userEmail"
							class="form-control" placeholder="Enter Email" required="required" onblur="checkEmail(this.value);"/>
							<span id="errmsg" style="color: red;"></span>
					</div>
					<div class="form-group">
						<label><b> First Name</b></label> <input type="text" name="fname"
							class="form-control" placeholder="Enter First Name" maxlength="100" required="required"/>
					</div>
					<div class="form-group">
						<label><b> Last Name</b></label> <input type="text" name="lname"
							class="form-control" placeholder="Enter Last Name" maxlength="100" required="required" />
					</div>
					<!-- data-toggle="modal" data-target="#myModal" -->
					<p align="right" class="help-block">press save button to save
						information</p>
					<p align="right">
						<button type="reset"  class="btn btn-danger">&nbsp;Clear</button>
						&nbsp;
						<button  type="submit" class="btn btn-success">
							<span class="glyphicon glyphicon-user"></span> &nbsp;Save
						</button>
						&nbsp;
					</p>
						</form:form>
					<hr />
				</div>
			</div>
			<div class="col-md-3"></div>

		</div>
	</div>
	</div>
	<!-- CONTENT-WRAPPER SECTION END-->
	<!-- Modal contant -->
	<div class="modal fade" id="passwordShow" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">Claymotion</h4>
				</div>
				<div class="modal-body">
					
					<h4 style="color: #ff6666;">Please copy your password and Save
						it....</h4>
					<hr>

					<div class="form-group">
						<label><b> User Password </b></label> <input type="text"
							class="form-control" id="copyTarget" readonly="readonly" value="${password}" /><br>
						<p align="right">
							<button class="btn btn-danger" data-dismiss="modal"
								aria-hidden="true" id="copyButton">&nbsp;Copy</button>
						</p>
					</div>
				</div>

				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	<!-- Modal contant End -->
	
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					&copy; | By : <a href="http://claymotion.in" target="_blank">Claymotion</a>
				</div>

			</div>
		</div>
	</footer>

	<!-- CLAYMOTION CUSTOM JAVASCRIPT -->
	<script>
	function checkEmail(str)
	{
	    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    if(str.trim()!==""){
		    if(!re.test(str)){ 
		      $( "#errmsg" ).empty();
		    $( "#errmsg" ).append('<b>Please write Correct Email</b>');
		    
		     }
	    else{ 
	    	$( "#errmsg" ).empty();
		    return true; }
	    }
	    else{$( "#errmsg" ).empty();}
	    
	}
	
	document.getElementById("copyButton").addEventListener("click", function() {
        copyToClipboard(document.getElementById("copyTarget"));
    });

    function copyToClipboard(elem) {
          // create hidden text element, if it doesn't already exist
        var targetId = "_hiddenCopyText_";
        var isInput = elem.tagName === "INPUT" || elem.tagName === "TEXTAREA";
        var origSelectionStart, origSelectionEnd;
        if (isInput) {
            // can just use the original source element for the selection and copy
            target = elem;
            origSelectionStart = elem.selectionStart;
            origSelectionEnd = elem.selectionEnd;
        } else {
            // must use a temporary form element for the selection and copy
            target = document.getElementById(targetId);
            if (!target) {
                var target = document.createElement("textarea");
                target.style.position = "absolute";
                target.style.left = "-9999px";
                target.style.top = "0";
                target.id = targetId;
                document.body.appendChild(target);
            }
            target.textContent = elem.textContent;
        }
        // select the content 
        var currentFocus = document.activeElement;
        target.focus();
        target.setSelectionRange(0, target.value.length);
        
        // copy the selection
        var succeed;
        try {
              succeed = document.execCommand("copy");
        } catch(e) {
            succeed = false;
        }
        // restore original focus
        if (currentFocus && typeof currentFocus.focus === "function") {
            currentFocus.focus();
        }
        
        if (isInput) {
            // restore prior selection
            elem.setSelectionRange(origSelectionStart, origSelectionEnd);
        } else {
            // clear temporary content 
            target.textContent = "";
        }
        return succeed;
    }
			  </script>

</body>
</html>
