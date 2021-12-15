package com.sb.downport;

import java.io.PrintStream;

public class Logger {
   static PrintStream out;

   static {
      out = System.out;
   }

   public static void setLogStream(PrintStream ps) {
      out = ps;
   }

   public static void log(Object obj) {
      if(obj instanceof Throwable) {
         ((Throwable)obj).printStackTrace();
      } else {
         out.println(obj);
      }

   }
}
