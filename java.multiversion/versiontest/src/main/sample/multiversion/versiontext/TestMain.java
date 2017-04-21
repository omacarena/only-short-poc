package sample.multiversion.versiontext;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class TestMain {

    public static void main(String[] args)
            throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        // multiple versions of the same library to be used at the same time
        URL v1 = Paths.get("./../v1/build/libs/v1.jar").toUri().toURL();
        URL v2 = Paths.get("./../v2/build/libs/v2.jar").toUri().toURL();

        // library dependencies
        URL v1Dep = Paths.get("./../v1dep/build/libs/v1dep.jar").toUri().toURL();
        URL v2Dep = Paths.get("./../v2dep/build/libs/v2dep.jar").toUri().toURL();

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

        // nested class loaders
        URLClassLoader loaderV1Dep = new URLClassLoader(new URL[]{v1Dep}, currentClassLoader);
        URLClassLoader loaderV2Dep = new URLClassLoader(new URL[]{v2Dep}, currentClassLoader);
        URLClassLoader loaderV1 = new URLClassLoader(new URL[]{v1}, loaderV1Dep);
        URLClassLoader loaderV2 = new URLClassLoader(new URL[]{v2}, loaderV2Dep);

        // one class loader per version
//        URLClassLoader loaderV1 = new URLClassLoader(new URL[]{v1, v1Dep}, currentClassLoader);
//        URLClassLoader loaderV2 = new URLClassLoader(new URL[]{v2, v2Dep}, currentClassLoader);

        // get class from loader
        Class<?> coreV1Class = loaderV1.loadClass("sample.multiversion.ImportantCore");
        Class<?> coreV2Class = loaderV2.loadClass("sample.multiversion.ImportantCore");

        // create class instance
        Object coreV1Instance = coreV1Class.newInstance();
        Object coreV2Instance = coreV2Class.newInstance();

        // get version
        String v1Str = (String) coreV1Class.getMethod("getVersion").invoke(coreV1Instance);
        String v2Str = (String) coreV2Class.getMethod("getVersion").invoke(coreV2Instance);

        // get version of dependency
        String v1DepStr = (String) coreV1Class.getMethod("getDependencyVersion").invoke(coreV1Instance);
        String v2DepStr = (String) coreV2Class.getMethod("getDependencyVersion").invoke(coreV2Instance);

        System.out.println(String.format("V1 loader :: version = '%s' :: dependency_version = '%s'", v1Str, v1DepStr));
        System.out.println(String.format("V2 loader :: version = '%s' :: dependency_version = '%s'", v2Str, v2DepStr));
    }
}
