#!/bin/sh
#
# $xId: jboss_init_redhat.sh 60995 2007-02-28 11:35:31Z dimitris@jboss.org $
#
# JBoss Control Script
#
# To use this script run it as root - it will switch to the specified user
#
# Here is a little (and extremely primitive) startup/shutdown script
# for RedHat systems. It assumes that JBoss lives in /usr/local/jboss,
# it's run by user 'jboss' and JDK binaries are in /usr/local/jdk/bin.
# All this can be changed in the script itself. 
#
# Either modify this script for your requirements or just ensure that
# the following variables are set correctly before calling the script.

# These lines required by the chkconfig tool:
# chkconfig: 345 90 10
# description: ${param.service.desc}
# processname: ${param.service.name}

#define where jboss is - this is the directory containing directories log, bin, conf etc
JBOSS_HOME="${home.jboss}"

#define the user under which jboss will run, or use 'RUNASIS' to run as the current user
JBOSS_USER=${JBOSS_USER:-"jboss"}

#make sure java is in your path
JAVAPTH="${env.JAVA_HOME}/bin"

#configuration to use, usually one of 'minimal', 'default', 'all'
JBOSS_CONF="${param.jboss.server}"

#if JBOSS_HOST specified, use -b to bind jboss services to that address
JBOSS_HOST="${param.jboss.host}"
JBOSS_BIND_ADDR=${JBOSS_HOST:+"-b $JBOSS_HOST"}

#if JBOSS_BINDSET specified, set -Djboss.service.binding.set
JBOSS_BINDSET="${param.jboss.binding}"
JBOSS_BIND_SET=${JBOSS_BINDSET:+"-Djboss.service.binding.set=$JBOSS_BINDSET"}

#define the classpath for the shutdown class
JBOSSCP=${JBOSSCP:-"$JBOSS_HOME/bin/shutdown.jar:$JBOSS_HOME/client/jnet.jar"}

#define the script to use to start jboss
JBOSSSH=${JBOSSSH:-"$JBOSS_HOME/bin/run.sh -c $JBOSS_CONF $JBOSS_BIND_ADDR $JBOSS_BIND_SET"}

# Define the location of the console log
JBOSS_CONSOLE="$JBOSS_HOME/bin/run_${param.jboss.server}.log"

# Define the server option used to stop the app server
JBOSS_STOP="-s ${param.jboss.host}:${param.jndi.port}"


if [ "$JBOSS_USER" = "RUNASIS" ]; then
  SUBIT=""
else
  SUBIT="su - $JBOSS_USER -c "
fi

if [ -n "$JBOSS_CONSOLE" -a ! -d "$JBOSS_CONSOLE" ]; then
  # ensure the file exists
  touch $JBOSS_CONSOLE
  if [ ! -z "$SUBIT" ]; then
    chown $JBOSS_USER $JBOSS_CONSOLE
  fi 
fi

if [ -n "$JBOSS_CONSOLE" -a ! -f "$JBOSS_CONSOLE" ]; then
  echo "WARNING: location for saving console log invalid: $JBOSS_CONSOLE"
  echo "WARNING: ignoring it and using /dev/null"
  JBOSS_CONSOLE="/dev/null"
fi

#define what will be done with the console log
JBOSS_CONSOLE=${JBOSS_CONSOLE:-"/dev/null"}

JBOSS_CMD_START="$JBOSSSH"
JBOSS_CMD_STOP=${JBOSS_CMD_STOP:-"java -classpath $JBOSSCP org.jboss.Shutdown --shutdown $JBOSS_STOP"}

if [ -z "`echo $PATH | grep $JAVAPTH`" ]; then
  export PATH=$PATH:$JAVAPTH
fi

if [ ! -d "$JBOSS_HOME" ]; then
  echo JBOSS_HOME does not exist as a valid directory : $JBOSS_HOME
  exit 1
fi

case "$1" in
start)
    echo JBOSS_CMD_START = $JBOSS_CMD_START
    cd $JBOSS_HOME/bin
    if [ -z "$SUBIT" ]; then
        eval $JBOSS_CMD_START >${JBOSS_CONSOLE} 2>&1 &
    else
        $SUBIT "$JBOSS_CMD_START >${JBOSS_CONSOLE} 2>&1 &" 
    fi
    ;;
stop)
    echo JBOSS_CMD_STOP = $JBOSS_CMD_STOP
    if [ -z "$SUBIT" ]; then
        $JBOSS_CMD_STOP
    else
        $SUBIT "$JBOSS_CMD_STOP"
    fi 
    ;;
restart)
    $0 stop
    $0 start
    ;;
*)
    echo "usage: $0 [start|stop|restart|help]"
esac

