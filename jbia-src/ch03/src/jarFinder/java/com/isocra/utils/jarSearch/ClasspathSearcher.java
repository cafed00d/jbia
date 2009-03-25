/**
 * Copyright Isocra Ltd 2004
 * You can use, modify and freely distribute this file as long as you credit Isocra Ltd.
 * There is no explicit or implied guarantee of functionality associated with this file, use it at your own risk.
 */

package com.isocra.utils.jarSearch;

import java.util.StringTokenizer;
import java.io.File;

/**
 * This class searches through the jars in the classes in the CLASSPATH and looks at the names of the contents to
 * match the specified searchJar string.
 */
public class ClasspathSearcher {

    /** Go through the classpath and look at every class or jar file to see if it will match against the pattern */
    private void search(EntryVisitor visitor) {
        String classpath = System.getProperty("java.class.path");
        String pathSep = System.getProperty("path.separator");
        StringTokenizer pathEls = new StringTokenizer(classpath, pathSep);
        while (pathEls.hasMoreTokens())
        {
            String element = pathEls.nextToken();
            File file = new File(element);
            if (visitor.isJar(file)) {
                // If it's a jar, then search the jar
                visitor.searchJar(file);
            } else if (file.isDirectory()) {
                // If it's a directory, then search the directory
                visitor.searchDir(file);
            } else {
                // Otherwise, visit it anyway, so that we match against specific files
                visitor.visit(file);
            }
        }
    }

    /** A main function to start off the process */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("ClasspathSearcher usage: ClasspathSearcher <pattern>\n" +
                    "Where <pattern> is a regular expression that will be matched against the\n" +
                    "entries in the jars e.g.\n" +
                    ".*class will match all class files;\n" +
                    ".*ObjectPool.*class will return any classes starting with ObjectPool\n" +
                    "org/apache/commons/pool.*class will return all classes in that package");
        } else {
            String pattern = args[0];
            ClasspathSearcher searcher = new ClasspathSearcher();
            EntryVisitor visitor = new EntryNameVisitor(pattern);
            searcher.search(visitor);
            System.out.println(visitor.getResults());
        }
        System.out.println("Classpath searcher: http://www.isocra.com");
    }

}
