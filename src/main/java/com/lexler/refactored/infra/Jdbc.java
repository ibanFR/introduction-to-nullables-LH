package com.lexler.refactored.infra;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Low level infrastructure wrapper. Annoying to build but extremely reusable in all similar projects
class Jdbc {

    static DataSourceWrapper create(DataSource dataSource) {
        return new RealDataSource(dataSource);
    }

    static DataSourceWrapper createNull() {
        return createNull(List.of());
    }

    static DataSourceWrapper createNull(List<Map<String, Object>> rows) {
        return new StubbedJdbc(rows, false);
    }

    static DataSourceWrapper createNullDown() {
        return new StubbedJdbc(List.of(), true);
    }

    interface DataSourceWrapper {
        ConnectionWrapper getConnection() throws SQLException;
    }

    interface ConnectionWrapper extends AutoCloseable {
        StatementWrapper prepareStatement(String sql) throws SQLException;

        void close() throws SQLException;
    }

    interface StatementWrapper extends AutoCloseable {
        ResultSetWrapper executeQuery() throws SQLException;

        void close() throws SQLException;
    }

    interface ResultSetWrapper {
        boolean next() throws SQLException;

        String getString(String columnLabel) throws SQLException;

        Date getDate(String columnLabel) throws SQLException;
    }


    // Real implementations: pure delegation to JDBC

    private static class RealDataSource implements DataSourceWrapper {
        private final DataSource dataSource;

        RealDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        public ConnectionWrapper getConnection() throws SQLException {
            return new RealConnection(dataSource.getConnection());
        }
    }

    private static class RealConnection implements ConnectionWrapper {
        private final Connection connection;

        RealConnection(Connection connection) {
            this.connection = connection;
        }

        @Override
        public StatementWrapper prepareStatement(String sql) throws SQLException {
            return new RealStatement(connection.prepareStatement(sql));
        }

        @Override
        public void close() throws SQLException {
            connection.close();
        }
    }

    private static class RealStatement implements StatementWrapper {
        private final PreparedStatement preparedStatement;

        RealStatement(PreparedStatement preparedStatement) {
            this.preparedStatement = preparedStatement;
        }

        @Override
        public ResultSetWrapper executeQuery() throws SQLException {
            return new RealResultSet(preparedStatement.executeQuery());
        }

        @Override
        public void close() throws SQLException {
            preparedStatement.close();
        }
    }

    private static class RealResultSet implements ResultSetWrapper {
        private final ResultSet resultSet;

        RealResultSet(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        @Override
        public boolean next() throws SQLException {
            return resultSet.next();
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return resultSet.getString(columnLabel);
        }

        @Override
        public Date getDate(String columnLabel) throws SQLException {
            return resultSet.getDate(columnLabel);
        }
    }


    // Embedded stub: one object plays the whole JDBC chain, answering with the configured rows

    private static class StubbedJdbc implements DataSourceWrapper, ConnectionWrapper, StatementWrapper, ResultSetWrapper {
        private final List<Map<String, Object>> rows;
        private final boolean down;
        private Iterator<Map<String, Object>> queryResults;
        private Map<String, Object> currentRow;

        StubbedJdbc(List<Map<String, Object>> rows, boolean down) {
            this.rows = rows;
            this.down = down;
        }

        @Override
        public ConnectionWrapper getConnection() throws SQLException {
            failIfDown();
            return this;
        }

        @Override
        public StatementWrapper prepareStatement(String sql) {
            return this;
        }

        @Override
        public ResultSetWrapper executeQuery() {
            queryResults = rows.iterator();
            return this;
        }

        private void failIfDown() throws SQLException {
            if (down) {
                throw new SQLException("Nulled Jdbc: the database is down");
            }
        }

        @Override
        public boolean next() {
            if (queryResults == null) {
                throw new IllegalStateException("Nulled Jdbc: next() called before executeQuery()");
            }
            if (!queryResults.hasNext()) {
                return false;
            }
            currentRow = queryResults.next();
            return true;
        }

        @Override
        public String getString(String columnLabel) {
            return column(columnLabel, String.class);
        }

        @Override
        public Date getDate(String columnLabel) {
            return column(columnLabel, Date.class);
        }

        private <T> T column(String columnLabel, Class<T> type) {
            Object value = currentRow.get(columnLabel);
            if (!type.isInstance(value)) {
                throw new IllegalArgumentException("Nulled Jdbc has no " + type.getSimpleName().toLowerCase() + " column '" + columnLabel + "'");
            }
            return type.cast(value);
        }

        @Override
        public void close() {
        }
    }
}
