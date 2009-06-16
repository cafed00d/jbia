#!/bin/sh
#*******************************************************************************
#  Copyright 2008, Javid Jamae and Peter Johnson
# 
#  Licensed under the Apache License, Version 2.0 (the "License"); you may not
#  use this file except in compliance with the License. You may obtain a copy
#  of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
#  
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
#  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
#  License for the specific language governing permissions and limitations
#  under the License.
#*******************************************************************************
#
# This script completes the registration of the two application servers created
# by the target 04 so that they can be run as services. You must be logged in as
# root to run this script.
#

# Set the account name and password
ACCT="jboss"
PWD="${ACCT}pwd"

# Create the account used to run the services:
useradd -p$PWD -m $ACCT 

# Give the account ownership of the application server directory:
chown -R $ACCT ${home.jboss}

# Copy the scripts to the /etc/init.d directory:
cp ${dir.output}/linux/jboss_init_${jboss.server}.sh /etc/init.d/jbia-${jboss.server}
cp ${dir.output}/linux/jboss_init_${jboss.server.2}.sh /etc/init.d/jbia-${jboss.server.2}


# The following steps are commented out so that the instructions for working
# with the 04 target results on Linux are similar to those for Windows, where
# the user must manually register and start the service.

# Register the services so that they will autostart when the system is rebooted:
#chkconfig --add jbia-${jboss.server}
#chkconfig --add jbia-${jboss.server.2}

# Start the services:
#service jbia-${jboss.server} start
#service jbia-${jboss.server.2} start
