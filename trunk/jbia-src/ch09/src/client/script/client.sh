#!/bin/sh
## *****************************************************************************
##  Copyright 2008, Javid Jamae and Peter Johnson
##
##  Licensed under the Apache License, Version 2.0 (the "License"); you may not
##  use this file except in compliance with the License. You may obtain a copy
##  of the License at
##
##     http://www.apache.org/licenses/LICENSE-2.0
##
##  Unless required by applicable law or agreed to in writing, software
##  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
##  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
##  License for the specific language governing permissions and limitations
##  under the License.
## *****************************************************************************
##
## This script runs the client for the Web service example.
##
## -----------------------------------------------------------------------------

## Initialize the environment:
CLASSPATH=${jar.path}

## Invoke the application, letting the user know what is being run:
APP="${home.jboss}/bin/wsrunclient.sh -classpath $CLASSPATH org.jbia.ws.Client $*"
echo $APP
$APP
