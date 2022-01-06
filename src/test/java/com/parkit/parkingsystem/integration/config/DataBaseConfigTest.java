package com.parkit.parkingsystem.integration.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataBaseConfigTest {

    private DataBaseConfig dataBaseConfig;

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    private void setUpPerTest() {
        dataBaseConfig = new DataBaseConfig();
    }

    @Test
    public void closeConnectionTest() {

        dataBaseConfig.closeConnection(connection);

        try {
            verify(connection, times(1)).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closeConnectionNullTest() {

        dataBaseConfig.closeConnection(null);

        try {
            verify(connection, never()).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closePreparedStatementTest() {

        dataBaseConfig.closePreparedStatement(preparedStatement);

        try {
            verify(preparedStatement, times(1)).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closePreparedStatementNullTest() {

        dataBaseConfig.closePreparedStatement(null);

        try {
            verify(preparedStatement, never()).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closeResultSetTest() {

        dataBaseConfig.closeResultSet(resultSet);

        try {
            verify(resultSet, times(1)).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closeResultSetNullTest() {

        dataBaseConfig.closeResultSet(null);

        try {
            verify(resultSet, never()).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

