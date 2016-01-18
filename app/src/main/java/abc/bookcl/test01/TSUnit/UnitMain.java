package abc.bookcl.test01.TSUnit;

/**
 * Created by NUSW on 2016/1/18.
 */
public class UnitMain {
    //public static native String getStringFormC();

    static {
        //System.loadLibrary("test01");    //defaultConfig.ndk.moduleName
    }
    public static String get_java_str(String msg) {
        return "String from java";
    }
}
