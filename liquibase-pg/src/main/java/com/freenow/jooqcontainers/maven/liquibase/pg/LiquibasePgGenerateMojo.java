package com.freenow.jooqcontainers.maven.liquibase.pg;

import com.freenow.jooqcontainers.core.LiquibaseGenerator;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jooq.meta.jaxb.Configuration;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class LiquibasePgGenerateMojo extends AbstractMojo
{
    @Parameter(property = "generate.jooq", required = true)
    private Configuration jooq = new Configuration();

    @Parameter(property = "generate.liquibase", required = true)
    private Map<String, Object> liquibase;

    @Parameter(property = "generate.testcontainers", required = true)
    private Map<String, Object> testcontainers;

    @Parameter(property = "generate.databaseName", defaultValue = "postgresql", required = false)
    private String databaseName = "postgresql";

    @Parameter(property = "generate.databaseVersion", required = false)
    private String databaseVersion;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;


    @Override
    public void execute()
    {
        String liquibaseChangeLogFile = (String) liquibase.get("changeLogFile");
        LiquibaseGenerator.setJooqTargetDirectory(jooq);
        LiquibaseGenerator generator;

        project.addCompileSourceRoot(jooq.getGenerator().getTarget().getDirectory());

        if (databaseVersion != null)
        {
            generator = new LiquibaseGenerator(databaseName, databaseVersion, jooq, liquibaseChangeLogFile);
        }
        else
        {
            String tcJdbcUrl = (String) testcontainers.get("jdbcUrl");
            String tcDatabaseName = (String) testcontainers.get("databaseName");
            String tcDatabaseVersion = (String) testcontainers.get("databaseVersion");

            if (tcJdbcUrl != null)
            {
                generator = new LiquibaseGenerator(tcJdbcUrl, jooq, liquibaseChangeLogFile);
            }
            else
            {
                generator = new LiquibaseGenerator(tcDatabaseName, tcDatabaseVersion, jooq, liquibaseChangeLogFile);
            }
        }

        generator.generate();
    }
}
