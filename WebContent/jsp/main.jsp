<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="url" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>Bookstore</title>
<jsp:include page="scripts.jsp"/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel-body">

                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/jsp/welcome">Home</a></li>
				</ul>
            	<br>
            
                <table class="table table-hover table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>A</th>
                            <th>B</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr><td>${date}</td><td>2</td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
        </div>
    </div>
</div>

</body>
</html>