@echo off
REM ****************************************************************************
REM  Copyright 2008, Javid Jamae and Peter Johnson
REM  
REM  Licensed under the Apache License, Version 2.0 (the "License"); you may not
REM  use this file except in compliance with the License. You may obtain a copy
REM  of the License at
REM 
REM     http://www.apache.org/licenses/LICENSE-2.0
REM  
REM  Unless required by applicable law or agreed to in writing, software
REM  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
REM  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
REM  License for the specific language governing permissions and limitations
REM  under the License.
REM ****************************************************************************
REM
REM This script runs the store client for the messaging example.
REM 
REM ----------------------------------------------------------------------------
setlocal

REM Initialize the environment:
call %~dp0\setcp.bat

REM Invoke the application, letting the user know what is being run:
set APP=%JAVA% -cp %CLASSPATH% %KEYSTORE% org.jbia.jms.sofaspuds.Store
echo %APP%
%APP%
