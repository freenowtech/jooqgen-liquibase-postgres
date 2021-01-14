# Publishing to JCenter

[ ![Download](https://api.bintray.com/packages/mytaxi/oss/jooqcontainers/images/download.svg) ](https://bintray.com/mytaxi/oss/jooqcontainers/_latestVersion)

This is a guide on how to publish to JCenter manually.

## Bintray repository

This library is published to JCenter through Bintray. The package can be found
at https://bintray.com/mytaxi/oss/jooqcontainers.

## Find your credentials

- Open https://bintray.com/profile/edit
- Click **API Key**
- Use the **Copy to Clipboard** or **Show** button to copy the API Key

## Setup Maven

- Open `~/.m2/settings.xml` with your favorite editor
- Add a server block to the servers list as follows:

```xml

<settings>
    <servers>
        <server>
            <id>bintray-mytaxi-oss</id>
            <username>your-bintray-username</username>
            <password>the-api-key</password>
        </server>
    </servers>
</settings>
```

## Set the right Java version

Make sure to use the correct version of the JDK. This lib targets Java 8.

### Using jEnv (optional)

[jEnv](https://www.jenv.be/) is a tool for quickly switching Java versions. Once it's installed:

- Run `jenv enable-plugin maven` to enable Maven support
- Run `jenv enable-plugin export` to enable exporting `JAVA_HOME`
- Run `jenv add /path/to/jdk` to add a JDK to jEnv
- Run `jenv local 1.8` to set JDK 1.8 locally
    - a `.java-version` file will be created, but it's already in `.gitignore`
- Run `java -version` to verify the version was set correctly
- Run `mvn -version` to verify the version was also applied to Maven

## Release and Publish

Prepare the new release by running

```sh
mvn release:prepare -B
```

this will increase the version to the next patch version (e.g. 1.0.0 -> 1.0.1). To set a different version use

```sh
mvn release:prepare -B -DreleaseVersion=1.1.0
```

Once the process is done, run

```sh
mvn release:perform
```

There's no need to run `mvn deploy`, it will be done during the "perform" phase.