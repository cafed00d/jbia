# Editing with Eclipse #

This wiki page outlines the steps necessary to work with the source code in Eclipse.

# Quick Start #

Import the existing chxx projects into Eclipse and set these classpath variables:

  * JBOSS\_HOME – where you unzipped the JBoss AS binary ZIP file (do not use the directory where the build placed JBoss AS because that directory will not have a ‘default’ configuration which is required by some of the projects)
  * PORTAL\_HOME – where you unzipped the JBoss Portal binary ZIP file (not the AS + Portal bundle!)

# Step-by-Step Details #

First, either get the source from Subversion (see the Source tab) or download the source zip file (see the Downloads tab). If you download the zip file, unzip it.

There will be a jbia-src directory that contains index.html and the various chapter directories (ch00..ch15). Open the index.html file with your browser and follow the directions for configuring the build environment. Try out one of the builds to ensure that your settings are correct. For example:

```
cd jbia-src/ch01
ant 01
```

Start Eclipse. When it asks for a workspace directory, give it the jbia-src directory. For example, if I unpacked the jbia-src-1.1.zip file at /user/peter/jbia-src (and thus had file /user/peter/jbia-src/index.html), then I would tell Eclipse to use /user/peter/jbia-src as the workspace.

Each chapter has its own project, so the next step is to import those projects. To do this, in Eclipse:

  1. Select File | Import
  1. In the Import dialog select General | Existing Projects into Workspace. Click Next.
  1. On the Import Project dialog click the Browse button next to Select Root Directory.
  1. On the Browse for Folder dialog, click OK (the jbia-src directory should be selected by default, which is what you want)
  1. You should see all of the chxx projects selected in the Import Projects dialog. Click Finish.

All the chxx projects should show up in the Project Explorer, but many of them will have red Xs next to them. That is because Eclipse cannot find the necessary libraRies for the projects. Let’s fix that next.

You need to set two classpath variables within Eclipse. To set the variables, got to Windows | Preferences and on the Preferences dialog select Java | Build Path | Classpath Variables. In the Classpath Variables panel click New and then enter the variables mentioned under Quick Start above.

Most of the red Xs should now be gone. There will still be a red X on the following projects, but this is expected and does not impact the build for those projects:

  * ch03, ch05 – there are duplicate class files in different source directories.
  * ch07, ch08, ch09 – the source files use a conditional compilation mechanism and thus are not valid Java source files

You can edit the source files within Eclipse but you must run the builds from the command line – the projects are not set up to use the Eclipse capability to build using an Ant script.

**NOTE:** the jbia-src-1.0.zip download contains obsolete library locations; if you use it you will have to also correct the library locations. The jbia-src-1.1.zip download and Subversion trunk and branches/jbia-src-1.x have the correct locations.