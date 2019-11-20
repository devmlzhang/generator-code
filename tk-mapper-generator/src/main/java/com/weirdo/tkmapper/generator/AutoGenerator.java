package com.weirdo.tkmapper.generator;

import com.weirdo.tkmapper.generator.config.builder.ConfigBuilder;
import com.weirdo.tkmapper.generator.config.po.TableInfo;
import com.weirdo.tkmapper.generator.engine.AbstractTemplateEngine;
import com.weirdo.tkmapper.generator.engine.FreemarkerTemplateEngine;
import com.weirdo.tkmapper.generator.common.StringUtils;
import com.weirdo.tkmapper.generator.config.*;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *   生成文件
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
@Slf4j
public class AutoGenerator {
    /**
     * 配置信息
     */
    protected ConfigBuilder config;
    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;
    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;
    /**
     * 包 相关配置
     */
    private PackageConfig packageConfig;
    /**
     * 模板 相关配置
     */
    private TemplateConfig template;
    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;
    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * 生成代码
     */
    public void execute() {
        log.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(packageConfig, dataSource, strategy, template, globalConfig);
        }
        templateEngine = new FreemarkerTemplateEngine();
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkDirs().batchOutput().open();
        log.debug("==========================文件生成完成！！！==========================");
    }

    /**
     * 开放表信息、预留子类重写
     *
     * @param config 配置信息
     * @return ignore
     */
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * 预处理配置
     *
     * @param config 总配置信息
     * @return 解析数据结果集
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /*
         * 表信息列表
         */
        List<TableInfo> tableList = this.getAllTableInfoList(config);
        for (TableInfo tableInfo : tableList) {
            // 表注解
            tableInfo.setImportPackages(Table.class.getCanonicalName());
            if (tableInfo.isHaveId()) {
                // Id注解
                tableInfo.setImportPackages(Id.class.getCanonicalName());
                if (tableInfo.isHaveIdentityId()) {
                    // 自增注解
                    tableInfo.setImportPackages(KeySql.class.getCanonicalName());
                }
            }
            boolean importSerializable = true;
            if (StringUtils.isNotEmpty(config.getSuperEntityClass())) {
                // 父实体
                tableInfo.setImportPackages(config.getSuperEntityClass());
                importSerializable = false;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
            // Boolean类型is前缀处理
            if (config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()) {
                tableInfo.getFields().stream().filter(field -> "boolean".equalsIgnoreCase(field.getPropertyType()))
                    .filter(field -> field.getPropertyName().startsWith("is"))
                    .forEach(field -> field.setPropertyName(StringUtils.removePrefixAfterPrefixToLower(field.getPropertyName(), 2)));
            }
        }
        return config.setTableInfoList(tableList);
    }
}
