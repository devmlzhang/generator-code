package com.weirdo.tkmapper.generator.config.po;

import com.weirdo.tkmapper.generator.config.rules.DbColumnType;
import com.weirdo.tkmapper.generator.config.rules.IColumnType;
import com.weirdo.tkmapper.generator.common.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * <p>
 *  表字段信息
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class TableField {

    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;
    private String name;
    private String type;
    private String propertyName;
    private IColumnType columnType;
    private String comment;
    private String fill;
    /**
     * 自定义查询字段列表
     */
    private Map<String, Object> customMap;

    public String getPropertyType() {
        if (null != columnType) {
            return columnType.getType();
        }
        return null;
    }

    /**
     * 按JavaBean规则来生成get和set方法
     */
    public String getCapitalName() {
        if (propertyName.length() <= 1) {
            return propertyName.toUpperCase();
        }
        String setGetName = propertyName;
        if (DbColumnType.BASE_BOOLEAN.getType().equalsIgnoreCase(columnType.getType())) {
            setGetName = StringUtils.removeIsPrefixIfBoolean(setGetName, Boolean.class);
        }
        // 第一个字母 小写、 第二个字母 大写 ，特殊处理
        String firstChar = setGetName.substring(0, 1);
        if (Character.isLowerCase(firstChar.toCharArray()[0])
                && Character.isUpperCase(setGetName.substring(1, 2).toCharArray()[0])) {
            return firstChar.toLowerCase() + setGetName.substring(1);
        }
        return firstChar.toUpperCase() + setGetName.substring(1);
    }
}
