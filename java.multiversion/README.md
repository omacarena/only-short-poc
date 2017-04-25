Short sample to show that multiple versions of the same library can be loaded dynamically and used the same time.

Build is managed using **Gradle**.

The solution already defines a gradle wrapper in build.gradle file.

Therefore **gradlew** command can be used.
 
Useful commands for this test:
- **run the app:** gradlew build :versiontest:run
- **clean everything:** gradlew clean

Gradle projects: [each has build.gradle file]
- **v1** - library version 1
- **v2** - library version 2
- **v3** - library version 3
- **v1dep** - dependency of library version 1
- **v2dep** - dependency of library version 2
- **v3dep** - dependency of library version 3
- **versiontest** - application using library version 1, 2, 3 at the same time but which uses version3 directly (using classpath)

**Output**

Version 3 is used directly by being present on the classpath. 
Therefore, by using the root loader as a parent for URLClassLoader used to load version 1 and 2, then the classes from Version3 (classpath) will be used yielding:

- V1 loader :: version = 'core-v3' :: dependency_version = 'core-dep-v3'
- V2 loader :: version = 'core-v3' :: dependency_version = 'core-dep-v3'
- V3 loader :: version = 'core-v3' :: dependency_version = 'core-dep-v3'

In order to fix this

- V1 loader :: version = 'core-v1' :: dependency_version = 'core-dep-v1'
- V2 loader :: version = 'core-v2' :: dependency_version = 'core-dep-v2'

Meaning that the two library versions and their associated dependencies are loaded correctly.


**NOTE:** created using IntelliJ IDEA 2017.1

**NOTE:** `Core` interface could be part of another project which is used by projects `v1` and `v2`, which could allow us to cast the newly created instance and work in a typesafe manner. But this might not be doable every time.
