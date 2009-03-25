/**
 * Copyright Isocra Ltd 2004
 * You can use, modify and freely distribute this file as long as you credit Isocra Ltd.
 * There is no explicit or implied guarantee of functionality associated with this file, use it at your own risk.
 */

package com.isocra.utils.jarSearch;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;

/** EntryVisitor is the abstract class for visitors which will be called on every file inside
 * a Jar */
public abstract class EntryVisitor {

    /** visit is called on every entry in the current jar
     * @param jar the jar file we're currently searching (we need this so that we can output the name)
     * @param entry the entry that we're trying to match against.
     */
    public abstract void visit(JarFile jar, JarEntry entry);

    /** visit a file rather than a jar entry
     * @param file the file we're trying to match against.
     */
    public abstract void visit(File file);

    /** get the results as a string, like that we have a choice about how to output them */
    public abstract String getResults();

    /** Takes in a file returns true if the name of the file ends in .JAR (regardless of case)  */
    public boolean isJar(File file) {
        return (file.getName().toLowerCase().endsWith(".jar"));
    }

    /** Search through the jar file visiting every entry to see if it matches */
    public void searchJar(File jarFile) {
        try {
            JarFile jar = new JarFile(jarFile, false, JarFile.OPEN_READ);
            // From the jar file we can get an enumeration of entries (each file in the jar)
            for (Enumeration entries = jar.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = (JarEntry) entries.nextElement();
                this.visit(jar, entry);
            }
        } catch (IOException e) {
            e.printStackTrace();  // @todo handle this exception
        }
    }

    /** Search through a directory looking for jars or class files */
    public void searchDir(File directory) {
        File[] files = directory.listFiles();
        // Now we go through each file in the current directory to see if it's a jar or a directory
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (isJar(file)) {
                // It's a jar
                this.searchJar(file);
            } else if (file.isDirectory()) {
                // It's a directory , so call ourselves recursively
                this.searchDir(file);
            } else {
                // If it's any other sort of file, visit it anyway
                visit(file);
            }
        }

    }
}
