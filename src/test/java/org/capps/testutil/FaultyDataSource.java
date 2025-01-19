package org.capps.testutil;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalDataSourceMetrics;
import io.agroal.api.AgroalPoolInterceptor;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;

public class FaultyDataSource implements AgroalDataSource {

    private final AgroalDataSource delegate;

    Set<FaultyState> faultableQueries = Set.of();

    // public FaultyDataSource(String url, String user, String password) {
    // this.delegate = new JdbcDataSource();
    // this.delegate.setURL(url);
    // this.delegate.setUser(user);
    // this.delegate.setPassword(password);
    // }
    public FaultyDataSource(AgroalDataSource dataSource) {
        this.delegate = dataSource;
    }
    // public FaultyDataSource(JdbcDataSource JdbcDataSource) {
    // this.delegate = JdbcDataSource;
    // }

    // Add custom logic here (e.g., simulate failures)
    private boolean simulateFailure = false;

    // public void setSimulateFailure(boolean simulateFailure) {
    // this.simulateFailure = simulateFailure;
    // }

    public void setFaultableQueries(Set<FaultyState> faultableQueries) {
        this.faultableQueries = faultableQueries;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // if (simulateFailure) {
        // throw new SQLException("Simulated database failure");
        // }
        try {
            System.out.println("FaultyDataSource getConnection");
            Connection connection = delegate.getConnection();
            System.out.println("FaultyDataSource after delegate. getConnection");
            return new FaultyDbConnection(connection, faultableQueries);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if (simulateFailure) {
            throw new SQLException("Simulated database failure");
        }
        return delegate.getConnection(username, password);
    }

    // Delegate other methods to the JdbcDataSource
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return delegate.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delegate.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return delegate.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        delegate.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        delegate.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return delegate.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate.getParentLogger();
    }

    @Override
    public AgroalDataSourceConfiguration getConfiguration() {
        return delegate.getConfiguration();
    }

    @Override
    public AgroalDataSourceMetrics getMetrics() {
        return delegate.getMetrics();
    }

    @Override
    public void flush(FlushMode mode) {
        delegate.flush(mode);
    }

    @Override
    public void setPoolInterceptors(Collection<? extends AgroalPoolInterceptor> interceptors) {
        delegate.setPoolInterceptors(interceptors);
    }

    @Override
    public List<AgroalPoolInterceptor> getPoolInterceptors() {
        return delegate.getPoolInterceptors();
    }

    @Override
    public void close() {
        delegate.close();
    }
}
