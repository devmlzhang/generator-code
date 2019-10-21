package com.weirdo.tkmapper.generator.config.po;

import com.weirdo.tkmapper.generator.my.FieldFill;
import lombok.Data;

/**
 * <p>
 *   字段填充
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
public class TableFill {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 忽略类型
     */
    private FieldFill fieldFill;

    public TableFill(String fieldName, FieldFill ignore) {
        this.fieldName = fieldName;
        this.fieldFill = ignore;
    }
}
