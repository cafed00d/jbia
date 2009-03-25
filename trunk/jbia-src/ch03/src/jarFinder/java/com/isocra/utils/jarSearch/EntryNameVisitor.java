/**
 * Copyright Isocra Ltd 2004
 * You can use, modify and freely distribute this file as long as you credit Isocra Ltd.
 * There is no explicit or implied guarantee of functionality associated with this file, use it at your own risk.
 */

package com.isocra.utils.jarSearch;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;

/**
 * Checks to see if the name of the entry matches the pattern we're looking for
 */
public class EntryNameVisitor extends EntryVisitor {

    /** Store the pattern we're looking for */
    private Pattern _pattern;
    /** A string buffer to put the results in */
    private StringBuffer _output;
    /** A count telling us how many matches we've got so that we can display this at the end */
    private int _count = 0;

    /** Take in a string pattern that we can use for matching against jar entry names.
     * @param patternStr The pattern is given to Pattern.compile and is a regular expression.
     * @see java.util.regex.Pattern
     */
    public EntryNameVisitor(String patternStr) {
        _pattern = Pattern.compile(patternStr);
        _output = new StringBuffer();
    }

    /** look at the name of the entry and see if it matches, if so, add its name to the output.
     * @param jar the jar file we're currently searching (we need this so that we can output the name)
     * @param entry the entry that we're trying to match against.
     */
    public void visit(JarFile jar, JarEntry entry) {
        Matcher matcher = _pattern.matcher(entry.getName());
        if (matcher.matches()) {
            _output.append(jar.getName()+": "+entry.getName()+"\n");
            _count++;
        }
    }

    /** Look at the name of the file and see if it matches, if so add its name to the output
     * @param file the file we're trying to match against.
     */
    public void visit(File file) {
        Matcher matcher = _pattern.matcher(file.getName());
        if (matcher.matches()) {
            _output.append(file.getAbsolutePath()+"\n");
            _count++;
        }
    }

    /** Check to see if we've found anything and if not say so, otherwise return our results
     * @return a string containing a list of all the matching entries or files plus the number of entries,
     * or a message saying none found
     */
    public String getResults() {
        if (_count == 0) {
            return "No entries found matching \""+_pattern.pattern()+"\"";
        } else {
            _output.append(_count+" entries found.");
            return _output.toString();
        }
    }
}
