package com.weirdo.tkmapper.generator.config.converts;

import com.weirdo.tkmapper.generator.config.GlobalConfig;
import com.weirdo.tkmapper.generator.config.ITypeConvert;
import com.weirdo.tkmapper.generator.config.rules.DbColumnType;
import com.weirdo.tkmapper.generator.config.rules.IColumnType;
import com.weirdo.tkmapper.generator.config.rules.DateType;

/**
 * <p>
 *  SQLServer 字段类型转换
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public class SqlServerTypeConvert implements ITypeConvert {

    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char") || t.contains("xml")) {
            return DbColumnType.STRING;
        } else if (t.contains("bigint")) {
            return DbColumnType.LONG;
        } else if (t.contains("int")) {
            return DbColumnType.INTEGER;
        } else if (t.contains("date") || t.contains("time")) {
            switch (globalConfig.getDateType()) {
                case ONLY_DATE:
                    return DbColumnType.DATE;
                case SQL_PACK:
                    switch (t) {
                        case "date":
                            return DbColumnType.DATE_SQL;
                        case "time":
                            return DbColumnType.TIME;
                        default:
                            return DbColumnType.TIMESTAMP;
                    }
                case TIME_PACK:
                    switch (t) {
                        case "date":
                            return DbColumnType.LOCAL_DATE;
                        case "time":
                            return DbColumnType.LOCAL_TIME;
                        default:
                            return DbColumnType.LOCAL_DATE_TIME;
                    }
                default:
                    return DbColumnType.DATE;
            }
        } else if (t.contains("text")) {
            return DbColumnType.STRING;
        } else if (t.contains("bit")) {
            return DbColumnType.BOOLEAN;
        } else if (t.contains("decimal") || t.contains("numeric")) {
            return DbColumnType.DOUBLE;
        } else if (t.contains("money")) {
            return DbColumnType.BIG_DECIMAL;
        } else if (t.contains("binary") || t.contains("image")) {
            return DbColumnType.BYTE_ARRAY;
        } else if (t.contains("float") || t.contains("real")) {
            return DbColumnType.FLOAT;
        }
        return DbColumnType.STRING;
    }

}
