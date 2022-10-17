package ua.com.epam.project.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class ConnectionPool {
    private static ConnectionPool INSTANCE;
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool() {
    }

    public static synchronized ConnectionPool getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ConnectionPool();
        return INSTANCE;
    }

    /**
     * Function to return connection
     *
     * @return return connection from pool
     */
    public Connection getConnection() {
        Connection c = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/final_project");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
            LOG.error("problem get connection");
        }
        return c;
    }

    /**
     * Procedure to close connection
     *
     * @param c auto closeable object
     */
    public void close(AutoCloseable c) {
        try {
            if (c != null)
                c.close();
        } catch (Exception e) {
            LOG.error("problem to close autocloseable object");
        }
    }

    /**
     * Procedure to rollback connection
     *
     * @param con connection
     */
    public void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException ex) {
            LOG.error("problem to rollback autocloseable object");
        }
    }
}