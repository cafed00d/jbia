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
using System;
using System.Collections.Generic;
using System.Text;
using Client.salestax;

namespace Client
{

  /// <summary>
  /// Web service client that obtains sales tax rates. To run, provide one or more
  /// two character state code and the client prints the sales tax rate for each
  /// state.
  /// </summary>
  class Client
  {
    /// <summary>
    /// Main body of program
    /// </summary>
    /// <param name="args">List of two character state codes.</param>
    static void Main(string[] args)
    {
      if (args.Length == 0)
      {
        Console.WriteLine("usage: Client <list-of-states>");
      }
      else
      {
        SalesTaxService svc = new SalesTaxService();
        for (int i = 0; i < args.Length; i++)
        {
          /*
           * Unlike Java, C# has preprocessor directives, so we make use of them!
           * We assume RPC-binding by default.
           */
#if soapdoc
          getRate rr = new salestax.getRate();
          rr.arg0 = args[i];
          getRateResponse resp = svc.getRate(rr);
          double rate = resp.@return;
#else
          double rate = svc.getRate(args[i]);
#endif
          Console.WriteLine("Sales tax for " + args[i] + " is " + rate);
        }
      }
      Console.WriteLine();
      /*
       * Wait for input, otherwise the command window disappears!
       */
      Console.WriteLine("enter anything to exit...");
      Console.ReadLine();
    }
  }
}
