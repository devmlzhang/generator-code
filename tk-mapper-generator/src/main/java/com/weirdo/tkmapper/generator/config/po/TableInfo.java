package com.weirdo.tkmapper.generator.config.po;

import com.weirdo.tkmapper.generator.my.OthersUtils;
import com.weirdo.tkmapper.generator.my.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * <p>
 *  表信息，关联到当前字段信息
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class TableInfo {

    private final Set<String> importPackages = new HashSet<>();
    private boolean haveId;
    private boolean haveIdentityId;
    private String idType;
    private String idName;
    private String name;
    private String comment;
    private String entityName;
    private String modelName;
    private String lowerModelName;
    private String lowerEntityName;
    private String lowerName;
    private String upperName;
    private String mapperName;
    private String lowerMapperName;
    private String serviceName;
    private String lowerServiceName;
    private String serviceImplName;
    private String controllerName;
    private String entityPath;
    private List<TableField> fields;
    private List<TableField> commonFields;
    private String fieldNames;

    public TableInfo setEntityName(String entityName) {
        this.entityName = entityName;
        this.lowerEntityName = StringUtils.firstToLowerCase(entityName);
        this.entityPath = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        return this;
    }

    public TableInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        this.lowerServiceName = StringUtils.firstToLowerCase(serviceName);
        return this;
    }

    public TableInfo setFields(List<TableField> fields) {
        if (!OthersUtils.isEmpty(fields)) {
            this.fields = fields;
            // 收集导入包信息
            for (TableField field : fields) {
                if (null != field.getColumnType() && null != field.getColumnType().getPkg()) {
                    importPackages.add(field.getColumnType().getPkg());
                }
                if (field.isKeyFlag()) {
                    // 主键
                    importPackages.add(Id.class.getCanonicalName());
                    // 自增
                    if (field.isKeyIdentityFlag()) {
                        importPackages.add(KeySql.class.getCanonicalName());
                    }
                }
                if (null != field.getFill()) {
                    // 填充字段
                    importPackages.add(Transient.class.getCanonicalName());
                }
            }
        }
        return this;
    }

    public TableInfo setName(String name) {
        this.name = name;
        this.upperName = StringUtils.firstToUpperCase(StringUtils.underlineToCamel(name));
        this.lowerName = StringUtils.firstToLowerCase(this.upperName);
        return this;
    }
    public TableInfo setModelName(String modelName) {
        this.modelName = modelName;
        this.lowerModelName = StringUtils.firstToLowerCase(modelName);
        return this;
    }
    public TableInfo setMapperName(String mapperName) {
        this.mapperName = mapperName;
        this.lowerMapperName = StringUtils.firstToLowerCase(mapperName);
        return this;
    }

    public TableInfo setImportPackages(String pkg) {
        importPackages.add(pkg);
        return this;
    }

    /**
     * 转换filed实体为 xml mapper 中的 base column 字符串信息
     */
    public String getFieldNames() {
        if (OthersUtils.isEmpty(fieldNames)) {
            StringBuilder names = new StringBuilder();
            IntStream.range(0, fields.size()).forEach(i -> {
                TableField fd = fields.get(i);
                if (i == fields.size() - 1) {
                    names.append(fd.getName());
                } else {
                    names.append(fd.getName()).append(", ");
                }
            });
            fieldNames = names.toString();
        }
        return fieldNames;
    }
}
