package com.epam.spring.core.dao.db.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Sergiy_Dakhniy
 */
public class InsertStatementFactory {

    private String tableName;
    private String keyName;

    public InsertStatementFactory(String tableName, String keyName){
        this.tableName = tableName;
        this.keyName = keyName;
    };

    public int executeQuery(JdbcTemplate jdbcTemplate, Map<String, ?> params) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(tableName)
                .usingGeneratedKeyColumns(keyName);
        return insert.executeAndReturnKey(params).intValue();
    }
}
