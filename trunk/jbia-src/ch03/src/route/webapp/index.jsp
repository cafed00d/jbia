<%-- 
 *******************************************************************************
 *  Copyright 2008, Javid Jamae and Peter Johnson
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 *******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JBoss in Action - Class Loading and Scoping Demo</title>
<style>
body {
  font-family: sans-serif;
}

h1,h2,h3 {
  color: #700201;
}

div.scenario {
  margin: 1em;
  float: left;
  width: 250px;
  background-color: #CF7F2A;
  float: left;
}

.title {
  text-align: center;
  margin: 0.25em;
}

input+p {
  margin: 0 0.5em 0.5em 0.5em;
}

h3.result {
  margin: 0.25em;
}

div.result {
  margin: 0.25em;
  padding: 0.25em;
  background-color: #EEE;
}

div.resultdata {
  float: left;
  padding-right: 2em;
}

div.resultclose {
  clear: both;
}

input {
  border: 2px outset #555;
  margin: 0px 10px 5px 10px;
}
</style>
</head>
<body>

<%-- Instructions to the user --%>
<h2>Class Loading and Scoping Demo</h2>
<p>The diagrams below illustrate various class-invocation and method-calling
scenarios. Click on the diagram to invoke that scenario. A call log is displayed
showing which calls were successful and where, due to scoping or class
loading restrictions, an error occurred.</p>

<%-- Displays the call-graph results of running the scenario --%>
<c:if test="${route != null}">
  <div class="result">
  <div class="resultdata">
  <h3 class="result">You chose Scenario ${route}:</h3>
  <ul>
    <c:forEach var="p" items="${path}">
      <li>${p}</li>
    </c:forEach>
  </ul>
  </div>
  <div class="resultdata"><img src="images/route${route}.gif" /></div>
  <div class="resultclose" /></div>
</c:if>

<%-- The various scenarios --%>
<form action="route" method="post">

<div class="scenario">
<h3 class="title">Scenario A</h3>
<input type="image" src="images/routeA.gif" name="scenario" value="A"
  title="Click here to invoke scenario A" />
<p>The servlet calls each class individually, with each class returning to
the servlet. All calls are successful.</p>
</div>

<div class="scenario">
<h3 class="title">Scenario B</h3>
<input type="image" src="images/routeB.gif" name="scenario" value="B"
  title="Click here to invoke scenario B" />
<p>Each class calls the next class along a route. In this scenario, the
route is set up such that each call will work.</p>
</div>

<div class="scenario">
<h3 class="title">Scenario C</h3>
<input type="image" src="images/routeC.gif" name="scenario" value="C"
  title="Click here to invoke scenario C" />
<p>Each class calls the next class along a route, but the last class in the
route is not accessible, generating an exception.</p>
</div>

<div class="scenario">
<h3 class="title">Scenario D</h3>
<input type="image" src="images/routeD.gif" name="scenario" value="D"
  title="Click here to invoke scenario D" />
<p>Both the WAR file and the EAR file containing the same definition for the
class named <em>Same</em>. The servlet calls InEar, which creates an instance of
<em>Same</em> and passes it back to the servlet as an Object. The servlet then
attempts to cast the object to <em>Same</em>. Because the servlet is using a
different loader repository than InEar, a class cast exception occurs.</p>
</div>

<div class="scenario">
<h3 class="title">Scenario E</h3>
<input type="image" src="images/routeE.gif" name="scenario" value="E"
  title="Click here to invoke scenario E" />
<p>The EAR package contains one version of a class named <em>Version</em>,
while the JAR in the lib directory contains another version of this class. The
EAR uses a scoped class loader so that it is guaranteed to use its version of
the class.</p>
</div>

</form>

</body>
</html>
