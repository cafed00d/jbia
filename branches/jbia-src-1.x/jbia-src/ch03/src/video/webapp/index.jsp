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
<title>JBoss in Action - Hibernate Archive Example</title>
<style>
body {
  font-family: sans-serif;
}

h1,h2,h3 {
  color: #700201;
}

table.input th {
  text-align: right;
}

table.list th {
  text-align: left;
}

td.number {
  text-align: right;
}

table.list td,table.list th {
  padding-right: 1em;
}
</style>
</head>
<body>

<h3>Input data for new video:</h3>
<form action="video" method="post">
<table class="input">
  <tr>
    <th>Name:</th>
    <td><input type="text" size="50" name="name"
      value="Monty Python and the Holy Grail" /></td>
  </tr>
  <tr>
    <th>Minutes:</th>
    <td><input type="text" name="minutes" value="91" /></td>
  </tr>
  <tr>
    <th>Price:</th>
    <td><input type="text" name="price" value="14.99" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" value="submit" title="Add" /></td>
  </tr>
</table>
</form>

<c:if test="${videos != null}">
  <h3>Available videos:</h3>
  <table class="list">
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Minutes</th>
      <th>Price</th>
    </tr>
    <c:forEach var="video" items="${videos}">
      <tr>
        <td class="number">${video.id}</td>
        <%--
          c:out will filter special characters to prevent cross-site scripting
          attacks. We need to do it only for this attribute because it is the
          only one that is a string.
        --%>
        <td><c:out value="${video.name}" /></td>
        <td class="number">${video.minutes}</td>
        <td class="number">${video.price}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>

</body>
</html>
