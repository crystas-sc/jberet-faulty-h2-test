package org.capps.testutil;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.inject.spi.Producer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

// @ApplicationScoped
// @Priority(1)

public class FaultyDataSourceProducer{}

// public class FaultyDataSourceProducer implements Producer<AgroalDataSource> {
//     private static final Logger LOGGER = Logger.getLogger(FaultyDataSourceProducer.class.getName());

//     @Inject
//     @ConfigProperty(name = "quarkus.datasource.jdbc.url", defaultValue = "jdbc:h2:mem:db;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:init.sql'")
//     String url;

//     @Inject
//     @ConfigProperty(name = "quarkus.datasource.username", defaultValue = "sssss")
//     String user;

//     @Inject
//     @ConfigProperty(name = "quarkus.datasource.password", defaultValue = "")
//     String password;

//     @Inject
//     @ConfigProperty(name = "quarkus.datasource.jdbc.max-size", defaultValue = "10")
//     int maxSize;

//     @Inject
//     @ConfigProperty(name = "quarkus.datasource.active", defaultValue = "true")
//     boolean datasourceActive;

//     public void onStart(@Observes @Initialized(ApplicationScoped.class) Object init) {
//         LOGGER.info("FaultyDataSourceProducer ApplicationScoped initialized");
//         init();
//     }

//     // @PostConstruct
//     public void init() {
//         LOGGER.info("Initializing FaultyDataSourceProducer");
//         produce(null); // Ensure the data source is produced at initialization
//     }

//     @Override
//     public AgroalDataSource produce(CreationalContext<AgroalDataSource> ctx) {
//         if (!datasourceActive) {
//             throw new RuntimeException("Datasource '<default>' was deactivated through configuration properties.");
//         }
//         AgroalDataSourceConfigurationSupplier configurationSupplier = new AgroalDataSourceConfigurationSupplier()
//                 .connectionPoolConfiguration(cp -> cp
//                         .connectionFactoryConfiguration(
//                                 cf -> cf.jdbcUrl(url).principal(() -> user).credential(password))
//                         .maxSize(maxSize));
//         try {
//             return new FaultyDataSource(AgroalDataSource.from(configurationSupplier));
//         } catch (SQLException e) {
//             throw new RuntimeException("Failed to create AgroalDataSource", e);
//         }
//     }

//     @Override
//     public Set<InjectionPoint> getInjectionPoints() {
//         return Collections.emptySet(); // No injection points for this producer
//     }

// 	@Override
// 	public void dispose(AgroalDataSource instance) {
// 		// TODO Auto-generated method stub
// 		// throw new UnsupportedOperationException("Unimplemented method 'dispose'");
// 	}

// }
