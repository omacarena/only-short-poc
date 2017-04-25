package sample.multiversion.versiontext;

import sample.multiversion.Core;
import sample.multiversion.ImportantCore;

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

        /**
         * version 1 and 2 loaders
         * - these loaders do not use the root loader - Thread.currentThread().getContextClassLoader()
         * - using the root loader new URLClassLoader(new URL[]{v1, v1Dep}, Thread.currentThread().getContextClassLoader());
         * will solve any class with the root loader and if no class is found then the child loader will be used
         * - because version 3 is loaded from classpath, the root loader should not be used to load version 1 and 2
         * - null needs to be passed to parent argument, else will not work
         */
        URLClassLoader loaderV1 = new URLClassLoader(new URL[]{v1, v1Dep}, null);
        URLClassLoader loaderV2 = new URLClassLoader(new URL[]{v2, v2Dep}, null);

        /**
         * Use custom class loader for loading classes first from children and last from parent
         */
        ParentLastClassLoader loaderV1Alt = new ParentLastClassLoader(new URL[]{v1, v1Dep});
        ParentLastClassLoader loaderV2Alt = new ParentLastClassLoader(new URL[]{v2, v2Dep});
        ParentLastClassLoader loaderV3Alt = new ParentLastClassLoader(new URL[]{});

        // get class from loader
        Class<?> coreV1Class = loaderV1.loadClass("sample.multiversion.ImportantCore");
        Class<?> coreV2Class = loaderV2.loadClass("sample.multiversion.ImportantCore");

        // get class from loader - custom version
        Class<?> coreV1AltClass = loaderV1Alt.loadClass("sample.multiversion.ImportantCore");
        Class<?> coreV2AltClass = loaderV2Alt.loadClass("sample.multiversion.ImportantCore");
        Class<?> coreV3AltClass = loaderV3Alt.loadClass("sample.multiversion.ImportantCore");

        // create class instance
        Object coreV1Instance = coreV1Class.newInstance();
        Object coreV2Instance = coreV2Class.newInstance();

        // create class instance - obtained from custom class loader
        Object coreV1AltInstance = coreV1AltClass.newInstance();
        Object coreV2AltInstance = coreV2AltClass.newInstance();

        // note that this is loaded from classpath
        Core coreV3Instance = new ImportantCore();
        Core coreV3AltInstance = (Core)coreV3AltClass.newInstance();

        // get version
        String v1Str = (String) coreV1Class.getMethod("getVersion").invoke(coreV1Instance);
        String v2Str = (String) coreV2Class.getMethod("getVersion").invoke(coreV2Instance);
        String v1AltStr = (String) coreV1AltClass.getMethod("getVersion").invoke(coreV1AltInstance);
        String v2AltStr = (String) coreV2AltClass.getMethod("getVersion").invoke(coreV2AltInstance);

        String v3Str = coreV3Instance.getVersion();
        String v3AltStr = coreV3AltInstance.getVersion();

        // get version of dependency
        String v1DepStr = (String) coreV1Class.getMethod("getDependencyVersion").invoke(coreV1Instance);
        String v2DepStr = (String) coreV2Class.getMethod("getDependencyVersion").invoke(coreV2Instance);
        String v1AltDepStr = (String) coreV1AltClass.getMethod("getDependencyVersion").invoke(coreV1AltInstance);
        String v2AltDepStr = (String) coreV2AltClass.getMethod("getDependencyVersion").invoke(coreV2AltInstance);

        String v3DepStr = coreV3Instance.getDependencyVersion();
        String v3AltDepStr = coreV3AltInstance.getDependencyVersion();

        System.out.println(String.format("V1 loader :: version = '%s' :: dependency_version = '%s'", v1Str, v1DepStr));
        System.out.println(String.format("V2 loader :: version = '%s' :: dependency_version = '%s'", v2Str, v2DepStr));
        System.out.println(String.format("V3 loader :: version = '%s' :: dependency_version = '%s'", v3Str, v3DepStr));

        System.out.println(String.format("V1 custom loader :: version = '%s' :: dependency_version = '%s'", v1AltStr, v1AltDepStr));
        System.out.println(String.format("V2 custom loader :: version = '%s' :: dependency_version = '%s'", v2AltStr, v2AltDepStr));
        System.out.println(String.format("V3 custom loader :: version = '%s' :: dependency_version = '%s'", v3AltStr, v3AltDepStr));
    }
}
