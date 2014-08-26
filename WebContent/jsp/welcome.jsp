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
            
                <p>Welcome!</p>
                
                <p>Triple count: ${size}.</p>
                
                <p>SELECT ?s ?p ?o  WHERE {?s ?p ?o .}</p>
                
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/main">Select</a></li>
				</ul>
				
				<br><p>SELECT ?s<br>
					WHERE { ?s  &lt;http://www.w3.org/2000/01/rdf-schema#subClassOf&gt; &lt;http://dbpedia.org/ontology/#PrintedMatter&gt; }</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=1">Select</a></li>
				</ul>
				                
				<br><p>SELECT ?s ?o<br>
					WHERE {?s &lt;http://www.w3.org/2000/01/rdf-schema#subClassOf&gt; ?o}<br>
					(single solution)</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=2">Select</a></li>
				</ul>
       
            </div>
        </div>
    </div>
</div>

</body>
</html>