@echo off
REM JBoss, the OpenSource webOS
REM
REM Distributable under LGPL license.
REM See terms of license at gnu.org.
REM
REM -------------------------------------------------------------------------
REM JBoss Service Script for Windows
REM -------------------------------------------------------------------------


@if not "%ECHO%" == "" echo %ECHO%
@if "%OS%" == "Windows_NT" setlocal
set DIRNAME=%CD%

REM
REM VERSION, VERSION_MAJOR and VERSION_MINOR are populated
REM during the build with ant filter.
REM
set SVCNAME=${param.service.name}
set SVCDISP=${param.service.disp}
set SVCDESC=${param.service.desc}
set JBOSSOPT=-c ${param.jboss.server} -Djboss.service.binding.set=${param.jboss.binding}
set JBOSSSTOP=-s ${param.jboss.host}:${param.jndi.port}
set JAVA_HOME=${env.JAVA_HOME}
set NOPAUSE=Y
set LOCK=.run.${param.jboss.server}.lock
set RUNLOG=run_${param.jboss.server}.log
set STOPLOG=stop_${param.jboss.server}.log
set ERRLOG=error_${param.jboss.server}.log

REM Suppress killing service on logoff event
set JAVA_OPTS=-Xrs

REM Figure out the running mode

if /I "%1" == "install"   goto cmdInstall
if /I "%1" == "uninstall" goto cmdUninstall
if /I "%1" == "start"     goto cmdStart
if /I "%1" == "stop"      goto cmdStop
if /I "%1" == "restart"   goto cmdRestart
if /I "%1" == "signal"    goto cmdSignal
echo Usage: service install^|uninstall^|start^|stop^|restart^|signal
goto cmdEnd

REM jbosssvc retun values
REM ERR_RET_USAGE           1
REM ERR_RET_VERSION         2
REM ERR_RET_INSTALL         3
REM ERR_RET_REMOVE          4
REM ERR_RET_PARAMS          5
REM ERR_RET_MODE            6

:errExplain
if errorlevel 1 echo Invalid command line parameters
if errorlevel 2 echo Failed installing %SVCDISP%
if errorlevel 4 echo Failed removing %SVCDISP%
if errorlevel 6 echo Unknown service mode for %SVCDISP%
goto cmdEnd

:cmdInstall
jbosssvc.exe -imwdc %SVCNAME% "%DIRNAME%" "%SVCDISP%" "%SVCDESC%" service-${param.jboss.server}.bat
if not errorlevel 0 goto errExplain
echo Service %SVCDISP% installed
goto cmdEnd

:cmdUninstall
jbosssvc.exe -u %SVCNAME%
if not errorlevel 0 goto errExplain
echo Service %SVCDISP% removed
goto cmdEnd

:cmdStart
REM Executed on service start
del %LOCK% 2>&1 | findstr /C:"being used" > nul
if not errorlevel 1 (
  echo Service %SVCNAME% is probably already running - locking file %LOCK% in use.
  date /t >> %ERRLOG%
  time /t >> %ERRLOG%
  echo "Service %SVCNAME% is probably already running - locking file %LOCK% in use." >> %ERRLOG%
  goto cmdEnd
)
echo Y > %LOCK%
jbosssvc.exe -p 1 "Starting %SVCDISP%" > %RUNLOG%
call run.bat %JBOSSOPT% < %LOCK% >>%RUNLOG% 2>&1
jbosssvc.exe -p 1 "Shutdown %SVCDISP% service" >> %RUNLOG%
del %LOCK%
goto cmdEnd

:cmdStop
REM Executed on service stop
jbosssvc.exe -p 1 "Shutting down %SVCDISP%" > %STOPLOG%
call shutdown -S %JBOSSSTOP% >>%STOPLOG% 2>&1
jbosssvc.exe -p 1 "Shutdown %SVCDISP% service" >> %STOPLOG%
goto cmdEnd

:cmdRestart
REM Executed on service restart
REM Note: We can only stop and start
jbosssvc.exe -p 1 "Shutting down %SVCDISP%" >> %STOPLOG%
call shutdown -S %JBOSSSTOP% >>%STOPLOG% 2>&1
:waitRun
REM Delete lock file
del %LOCK% > nul 2>&1
REM Wait one second and if lock file still exist try to delete it again
jbosssvc.exe -s 1
if exist "%LOCK%" goto waitRun
echo Y > %LOCK%
jbosssvc.exe -p 1 "Restarting %SVCDISP%" >> %RUNLOG%
call run.bat %JBOSSOPT% < %LOCK% >>%RUNLOG% 2>&1
jbosssvc.exe -p 1 "Shutdown %SVCDISP% service" >> %RUNLOG%
del %LOCK%
goto cmdEnd

:cmdSignal
REM Send signal to the service.
REM Requires jbosssch.dll to be loaded in JVM
@if not ""%2"" == """" goto execSignal
echo Missing signal parameter.
echo Usage: service signal [0...9]
goto cmdEnd
:execSignal
jbosssvc.exe -k%2 %SVCNAME%
goto cmdEnd

:cmdEnd
