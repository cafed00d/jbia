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
<%@ page session="false"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<form action="${image.editAction}" method="post">
<table>
  <tr>
    <td><f:message key="label.title" /></td>
    <td><input type="text" size="60" name="title"
      title="<f:message key='tip.title' />" value="${image.title}"/>
    </td>
  </tr>
  <tr>
    <td><f:message key="label.url" /></td>
    <td><input type="text" size="60" name="url"
      title="<f:message key='tip.url' />" value="${image.url}" />
    </td>
  </tr>
  <tr>
    <td><f:message key="label.regex" /></td>
    <td><input type="text" size="60" name="regex"
      title="<f:message key='tip.regex' />"
      value="<c:out value='${image.regex}'/>" />
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>
      <input type="submit" name="submit"
        value="<f:message key='button.submit' />" />
      <input type="submit" name="cancel"
        value="<f:message key='button.cancel' />" /></td>
  </tr>
</table>
</form>