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
<p><f:message key="help.intro" /></p>
<ol>
  <li><f:message key="help.option1" /></li>
  <li><f:message key="help.option2" /></li>
</ol>
<p><f:message key="help.table" /></p>
<table border="1" cellpadding="2px">
  <tr>
    <th><f:message key="help.table.subject" /></th>
    <th><f:message key="help.table.url" /></th>
    <th><f:message key="help.table.regex" /></th>
  </tr>
  <tr>
    <td><f:message key="help.table.subject.1" /></td>
    <td><f:message key="help.table.url.1" /></td>
    <td><f:message key="help.table.regex.1" /></td>
  </tr>
</table>