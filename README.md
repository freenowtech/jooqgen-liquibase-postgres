# JOOQ Containers

[ ![Download](https://api.bintray.com/packages/mytaxi/oss/jooqcontainers/images/download.svg) ](https://bintray.com/mytaxi/oss/jooqcontainers/_latestVersion)

JOOQ Codegen + Testcontainers = JOOQ Containers

## Examples

**Postgres**

```xml
<plugin>
    <groupId>com.free-now.jooqcontainers</groupId>
    <artifactId>jooqcontainers-liquibase-pg-maven</artifactId>
    <version>1.0.0</version>
    <configuration>
        <jooq> <!-- Complete JOOQ configuration -->
            <generator>
                <database>
                    <inputSchema>public</inputSchema>
                </database>
                <generate>
                    <records>true</records>
                    <pojos>true</pojos>
                    <daos>true</daos>
                </generate>
                <target>
                    <packageName>com.example.yourpackage</packageName>
                </target>
            </generator>
        </jooq>

        <liquibase>
            <changeLogFile>src/main/resources/liquibase/changelog.xml</changeLogFile>
        </liquibase>

        <databaseVersion>9.6</databaseVersion>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
 ```

**Postgis**
```xml
<plugin>
    <groupId>com.free-now.jooqcontainers</groupId>
    <artifactId>jooqcontainers-liquibase-pg-maven</artifactId>
    <version>1.0.0</version>
    <configuration>
        <jooq> <!-- Complete JOOQ configuration -->
            <generator>
                <database>
                    <include>yourprefix.*</include>
                    <inputSchema>public</inputSchema>
                </database>
                <generate>
                    <records>true</records>
                    <pojos>true</pojos>
                    <daos>true</daos>
                </generate>
                <target>
                    <packageName>com.example.yourpackage</packageName>
                </target>
            </generator>
        </jooq>

        <liquibase>
            <changeLogFile>src/main/resources/liquibase/changelog.xml</changeLogFile>
        </liquibase>
        
        <databaseName>postgis</databaseName>
        <databaseVersion>9.6</databaseVersion>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
 ```  

**Custom database configuration**
```xml
<plugin>
    <groupId>com.free-now.jooqcontainers</groupId>
    <artifactId>jooqcontainers-liquibase-maven</artifactId>
    <version>1.0.0</version>
    <configuration>
        <jooq> <!-- Complete JOOQ configuration -->
            <generator>
                <database>
                    <include>yourprefix.*</include>
                    <inputSchema>yourmysqldb</inputSchema>
                </database>
                <generate>
                    <records>true</records>
                    <pojos>true</pojos>
                    <daos>true</daos>
                </generate>
                <target>
                    <packageName>com.example.yourpackage</packageName>
                </target>
            </generator>
        </jooq>

        <liquibase>
            <changeLogFile>src/main/resources/liquibase/changelog.xml</changeLogFile>
        </liquibase>
        
        <testcontainers>
            <jdbcUrl>jdbc:tc:mysql:5.7.22://hostname/yourmysqldb?TC_DAEMON=true</jdbcUrl>
        </testcontainers>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
    
    <dependencies>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mysql</artifactId>
            <version>1.12.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.17</version>
        </dependency>
    </dependencies>

</plugin>
 ```  

# What it does

1. Starts a Testcontainer provided docker container (e.g PostgreSQL)
2. Applies a migration script to the loaded database
3. Generates JOOQ classes for the source project connecting to postgres container

# Supported technologies

* Currently only Liquibase migrations are supported, but Flyway will be considered for the neat future
* PostgreSQL and Postgis are supported out of the box with the `jooqcontainers-liquibase-pg-maven` plugin
* Other databases can be started using the `jooqcontainers-liquibase-maven` plugin, more information on how to use
 Testcontainers JDBC construct at https://www.testcontainers.org/modules/databases/
* All regular JOOQ configuration can be used inside the `<jooq>...</jooq>` configuration tags 

# Problems and solutions

If generated classes fail to compile, 
1. include ` /target/generated-sources/jooq/` folder to corresponding compiler plugin.
2. If the `NoClassDefError` happens, this means the class files are missing. Add the following plugin
```
 <plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>add-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>${project.build.directory}/generated-sources/jooq</source>
                </sources>
            </configuration>
        </execution>
    </executions>
 </plugin>
```

# Contributors: Publishing a new version

A guide for contributors can be found at [PUBLISHING.md](PUBLISHING.md)