package com.weirdo.tkmapper.generator.my;


import java.lang.reflect.Field;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author ML.Zhang
 * @since  2019/10/21
 */
public class TableInfoHelper {

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     *
     * @param clazz 反射类
     * @return 属性集合
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = ReflectionKit.getFieldList(ClassUtils.getUserClass(clazz));
        if (!OthersUtils.isEmpty(fieldList)) {
            return fieldList.stream()
                .filter(i -> {
                    /* 过滤注解非表字段属性 */
                    TableField tableField = i.getAnnotation(TableField.class);
                    return (tableField == null || tableField.exist());
                }).collect(toList());
        }
        return fieldList;
    }
}
