package com.example.coffemachine.services;
import com.example.coffemachine.repositories.DbConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.annotation.PostConstruct;
import java.sql.Connection;
@Service
public class PostgreConnector implements DbConnector<Connection>
{
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    BasicDataSource _ds;

    @Value("${spring.datasource.url}")
    private String _connStr;
    @Value("${spring.datasource.username}")
    private String usern;
    @Value("${spring.datasource.password}")
    private String pass;

    @PostConstruct
    private void postConstruct()
    {
        _ds = new BasicDataSource();
        _ds.setDriverClassName(JDBC_DRIVER);
        _ds.setUrl(_connStr);
        _ds.setUsername(usern);
        _ds.setPassword(pass);

    }

    @Override
    public Connection getSession() throws Exception
    {
        Connection connection = _ds.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }
}

