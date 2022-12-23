package com.zerobase.fastlms.util;

import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {

      public static boolean equals(String plaintext,String hased){
          if(plaintext == null || plaintext.length() <1){
            return false;
          }
          if(hased==null || hased.length()<1){
            return false;
          }

          return BCrypt.checkpw(plaintext,hased);
      }
      public static String encPassword(String plainText){
          if(plainText == null || plainText.length()<1){
            return "";
          }
          return BCrypt.hashpw(plainText,BCrypt.gensalt());
      }

}
