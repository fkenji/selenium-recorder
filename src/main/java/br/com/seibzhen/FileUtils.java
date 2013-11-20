package br.com.seibzhen;

public class FileUtils {

    private static final String PACKAGE_CLASS_SEPARATOR = ".";

    public static String folderNameBasedOn(Class clazz) {
        return clazz.getPackage().getName() + PACKAGE_CLASS_SEPARATOR + clazz.getSimpleName();
    }
}