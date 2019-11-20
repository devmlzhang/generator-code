package com.weirdo.tkmapper.generator.config;

import com.weirdo.tkmapper.generator.config.rules.IColumnType;

/**
 * <p>
 *   数据库字段类型转换
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public interface ITypeConvert {


    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return ignore
     */
    IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType);
}
