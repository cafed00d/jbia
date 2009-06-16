/**
 * Copyright Isocra Ltd 2004
 * You can use, modify and freely distribute this file as long as you credit Isocra Ltd.
 * There is no explicit or implied guarantee of functionality associated with this file, use it at your own risk.
 */

package com.isocra.utils.jarSearch;

import com.isocra.utils.jarSearch.EntryNameVisitor;
import com.isocra.utils.jarSearch.EntryVisitor;

import java.io.File;

/**
 * This class searches through the jars in a searchJar path and looks at the names of the contents to match the
 * specified searchJar string.
 */
public class DirectorySearcher {

    /** A main function to start off the process */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("DirectorySearcher usage: DirectorySearcher <directory> <pattern>\n" +
                    "Where <pattern> is a regular expression that will be matched against the\n" +
                    "entries in the jars e.g.\n" +
                    ".*class will match all class files;\n" +
                    ".*ObjectPool.*class will return any classes starting with ObjectPool\n" +
                    "org/apache/commons/pool.*class will return all classes in that package");
        } else {
            File directory = new File(args[0]);
            String pattern = args[1];
            if (directory.isDirectory()) {
                EntryVisitor visitor = new EntryNameVisitor(pattern);
                visitor.searchDir(directory);
                System.out.println(visitor.getResults());
            } else {
                System.out.println("DirectorySearcher: "+directory+" must be a directory");
            }
        }
        System.out.println("Directory searcher: http://www.isocra.com");
    }
}
