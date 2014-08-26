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

                <p>Triple count: ${size}.</p>
                
                <p>SELECT ?s ?p ?o  WHERE {?s ?p ?o .}</p>
                
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/main">SELECT all</a></li>
				</ul>
				
				<br><br><p>SELECT ?s<br>
					WHERE { ?s  &lt;http://www.w3.org/2000/01/rdf-schema#subClassOf&gt; &lt;http://dbpedia.org/ontology/#PrintedMatter&gt; }</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=1">SELECT main classes</a></li>
				</ul>
				                
				<br><br><p>SELECT ?s ?o<br>
					WHERE {?s &lt;http://www.w3.org/2000/01/rdf-schema#subClassOf&gt; ?o}<br>
					(single solution)</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=2">SELECT all subclasses</a></li>
				</ul>

				<br><br><p>SELECT ?s ?o<br>
					WHERE {<br>
					  {?s &lt;http://example.org/quantity&gt; ?o}<br>
					  UNION<br>
					  {?s &lt;http://example.org/price&gt; ?o}<br>
					}<br>
					(Literal Values)</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=3">Literal Values & UNION</a></li>
				</ul>

				<br><br><p>SELECT ?s<br>
					WHERE { ?s rdf:type &lt;owl:Class&gt; }<br>
					(all classes)</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=4">All classes</a></li>
				</ul>

				<br><br><p>SELECT ?s ?o<br>
					WHERE { ?s &lt;http://example.org/quantity&gt; ?o .<br>
    				FILTER (?o >= 100)<br>
    				}</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=5">FILTER</a></li>
				</ul>

				<br><br><p>SELECT ?s ?p ?o<br>
					WHERE { ?s ?p ?o .<br>
    				FILTER regex(?o, "Map")<br>
    				}</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=6">Regex FILTER</a></li>
				</ul>
				
				<br><br><p>SELECT ?s ?p ?o<br>
					WHERE {<br>
    				?s &lt;http://example.org/quantity&gt; ?p .<br>
    				OPTIONAL {?s &lt;http://example.org/price&gt; ?o }<br>
    				}</p>
                <ul class="nav nav-pills">
				  <li class="active"><a href="${url}/ex?zIndex=7">OPTIONAL</a></li>
				</ul>

            </div>
        </div>
    </div>
</div>

</body>
</html>