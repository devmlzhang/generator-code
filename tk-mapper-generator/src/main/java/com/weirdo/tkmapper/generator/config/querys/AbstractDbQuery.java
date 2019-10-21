package com.weirdo.tkmapper.generator.config.querys;

import com.weirdo.tkmapper.generator.config.IDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *   表数据查询抽象类
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public abstract class AbstractDbQuery implements IDbQuery {


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }


    @Override
    public String[] fieldCustom() {
        return null;
    }
}
