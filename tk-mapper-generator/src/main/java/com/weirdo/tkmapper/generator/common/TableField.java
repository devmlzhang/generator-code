package com.weirdo.tkmapper.generator.common;

import java.lang.annotation.*;

/**
 * <p>
 *   表字段标识
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {

    /**
     * 字段值（驼峰命名方式，该值可无）
     */
    String value() default "";

    /**
     * 当该Field为类对象时, 可使用#{对象.属性}来映射到数据表.
     */
    String el() default "";

    /**
     * 是否为数据库表字段
     * <p>默认 true 存在，false 不存在</p>
     */
    boolean exist() default true;

    /**
     * 字段 where 实体查询比较条件
     * <p>默认 `=` 等值</p>
     */
    String condition() default "";

    /**
     * 字段 update set 部分注入, 该注解优于 el 注解使用
     * <p>例如：@TableField(.. , update="%s+1") 其中 %s 会填充为字段</p>
     * <p>输出 SQL 为：update 表 set 字段=字段+1 where ...</p>
     * <p>例如：@TableField(.. , update="now()") 使用数据库时间</p>
     * <p>输出 SQL 为：update 表 set 字段=now() where ...</p>
     */
    String update() default "";

    /**
     * 字段验证策略
     * <p>默认追随全局配置</p>
     */
    FieldStrategy strategy() default FieldStrategy.DEFAULT;

    /**
     * 字段自动填充策略
     */
    FieldFill fill() default FieldFill.DEFAULT;

    /**
     * 是否进行 select 查询
     * <p>大字段可设置为 false 不加入 select 查询范围</p>
     */
    boolean select() default true;

    /**
     * 是否保持使用全局的 Format 的值
     * <p> 只生效于 既设置了全局的 Format 也设置了上面 {@link #value()} 的值 </p>
     * <li> 如果是 false , 全局的 Format 不生效 </li>
     *
     * @since 3.1.1
     */
    boolean keepGlobalFormat() default false;
}

