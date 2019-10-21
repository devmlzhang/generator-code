package com.weirdo.tkmapper.generator.config.builder;

import com.weirdo.tkmapper.generator.config.po.TableField;
import com.weirdo.tkmapper.generator.config.po.TableFill;
import com.weirdo.tkmapper.generator.config.po.TableInfo;
import com.weirdo.tkmapper.generator.config.querys.H2Query;
import com.weirdo.tkmapper.generator.config.rules.NamingStrategy;
import com.weirdo.tkmapper.generator.config.*;
import com.weirdo.tkmapper.generator.my.*;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * <p>
 *  配置汇总 传递给文件生成工具
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
@Slf4j
public class ConfigBuilder {

    /**
     * 模板路径配置信息
     */
    private final TemplateConfig template;
    /**
     * 数据库配置
     */
    private final DataSourceConfig dataSourceConfig;
    /**
     * SQL连接
     */
    private Connection connection;
    /**
     * SQL语句类型
     */
    private IDbQuery dbQuery;
    /**
     * 实体类的父类
     */
    private String superEntityClass;
    /**
     * Mapper的父类
     */
    private String superMapperClass;
    /**
     * Controller父类定义
     */
    private String superControllerClass;
    /**
     * 数据库表信息
     */
    private List<TableInfo> tableInfoList;
    /**
     * 包配置详情
     */
    private PackageInfo packageInfo;
    /**
     * 路径配置信息
     */
    private PathInfo pathInfo;
    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;
    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }
        // 模板配置
        if (null == template) {
            this.template = new TemplateConfig();
        } else {
            this.template = template;
        }
        // 包配置
        if (null == packageConfig) {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), new PackageConfig());
        } else {
            handlerPackage(this.template, this.globalConfig.getOutputDir(), packageConfig);
        }
        this.dataSourceConfig = dataSourceConfig;
        handlerDataSource(dataSourceConfig);
        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        handlerStrategy(this.strategyConfig);
    }


    /**
     * 模板路径配置信息
     *
     * @return 所以模板路径配置信息
     */
    public TemplateConfig getTemplate() {
        return template == null ? new TemplateConfig() : template;
    }

    /**
     * 处理包配置
     *
     * @param template  TemplateConfig
     * @param outputDir
     * @param config    PackageConfig
     */
    private void handlerPackage(TemplateConfig template, String outputDir, PackageConfig config) {
        // 包信息
        packageInfo = new PackageInfo()
                .setEntity(joinPackage(config.getParent(), config.getEntity()))
                .setModel(joinPackage((config.getParent()), config.getModel()))
                .setMapper(joinPackage(config.getParent(), config.getMapper()))
                .setService(joinPackage(config.getParent(), config.getService()))
                .setServiceImpl(joinPackage(config.getParent(), config.getServiceImpl()))
                .setController(joinPackage(config.getParent(), config.getController()))
                .setCore(joinPackage(config.getParent(), config.getCore()));

        // 自定义路径
        PathInfo configPathInfo = config.getPathInfo();
        if (configPathInfo != null) {
            pathInfo = configPathInfo;
        } else {
            // 生成路径信息
            pathInfo = new PathInfo();
            if (!OthersUtils.isEmpty(template.getEntity())) {
                pathInfo.setEntity(joinPath(outputDir, packageInfo.getEntity()));
            }
            if (!OthersUtils.isEmpty(template.getModel())) {
                pathInfo.setModel(joinPath(outputDir, packageInfo.getModel()));
            }
            if (!OthersUtils.isEmpty(template.getMapper())) {
                pathInfo.setMapper(joinPath(outputDir, packageInfo.getMapper()));
            }
            if (!OthersUtils.isEmpty(template.getService())) {
                pathInfo.setService(joinPath(outputDir, packageInfo.getService()));
            }
            if (!OthersUtils.isEmpty(template.getServiceImpl())) {
                pathInfo.setServiceImpl(joinPath(outputDir, packageInfo.getServiceImpl()));
            }
            if (!OthersUtils.isEmpty(template.getController())) {
                pathInfo.setController(joinPath(outputDir, packageInfo.getController()));
            }
            if (!OthersUtils.isEmpty(template.getPage())) {
                pathInfo.setPage(joinPath(outputDir, packageInfo.getCore()));
            }
            if (!OthersUtils.isEmpty(template.getPageResult())) {
                pathInfo.setPageResult(joinPath(outputDir, packageInfo.getCore()));
            }
            if (!OthersUtils.isEmpty(template.getAjax())) {
                pathInfo.setAjax(joinPath(outputDir, packageInfo.getCore()));
            }
        }
    }

    /**
     * 处理数据源配置
     *
     * @param config DataSourceConfig
     */
    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbQuery = config.getDbQuery();
    }


    /**
     * 处理数据库表 加载数据库表、列、注释相关数据集
     *
     * @param config StrategyConfig
     */
    private void handlerStrategy(StrategyConfig config) {
        processTypes(config);
        tableInfoList = getTablesInfo(config);
    }


    /**
     * 处理superClassName,IdClassType,IdStrategy配置
     *
     * @param config 策略配置
     */
    private void processTypes(StrategyConfig config) {
        if (OthersUtils.isEmpty(config.getSuperMapperClass())) {
            superMapperClass = ConstVal.SUPER_MAPPER_CLASS;
        } else {
            superMapperClass = config.getSuperMapperClass();
        }
        superEntityClass = config.getSuperEntityClass();
        superControllerClass = config.getSuperControllerClass();
    }


    /**
     * 处理表对应的类名称
     *
     * @param tableList 表名称
     * @param strategy  命名策略
     * @param config    策略配置项
     * @return 补充完整信息后的表
     */
    private List<TableInfo> processTable(List<TableInfo> tableList, NamingStrategy strategy, StrategyConfig config) {
        String[] tablePrefix = config.getTablePrefix();
        for (TableInfo tableInfo : tableList) {
            String entityName = NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategy, tablePrefix));
            if (StringUtils.isNotEmpty(globalConfig.getEntityName())) {
                tableInfo.setEntityName(String.format(globalConfig.getEntityName(), entityName));
            } else {
                tableInfo.setEntityName(entityName + ConstVal.ENTITY);
            }
            if (StringUtils.isNotEmpty(globalConfig.getModelName())) {
                tableInfo.setModelName(String.format(globalConfig.getModelName(), entityName));
            } else {
                tableInfo.setEntityName(entityName + ConstVal.ENTITY);
            }
            if (StringUtils.isNotEmpty(globalConfig.getMapperName())) {
                tableInfo.setMapperName(String.format(globalConfig.getMapperName(), entityName));
            } else {
                tableInfo.setMapperName(entityName + ConstVal.MAPPER);
            }
            if (StringUtils.isNotEmpty(globalConfig.getServiceName())) {
                tableInfo.setServiceName(String.format(globalConfig.getServiceName(), entityName));
            } else {
                tableInfo.setServiceName(entityName + ConstVal.SERVICE);
            }
            if (StringUtils.isNotEmpty(globalConfig.getServiceImplName())) {
                tableInfo.setServiceImplName(String.format(globalConfig.getServiceImplName(), entityName));
            } else {
                tableInfo.setServiceImplName(entityName + ConstVal.SERVICE_IMPL);
            }
            if (StringUtils.isNotEmpty(globalConfig.getControllerName())) {
                tableInfo.setControllerName(String.format(globalConfig.getControllerName(), entityName));
            } else {
                tableInfo.setControllerName(entityName + ConstVal.CONTROLLER);
            }
            // 检测导入包
            checkImportPackages(tableInfo);
        }
        return tableList;
    }

    /**
     * 检测导入包
     *
     * @param tableInfo ignore
     */
    private void checkImportPackages(TableInfo tableInfo) {
        if (StringUtils.isNotEmpty(strategyConfig.getSuperEntityClass())) {
            // 自定义父类
            tableInfo.getImportPackages().add(strategyConfig.getSuperEntityClass());
        }
    }


    /**
     * 获取所有的数据库表信息
     */
    private List<TableInfo> getTablesInfo(StrategyConfig config) {
        boolean isInclude = (null != config.getInclude() && config.getInclude().length > 0);
        boolean isExclude = (null != config.getExclude() && config.getExclude().length > 0);
        if (isInclude && isExclude) {
            throw new RuntimeException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        }
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();

        //需要反向生成或排除的表信息
        List<TableInfo> includeTableList = new ArrayList<>();
        List<TableInfo> excludeTableList = new ArrayList<>();

        //不存在的表名
        Set<String> notExistTables = new HashSet<>();
        try {
            String tablesSql = dbQuery.tablesSql();
            if (DbType.POSTGRE_SQL == dbQuery.dbType()) {
                String schema = dataSourceConfig.getSchemaName();
                if (schema == null) {
                    //pg默认schema=public
                    schema = "public";
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
            }
            //oracle数据库表太多，出现最大游标错误
            else if (DbType.ORACLE == dbQuery.dbType()) {
                String schema = dataSourceConfig.getSchemaName();
                //oracle默认用户的schema=username
                if (schema == null) {
                    schema = dataSourceConfig.getUsername().toUpperCase();
                    dataSourceConfig.setSchemaName(schema);
                }
                tablesSql = String.format(tablesSql, schema);
                if (isInclude) {
                    StringBuilder sb = new StringBuilder(tablesSql);
                    sb.append(" AND ").append(dbQuery.tableName()).append(" IN (");
                    Arrays.stream(config.getInclude()).forEach(tbname -> sb.append(StringPool.SINGLE_QUOTE).append(tbname.toUpperCase()).append("',"));
                    sb.replace(sb.length() - 1, sb.length(), StringPool.RIGHT_BRACKET);
                    tablesSql = sb.toString();
                } else if (isExclude) {
                    StringBuilder sb = new StringBuilder(tablesSql);
                    sb.append(" AND ").append(dbQuery.tableName()).append(" NOT IN (");
                    Arrays.stream(config.getExclude()).forEach(tbname -> sb.append(StringPool.SINGLE_QUOTE).append(tbname.toUpperCase()).append("',"));
                    sb.replace(sb.length() - 1, sb.length(), StringPool.RIGHT_BRACKET);
                    tablesSql = sb.toString();
                }
            }
            TableInfo tableInfo;
            try (PreparedStatement preparedStatement = connection.prepareStatement(tablesSql);
                 ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    String tableName = results.getString(dbQuery.tableName());
                    if (StringUtils.isNotEmpty(tableName)) {
                        String tableComment = results.getString(dbQuery.tableComment());
                        if (config.isSkipView() && "VIEW".equals(tableComment)) {
                            // 跳过视图
                            continue;
                        }
                        tableInfo = new TableInfo();
                        tableInfo.setName(tableName);
                        tableInfo.setComment(tableComment);
                        if (isInclude) {
                            for (String includeTable : config.getInclude()) {
                                // 忽略大小写等于 或 正则 true
                                if (tableNameMatches(includeTable, tableName)) {
                                    includeTableList.add(tableInfo);
                                } else {
                                    notExistTables.add(includeTable);
                                }
                            }
                        } else if (isExclude) {
                            for (String excludeTable : config.getExclude()) {
                                // 忽略大小写等于 或 正则 true
                                if (tableNameMatches(excludeTable, tableName)) {
                                    excludeTableList.add(tableInfo);
                                } else {
                                    notExistTables.add(excludeTable);
                                }
                            }
                        }
                        tableList.add(tableInfo);
                    } else {
                        log.error("当前数据库为空！！！");
                    }
                }
            }
            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (TableInfo tabInfo : tableList) {
                notExistTables.remove(tabInfo.getName());
            }
            if (notExistTables.size() > 0) {
                log.error("表 " + notExistTables + " 在数据库中不存在！！！");
            }

            // 需要反向生成的表信息
            if (isExclude) {
                tableList.removeAll(excludeTableList);
                includeTableList = tableList;
            }
            if (!isInclude && !isExclude) {
                includeTableList = tableList;
            }
            // 性能优化，只处理需执行表字段 github issues/219
            includeTableList.forEach(ti -> convertTableFields(ti, config.getColumnNaming()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return processTable(includeTableList, config.getNaming(), config);
    }


    /**
     * 表名匹配
     *
     * @param setTableName 设置表名
     * @param dbTableName  数据库表单
     * @return ignore
     */
    private boolean tableNameMatches(String setTableName, String dbTableName) {
        return setTableName.equals(dbTableName)
                || StringUtils.matches(setTableName, dbTableName);
    }

    /**
     * 将字段信息与表信息关联
     *
     * @param tableInfo 表信息
     * @param strategy  命名策略
     * @return ignore
     */
    private TableInfo convertTableFields(TableInfo tableInfo, NamingStrategy strategy) {
        boolean haveId = false;
        List<com.weirdo.tkmapper.generator.config.po.TableField> fieldList = new ArrayList<>();
        List<com.weirdo.tkmapper.generator.config.po.TableField> commonFieldList = new ArrayList<>();
        DbType dbType = dbQuery.dbType();
        String tableName = tableInfo.getName();
        try {
            String tableFieldsSql = dbQuery.tableFieldsSql();
            Set<String> h2PkColumns = new HashSet<>();
            if (DbType.POSTGRE_SQL == dbType) {
                tableFieldsSql = String.format(tableFieldsSql, dataSourceConfig.getSchemaName(), tableName);
            } else if (DbType.ORACLE == dbType) {
                tableName = tableName.toUpperCase();
                tableFieldsSql = String.format(tableFieldsSql.replace("#schema", dataSourceConfig.getSchemaName()), tableName);
            } else if (DbType.H2 == dbType) {
                tableName = tableName.toUpperCase();
                try (PreparedStatement pkQueryStmt = connection.prepareStatement(String.format(H2Query.PK_QUERY_SQL, tableName));
                     ResultSet pkResults = pkQueryStmt.executeQuery()) {
                    while (pkResults.next()) {
                        String primaryKey = pkResults.getString(dbQuery.fieldKey());
                        if (Boolean.valueOf(primaryKey)) {
                            h2PkColumns.add(pkResults.getString(dbQuery.fieldName()));
                        }
                    }
                }
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableName);
            }
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(tableFieldsSql);
                    ResultSet results = preparedStatement.executeQuery()) {
                while (results.next()) {
                    com.weirdo.tkmapper.generator.config.po.TableField field = new TableField();
                    String columnName = results.getString(dbQuery.fieldName());
                    // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                    boolean isId;
                    if (DbType.H2 == dbType) {
                        isId = h2PkColumns.contains(columnName);
                    } else {
                        String key = results.getString(dbQuery.fieldKey());
                        if (DbType.DB2 == dbType) {
                            isId = StringUtils.isNotEmpty(key) && "1".equals(key);
                        } else {
                            isId = StringUtils.isNotEmpty(key) && "PRI".equals(key.toUpperCase());
                        }
                    }
                    // 处理其它信息
                    field.setName(columnName);
                    field.setType(results.getString(dbQuery.fieldType()));
                    field.setPropertyName(processName(field.getName(), strategy));
                    field.setColumnType(dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field.getType()));
                    field.setComment(results.getString(dbQuery.fieldComment()));
                    // 处理ID
                    if (isId && !haveId) {
                        field.setKeyFlag(true);
                        if (DbType.H2 == dbType || dbQuery.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        }
                        haveId = true;
                        tableInfo.setIdType(field.getPropertyType());
                        tableInfo.setIdName(field.getPropertyName());
                    } else {
                        field.setKeyFlag(false);
                    }
                    // 自定义字段查询
                    String[] fcs = dbQuery.fieldCustom();
                    if (null != fcs) {
                        Map<String, Object> customMap = new HashMap<>(10);
                        for (String fc : fcs) {
                            customMap.put(fc, results.getObject(fc));
                        }
                        field.setCustomMap(customMap);
                    }

                    if (strategyConfig.includeSuperEntityColumns(field.getName())) {
                        // 跳过公共字段
                        commonFieldList.add(field);
                        continue;
                    }
                    // 填充逻辑判断
                    List<TableFill> tableFillList = getStrategyConfig().getTableFillList();
                    if (null != tableFillList) {
                        // 忽略大写字段问题
                        tableFillList.stream().filter(tf -> tf.getFieldName().equalsIgnoreCase(field.getName()))
                                .findFirst().ifPresent(tf -> field.setFill(tf.getFieldFill().name()));
                    }
                    fieldList.add(field);
                }
            }
            if (!haveId) {
                throw new RuntimeException("表【" + tableName + "】没有主键");
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL Exception：" + e.getMessage());
        }
        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }


    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (OthersUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }


    /**
     * 连接父子包名
     *
     * @param parent     父包名
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    private String joinPackage(String parent, String subPackage) {
        if (OthersUtils.isEmpty(parent)) {
            return subPackage;
        }
        return parent + StringPool.DOT + subPackage;
    }


    /**
     * 处理字段名称
     *
     * @return 根据策略返回处理后的名称
     */
    private String processName(String name, NamingStrategy strategy) {
        return processName(name, strategy, strategyConfig.getFieldPrefix());
    }


    /**
     * 处理表/字段名称
     *
     * @param name     ignore
     * @param strategy ignore
     * @param prefix   ignore
     * @return 根据策略返回处理后的名称
     */
    private String processName(String name, NamingStrategy strategy, String[] prefix) {
        boolean removePrefix = false;
        if (prefix != null && prefix.length != 0) {
            removePrefix = true;
        }
        String propertyName;
        if (removePrefix) {
            if (strategy == NamingStrategy.underline_to_camel) {
                // 删除前缀、下划线转驼峰
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                // 删除前缀
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            // 下划线转驼峰
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            // 不处理
            propertyName = name;
        }
        return propertyName;
    }
}
