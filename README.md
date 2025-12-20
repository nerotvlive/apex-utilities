# APEX UTILITIES `2025.12.1`

### Java 21 library designed to simplify the development of Java Desktop apps.<br>

---

### Implementation

You can implement this library via [Maven](#implement-via-maven), [Gradle (Groovy)](#implement-via-gradle-groovy),  [Gradle (Kotlin)](#implement-via-gradle-kotlin), [SBT](#implement-via-sbt) and as local library

#### Implement via Maven
```
<repositories>
    <!--Other repositories-->
    <repository>
        <id>nerofy-releases</id>
        <name>Nerofy Network Maven repository</name>
        <url>https://maven.nrfy.net/releases</url>
    </repository>
</repositories>
```
```
<dependencies>
    <!--Other dependencies-->
    <dependency>
        <groupId>org.zyneonstudios.apex</groupId>
        <artifactId>utilities</artifactId>
        <version>LATEST</version>
    </dependency>
</dependencies>
```

#### Implement via Gradle (Groovy)
```
repositories {
    maven {
        name "zyneonstudiosRepoReleases"
        url "https://maven.zyneonstudios.com/releases"
    }
}
```
```
dependencies {
    implementation 'org.zyneonstudios.apex:utilities:+'
}
```

#### Implement via Gradle (Kotlin)
```
repositories {
    maven {
        name = "zyneonstudiosRepoReleases"
        url = uri("https://maven.zyneonstudios.com/releases")
    }
}
```
```
dependencies {
    implementation('org.zyneonstudios.apex:utilities:+')
}
```

#### Implement via SBT
```
resolvers += "zyneonstudios-repo-releases" at "https://maven.zyneonstudios.com/releases"
```
```
libraryDependencies += "org.zyneonstudios.apex" % "utilities" % "LATEST_VERSION"
```