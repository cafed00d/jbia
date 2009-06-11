/*
 *  Copyright 2008, Javid Jamae and Peter Johnson
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */
package org.jbia.perf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple program that analyzes verbose:gc output and converts it into a
 * comma-separated-value formatted file.
 */
public class Analyzer {

  /**
   * This is the regular expression pattern used to extract verbose:gc data.
   */
  private static final String VERBOSE_GC_PATTERN = "\\[(?:Full |)GC (\\d*)K->(\\d*)K\\(\\d*K\\), ([\\d.]*) secs\\]";

  /**
   * This is the header used for verbose:gc data.
   */
  private static final String VERBOSE_GC_HDR = "Before,After,Seconds";

  /**
   * This is the command line argument used for verbose:gc data.
   */
  private static final String VERBOSE_GC_ARG = "-v";

  /**
   * This is the regular expression pattern used to extract PrintGCDetails data.
   * This pattern picks out only the young generation and total heap size data,
   * but it does gather that data from both minor and full collections.
   */
  private static final String PRINTGCDETAILS_PATTERN = "\\[(?:Full |)GC \\[PSYoungGen: (\\d*)K->(\\d*)K\\(\\d*K\\)\\](?: \\[PSOldGen: \\d*K->\\d*K\\(\\d*K\\)\\]|) (\\d*)K->(\\d*)K\\(\\d*K\\)(?: \\[PSPermGen: \\d*K->\\d*K\\(\\d*K\\)\\]|), ([\\d.]*) secs\\]";

  /**
   * This is the header used for PrintGCDetails data.
   */
  private static final String PRINTGCDETAILS_HDR = "YoungBefore,YoungAfter,HeapBefore,HeapAfter,Seconds";

  /**
   * This is the command line argument used for PrintGCDetails data.
   */
  private static final String PRINTGCDETAILS_ARG = "-d";

  /**
   * This is the regular expression pattern used to extract PrintHeapAtGC data.
   * This pattern picks out the young and old generations sizes, along with the
   * survivor space usage percentage.
   */
  private static final String PRINTHEAPATGC_PATTERN = "\\{Heap before.*\\s*PSYoungGen\\s*total \\d*K, used (\\d*)K.*\\s*.*\\s*.*\\s*.*\\s*PSOldGen\\s*total \\d*K, used (\\d*)K.*\\s*.*\\s*.*\\s*.*\\s*Heap after.*\\s*PSYoungGen\\s*total \\d*K, used (\\d*)K.*\\s*.*\\s*from space \\d*K, (\\d*)%.*\\s*.*\\s*PSOldGen\\s*total \\d*K, used (\\d*)K";

  /**
   * This is the header used for PrintHeapAtGC data.
   */
  private static final String PRINTHEAPATGC_HDR = "YoungBefore,OldBefore,YoungAfter,SurvivorSpace,OldAfter";

  /**
   * This is the command line argument used for PrintHeapAtGC data.
   */
  private static final String PRINTHEAPATGC_ARG = "-h";

  /**
   * The various garbage collection data options.
   */
  private enum Option {

    /**
     * Used when the file contains verbose:gc data.
     */
    VERBOSE_GC,

    /**
     * Used when the file contains PrintGCDetails data.
     */
    PRINTGCDETAILS,

    /**
     * Used when the file contains PrintHeapAtGC data.
     */
    PRINTHEAPATGC
  }

  /**
   * The regular expression patterns, organized for easy access.
   */
  private static final String[] pattern = {
      VERBOSE_GC_PATTERN,
      PRINTGCDETAILS_PATTERN,
      PRINTHEAPATGC_PATTERN };

  /**
   * The column headers, organized for easy access.
   */
  private static final String[] header = {
      VERBOSE_GC_HDR,
      PRINTGCDETAILS_HDR,
      PRINTHEAPATGC_HDR };


  /**
   * Reads the file and extracts the garbage collection data.
   * 
   * @param option
   *          Identifies which gc data to extract.
   * @param filename
   *          Identifies the file containing gc data.
   * @throws Exception
   *           Bad things happened.
   */
  private static void run(Option option, String filename) throws Exception {

    /*
     * Read the entire file into a string in memory:
     */
    InputStream fin = new FileInputStream(filename);
    int iSize = fin.available();
    byte mvIn[] = new byte[iSize];
    fin.read(mvIn, 0, iSize);
    fin.close();
    String strText = new String(mvIn);

    /*
     * Get the output file ready and print the header:
     */
    File outFile = new File(filename + ".csv");
    System.out.println("GC data placed in file " + outFile.getAbsolutePath());
    PrintStream fout = new PrintStream(new FileOutputStream(outFile));
    fout.println(header[option.ordinal()]);

    /*
     * Parse the GC data from the file, extracting the interesting pieces of
     * data, and writing them to the cvs file:
     */
    Pattern p = Pattern.compile(pattern[option.ordinal()]);
    Matcher m = p.matcher(strText);
    StringBuffer buf = new StringBuffer(1024);
    while (m.find()) {
      buf.setLength(0);
      for (int i = 1; i <= m.groupCount(); i++) {
        buf.append(m.group(i));
        buf.append(",");
      }
      fout.println(buf.toString());
    }

    /*
     * All done, close the file:
     */
    fout.close();
  }


  /**
   * Main body of program.
   * 
   * @param args
   *          The command line arguments.
   * @throws Exception
   *           Bad things happened.
   */
  public static void main(String[] args) throws Exception {

    /*
     * Validate the arguments:
     */
    if (args.length == 0) {
      printUsage();
    } else {
      /*
       * Determine if an option was given:
       */
      int inxFile = 0;
      Option option = Option.VERBOSE_GC;
      if (VERBOSE_GC_ARG.equals(args[0])) {
        inxFile = 1;
      } else if (PRINTGCDETAILS_ARG.equals(args[0])) {
        inxFile = 1;
        option = Option.PRINTGCDETAILS;
      } else if (PRINTHEAPATGC_ARG.equals(args[0])) {
        inxFile = 1;
        option = Option.PRINTHEAPATGC;
      }

      /*
       * Make sure we have enough arguments:
       */
      if (args.length == inxFile) {
        printUsage();
      } else {

        /*
         * Let's run...
         */
        run(option, args[inxFile]);
      }
    }
  }


  /**
   * Print the program's usages, assuming the existence of the run script.
   */
  private static void printUsage() {
    System.out.println("usage: analyzer [-v|-d|-h] file-name");
    System.out.println("where");
    System.out.println("  file-name - name of file containing gc data");
    System.out.println("  -v        - file contains verbose:gc data (default)");
    System.out.println("  -d        - file contains PrintGCDetails data");
    System.out.println("  -h        - file contains PrintHeapAtGC data");
  }
}
