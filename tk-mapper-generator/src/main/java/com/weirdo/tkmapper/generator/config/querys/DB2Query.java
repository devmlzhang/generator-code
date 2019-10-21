package com.weirdo.tkmapper.generator.config.querys;

import com.weirdo.tkmapper.generator.my.DbType;

/**
 * <p>
 *   DB2 表数据查询
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public class DB2Query extends AbstractDbQuery {


    @Override
    public DbType dbType() {
        return DbType.DB2;
    }


    @Override
    public String tablesSql() {
        return "SELECT * FROM SYSCAT.TABLES where tabschema=current schema";
    }


    @Override
    public String tableFieldsSql() {
        return "SELECT *  FROM syscat.columns WHERE tabschema=current schema AND tabname='%s'";
    }


    @Override
    public String tableName() {
        return "TABNAME";
    }


    @Override
    public String tableComment() {
        return "REMARKS";
    }


    @Override
    public String fieldName() {
        return "COLNAME";
    }


    @Override
    public String fieldType() {
        return "TYPENAME";
    }


    @Override
    public String fieldComment() {
        return "REMARKS";
    }


    @Override
    public String fieldKey() {
        return "IDENTITY";
    }

}
