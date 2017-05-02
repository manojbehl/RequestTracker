<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    
    <title>Claymotion</title>
    <jsp:include page="resources.jsp"></jsp:include>
    <style>
			a:hover{
				color:red;
				font-size:120%;
			}
	</style>
</head>
<body>
    <header>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <strong>Email: </strong>support@picsaxis.com
                    &nbsp;&nbsp;
                    <strong>Support: </strong>+91 8826721105
                </div>

            </div>
        </div>
    </header>
   
    <!-- MENU SECTION END-->
    <div class="content-wrapper">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h4 class="page-head-line">Please Login To Enter </h4>

                </div>

            </div>
            <div class="row" >
			<div class="col-md-3"></div>
                <div class="col-md-6">

                <p align="center"><label style="color: red">${Errormsg}</label></p>
                  <form:form role="form" action="validLogin" modelAttribute="login" method="post"> 
                     <label>Enter Email ID : </label>
                        <input type="email" class="form-control" name="email"  required="required"/>
                        <label>Enter Password :  </label>
                        <input type="password" class="form-control" name="password" required="required"/>
						<hr />
						<p align="right"><a href="#"  data-toggle="modal" data-target="#myModal"> Forgot Password</a></p>
                        <hr />
						<p align="right"><button type="submit"  class="btn btn-info" ><span class="glyphicon glyphicon-user"></span> &nbsp;Log Me In </button>&nbsp;<button type="reset" class="btn btn-danger" > &nbsp;Reset</button>&nbsp;
                        </p>
               	   </form:form>
                </div>
                <div class="col-md-3"></div>

            </div>
        </div>
    </div>
    <!-- CONTENT-WRAPPER SECTION END-->
	<!-- Modal contant -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <h4 class="modal-title" id="myModalLabel">Claymotion</h4>
                                        </div>
                                        <div class="modal-body" style="color:#ff6666;">
                                           <h4>Please contact your Claymotion Account Manager to reset your password.</h4>
                                        </div>
										<div class="modal-footer">
                                        </div>
                                    </div>
                                </div>
                </div>
	<!-- Modal contant End -->
   

   <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    &copy; | By : <a href="http://www.picsaxis.com" target="_blank">Claymotion</a>
                </div>

            </div>
        </div>
    </footer>

    <script src="assets/js/jquery-1.12.4.js"></script>
    <!-- BOOTSTRAP SCRIPTS  -->
    <script src="assets/js/bootstrap.js"></script>
	<!-- CLAYMOTION CUSTOM JAVASCRIPT -->

</body>
</html>
