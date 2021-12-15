package com.sb.downport;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StringResource {
   static Properties resourceMap = new Properties();
   static Properties ColorResourceMap = new Properties();

   public static String getString(String id) {
      return (String)resourceMap.get(id);
   }
   
   public static String getColorString(String id) {
	      return (String)ColorResourceMap.get(id);
   }
   
   public static Color getColor(String id) {
	      return Color.decode((String)ColorResourceMap.get(id));
   }

   public static void loadResource(String lang) throws FileNotFoundException, IOException {
      try {
         String e = "/lang/en.txt";
         resourceMap.load(StringResource.class.getResourceAsStream(e));
      } catch (Exception var3) {
         String langFile = "lang/en.txt";
         resourceMap.load(new FileReader(langFile));
      }

   }
   public static void loadColorResource(String lang) throws FileNotFoundException, IOException {
	      try {
	         String e = "/color/" + lang +".txt";
	         ColorResourceMap.load(StringResource.class.getResourceAsStream(e));
	      } catch (Exception var3) {
	         String langFile = "color/" + lang +".txt";
	         ColorResourceMap.load(new FileReader(langFile));
	      }
   }
}
