package com.weirdo.tkmapper.generator.config;

import com.weirdo.tkmapper.generator.config.po.TableFill;
import com.weirdo.tkmapper.generator.config.rules.NamingStrategy;
import com.weirdo.tkmapper.generator.common.OthersUtils;
import com.weirdo.tkmapper.generator.common.StringUtils;
import com.weirdo.tkmapper.generator.common.TableInfoHelper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *   策略配置项
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class StrategyConfig {

    /**
     * 是否大写命名
     */
    private boolean isCapitalMode = false;
    /**
     * 是否跳过视图
     */
    private boolean skipView = false;
    /**
     * 数据库表映射到实体的命名策略
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;
    /**
     * 数据库表字段映射到实体的命名策略
     */
    private NamingStrategy columnNaming = NamingStrategy.underline_to_camel;
    /**
     * 表前缀
     */
    @Setter(AccessLevel.NONE)
    private String[] tablePrefix;
    /**
     * 字段前缀
     */
    @Setter(AccessLevel.NONE)
    private String[] fieldPrefix;
    /**
     * 自定义继承的Entity类全称，带包名
     */
    @Setter(AccessLevel.NONE)
    private String superEntityClass;
    /**
     * 自定义基础的Entity类，公共字段
     */
    @Setter(AccessLevel.NONE)
    private String[] superEntityColumns;
    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superControllerClass;
    /**
     * 需要包含的表名，允许正则表达式（与exclude二选一配置）
     */
    @Setter(AccessLevel.NONE)
    private String[] include = null;
    /**
     * 需要排除的表名，允许正则表达式
     */
    @Setter(AccessLevel.NONE)
    private String[] exclude = null;

    /**
     * Boolean类型字段是否移除is前缀（默认 false）<br>
     * 比如 : 数据库字段名称 : 'is_xxx',类型为 : tinyint. 在映射实体的时候则会去掉is,在实体类中映射最终结果为 xxx
     */
    private boolean entityBooleanColumnRemoveIsPrefix = false;
    /**
     * 表填充字段
     */
    private List<TableFill> tableFillList = null;

    /**
     * 大写命名、字段符合大写字母数字下划线命名
     *
     * @param word 待判断字符串
     */
    public boolean isCapitalModeNaming(String word) {
        return isCapitalMode && StringUtils.isCapitalMode(word);
    }

    /**
     * 表名称包含指定前缀
     *
     * @param tableName 表名称
     */
    public boolean containsTablePrefix(String tableName) {
        if (null != tableName) {
            String[] tps = getTablePrefix();
            if (null != tps) {
                for (String tp : tps) {
                    if (tableName.contains(tp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public NamingStrategy getColumnNaming() {
        if (null == columnNaming) {
            // 未指定以 naming 策略为准
            return naming;
        }
        return columnNaming;
    }

    public StrategyConfig setTablePrefix(String... tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    public boolean includeSuperEntityColumns(String fieldName) {
        if (null != superEntityColumns) {
            for (String column : superEntityColumns) {
                if (column.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public StrategyConfig setSuperEntityColumns(String... superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
        return this;
    }

    public StrategyConfig setInclude(String... include) {
        this.include = include;
        return this;
    }

    public StrategyConfig setExclude(String... exclude) {
        this.exclude = exclude;
        return this;
    }

    public StrategyConfig setFieldPrefix(String... fieldPrefixs) {
        this.fieldPrefix = fieldPrefixs;
        return this;
    }

    public StrategyConfig setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
        return this;
    }


    /**
     * <p>
     * 设置实体父类，该设置自动识别公共字段<br/>
     * 属性 superEntityColumns 改配置无需再次配置
     * </p>
     * <p>
     * 注意！！字段策略要在设置实体父类之前有效
     * </p>
     *
     * @param clazz 实体父类 Class
     * @return
     */
    public StrategyConfig setSuperEntityClass(Class<?> clazz) {
        return setSuperEntityClass(clazz, null);
    }

    /**
     * <p>
     * 设置实体父类，该设置自动识别公共字段<br/>
     * 属性 superEntityColumns 改配置无需再次配置
     * </p>
     *
     * @param clazz        实体父类 Class
     * @param columnNaming 字段命名策略
     * @return
     */
    public StrategyConfig setSuperEntityClass(Class<?> clazz, NamingStrategy columnNaming) {
        if (null != columnNaming) {
            this.columnNaming = columnNaming;
        }
        String pkg = ClassUtils.getPackageName(clazz);
        if (StringUtils.isNotEmpty(pkg)) {
            pkg += "." + clazz.getSimpleName();
        } else {
            pkg = clazz.getSimpleName();
        }
        this.superEntityClass = pkg;
        convertSuperEntityColumns(clazz);
        return this;
    }

    /**
     * <p>
     * 父类 Class 反射属性转换为公共字段
     * </p>
     *
     * @param clazz 实体父类 Class
     */
    protected void convertSuperEntityColumns(Class<?> clazz) {
        List<Field> fields = TableInfoHelper.getAllFields(clazz);
        if (!OthersUtils.isEmpty(fields)) {
            this.superEntityColumns = fields.stream().map(field -> {
                if (null == columnNaming || columnNaming == NamingStrategy.no_change) {
                    return field.getName();
                }
                return StringUtils.camelToUnderline(field.getName());
            }).collect(Collectors.toSet()).stream().toArray(String[]::new);
        }
    }
}
