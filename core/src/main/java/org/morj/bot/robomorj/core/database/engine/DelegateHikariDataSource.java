package org.morj.bot.robomorj.core.database.engine;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@Getter
public record DelegateHikariDataSource(HikariDataSource delegate) implements DataSource {

    public Connection getConnection() throws SQLException {
        return this.delegate.getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return this.delegate.getConnection();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return this.delegate.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.delegate.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        this.delegate.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return this.delegate.getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.delegate.getParentLogger();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.delegate.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.delegate.isWrapperFor(iface);
    }

}
