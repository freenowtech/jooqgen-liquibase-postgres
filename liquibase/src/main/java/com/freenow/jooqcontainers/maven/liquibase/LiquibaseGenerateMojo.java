package com.freenow.jooqcontainers.maven.liquibase;

import com.freenow.jooqcontainers.core.LiquibaseGenerator;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jooq.meta.jaxb.Configuration;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class LiquibaseGenerateMojo extends AbstractMojo
{
    @Parameter(property = "generate.jooq", required = true)
    private Configuration jooq = new Configuration();

    @Parameter(property = "generate.liquibase", required = true)
    private Map<String, Object> liquibase;

    @Parameter(property = "generate.testcontainers", required = true)
    private Map<String, Object> testcontainers;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;


    @Override
    public void execute()
    {
        String liquibaseChangeLogFile = (String) liquibase.get("changeLogFile");
        String tcJdbcUrl = (String) testcontainers.get("jdbcUrl");
        String tcDatabaseName = (String) testcontainers.get("databaseName");
        String tcDatabaseVersion = (String) testcontainers.get("databaseVersion");

        LiquibaseGenerator.setJooqTargetDirectory(jooq);

        project.addCompileSourceRoot(jooq.getGenerator().getTarget().getDirectory());

        LiquibaseGenerator generator;

        if (tcJdbcUrl != null)
        {
            generator = new LiquibaseGenerator(tcJdbcUrl, jooq, liquibaseChangeLogFile);
        }
        else
        {
            generator = new LiquibaseGenerator(tcDatabaseName, tcDatabaseVersion, jooq, liquibaseChangeLogFile);
        }

        generator.generate();
    }
}
