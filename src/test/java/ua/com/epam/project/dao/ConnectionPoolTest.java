package ua.com.epam.project.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ConnectionPoolTest {

    @Mock
    private Connection c;

    @Mock
    private Context ctx;

    @Mock
    private DataSource ds;

    @Mock
    private AutoCloseable autoCloseable;

    @InjectMocks
    private ConnectionPool connectionPool;

    @Test
    void getInstance() {
        ConnectionPool instance = ConnectionPool.getInstance();
        assertNotNull(instance);
    }

    @Test
    void getConnection() throws NamingException, SQLException {
        lenient().when(ctx.lookup("jdbc/final_project")).thenReturn(ds);
        lenient().when(ds.getConnection()).thenReturn(c);

        Connection actual = connectionPool.getConnection();
        assertNull(actual);
    }

    @Test
    void close() throws Exception {
        doThrow(Exception.class).when(autoCloseable).close();
        connectionPool.close(autoCloseable);
    }

    @Test
    void rollback() throws SQLException {
        doThrow(SQLException.class).when(c).rollback();
        connectionPool.rollback(c);
    }
}