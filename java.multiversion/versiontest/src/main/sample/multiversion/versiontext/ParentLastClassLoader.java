package sample.multiversion.versiontext;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ParentLastClassLoader extends ClassLoader {

    private ClassLoader parentClassLoader;
    private URLClassLoader noParentClassLoader;

    public ParentLastClassLoader(URL[] urls, ClassLoader parent) {
        super(parent);
        this.noParentClassLoader = new URLClassLoader(urls, null);
    }

    public ParentLastClassLoader(URL[] urls) {
        super(Thread.currentThread().getContextClassLoader());
        this.noParentClassLoader = new URLClassLoader(urls, null);
    }

    public ParentLastClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(parent);
        this.noParentClassLoader = new URLClassLoader(urls, null, factory);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            // resolve using child class loader
            return noParentClassLoader.loadClass(name);
        } catch (ClassNotFoundException ex) {
            // resolve using parent class loader
            return super.loadClass(name, resolve);
        }
    }
}