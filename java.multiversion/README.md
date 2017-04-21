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
- **v1dep** - dependency of library version 1
- **v2dep** - dependency of library version 2
- **versiontest** - application using library version 1 and 2 at the same time

**Output**

V1 loader :: version = 'core-v1' :: dependency_version = 'core-dep-v1'
V2 loader :: version = 'core-v2' :: dependency_version = 'core-dep-v2'

Meaning that the two library versions and their associated dependencies are loaded correctly.


**NOTE:** created using IntelliJ IDEA 2017.1

**NOTE:** `Core` interface could be part of another project which is used by projects `v1` and `v2`, which could allow us to cast the newly created instance and work in a typesafe manner. But this might not be doable every time.
