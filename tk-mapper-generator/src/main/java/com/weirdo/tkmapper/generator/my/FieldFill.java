package com.weirdo.tkmapper.generator.my;

/**
 * <p>
 *  字段填充策略枚举类
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public enum FieldFill {
    /**
     * 默认不处理
     */
    DEFAULT,
    /**
     * 插入时填充字段
     */
    INSERT,
    /**
     * 更新时填充字段
     */
    UPDATE,
    /**
     * 插入和更新时填充字段
     */
    INSERT_UPDATE
}
