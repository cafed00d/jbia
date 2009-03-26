<!-- 
 *******************************************************************************
 *  Copyright 2007, Javid Jamae and Peter Johnson
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
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<f:view>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Interest calculator</title>
	</head>
	<body>
	<h1>Interest Calculator</h1>
	<h:form>
		<h:panelGrid columns="2">
			<h:outputLabel value="Present Value ($):" for="presentValue" />
			<h:panelGroup>
				<h:inputText id="presentValue" value="#{calculator.presentValue}" />
				<h:message for="presentValue"></h:message>
			</h:panelGroup>
			<h:outputLabel value="Years:" for="years" />
			<h:panelGroup>
				<h:inputText id="years" value="#{calculator.years}" />
				<h:message for="years"></h:message>
			</h:panelGroup>
		</h:panelGrid>

		<br />

		<h:commandButton value="Calculate"
			actionListener="#{calculator.calculate}" />

	</h:form>

	<hr />
	<h:panelGrid columns="2">
		<h:outputText value="Interest Rate (%):" />
		<h:outputText value="#{calculator.interestRate}" />
		<h:outputText value="Total Interest Earned ($):" />
		<h:outputText value="#{calculator.interestEarned}">
			<f:convertNumber minFractionDigits="2" maxFractionDigits="2" />
		</h:outputText>
		<h:outputText value="Future Value ($):" />
		<h:outputText value="#{calculator.futureValue}">
			<f:convertNumber minFractionDigits="2" maxFractionDigits="2" />
		</h:outputText>
	</h:panelGrid>
	</body>
</f:view>
</html>