package com.sb.downport;

public class InvalidContentException extends Exception {
   private static final long serialVersionUID = 4186150748634131925L;

   public InvalidContentException(String msg) {
      super(msg);
   }
}
