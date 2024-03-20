package 核心;

import rhino.ImporterTopLevel;
import rhino.NativeJavaPackage;

public class 包导入
{
   public static void hh(){
       new ImporterTopLevel().importPackage(new NativeJavaPackage("核心", js.class.getClassLoader()));


    }
}
