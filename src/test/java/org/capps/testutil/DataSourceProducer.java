package org.capps.testutil;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.quarkus.arc.DefaultBean;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.SQLException;

@ApplicationScoped
@Alternative
@Priority(1)
public class DataSourceProducer {

//    @Inject
    AgroalDataSource originalDataSource;

//    @Inject
//    @ConfigProperty(name = "quarkus.datasource.jdbc.url", defaultValue = "jdbc:h2:mem:db;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:init.sql'")
//    String dbUrl;
//    @Inject
//    @ConfigProperty(name = "quarkus.datasource.username", defaultValue = "sa")
//    String dbUserName;
//    @Inject
//    @ConfigProperty(name = "quarkus.datasource.password", defaultValue = "sa")
//    String dbPassword;

    FaultyDataSource faultyDataSource;

    @Inject
    public DataSourceProducer(

            @ConfigProperty(name = "quarkus.datasource.jdbc.url", defaultValue = "jdbc:h2:mem:db;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:init.sql'") String url,
            @ConfigProperty(name = "quarkus.datasource.username", defaultValue = "sssss") String user,
            @ConfigProperty(name = "quarkus.datasource.password", defaultValue = "") String password,
            @ConfigProperty(name = "quarkus.datasource.jdbc.max-size", defaultValue = "10") int maxSize
    ) throws SQLException {
        AgroalDataSourceConfigurationSupplier configurationSupplier = new AgroalDataSourceConfigurationSupplier()
                .connectionPoolConfiguration(cp->cp
                        .connectionFactoryConfiguration(cf->cf.jdbcUrl(url).principal(()->user).credential(password))
                        .maxSize(maxSize));
        this.faultyDataSource = new FaultyDataSource( AgroalDataSource.from(configurationSupplier));
    }

    @Produces
    @DefaultBean
    @ApplicationScoped
    @Default
    @Alternative
    @Priority(1)
    public AgroalDataSource dataSource() {
        return faultyDataSource;
    }

    @Produces
    @DefaultBean
    @ApplicationScoped
    public FaultyDataSource faultyDataSource() {
        return faultyDataSource;
    }
}
