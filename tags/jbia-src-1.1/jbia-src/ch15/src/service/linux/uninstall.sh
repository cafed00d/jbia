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
# This script unregisters the application server services on Linux, and removes
# the account created for the services. You must run this as root.
#

# Set the account name
ACCT="jboss"

# Stop the services:
service jbia-${jboss.server} stop
service jbia-${jboss.server.2} stop

# Unregister the services:
chkconfig --del jbia-${jboss.server}
chkconfig --del jbia-${jboss.server.2}

# Remove the scripts from the /etc/init.d directory:
rm -f /etc/init.d/jbia-${jboss.server}
rm -f /etc/init.d/jbia-${jboss.server.2}

# Remove the account used to run the services:
userdel -r $ACCT 

# Don't forget to reassign ownership of the JBOSS_HOME directory!
# Replace ??? with your account.
#chown -R ??? ${home.jboss}