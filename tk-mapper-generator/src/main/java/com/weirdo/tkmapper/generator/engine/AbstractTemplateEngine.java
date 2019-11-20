package com.weirdo.tkmapper.generator.engine;

import com.weirdo.tkmapper.generator.config.ConstVal;
import com.weirdo.tkmapper.generator.config.GlobalConfig;
import com.weirdo.tkmapper.generator.config.TemplateConfig;
import com.weirdo.tkmapper.generator.config.builder.ConfigBuilder;
import com.weirdo.tkmapper.generator.config.po.TableInfo;
import com.weirdo.tkmapper.generator.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 *    模板引擎抽象类
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public abstract class AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;


    /**
     * 模板引擎初始化
     */
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 输出 java文件
     */
    public AbstractTemplateEngine batchOutput() {
        boolean createAjax = false;
        boolean createPage = false;
        List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
        PathInfo pathInfo = getConfigBuilder().getPathInfo();
        TemplateConfig template = getConfigBuilder().getTemplate();
        Map<String, Object> params = buildParams();
        params.put("tables", tableInfoList);
        for (TableInfo tableInfo : tableInfoList) {
            params.put("table", tableInfo);
            // Entry.java
            String entityName = tableInfo.getEntityName();
            if (entityName != null) {
                String entityFile = String.format((pathInfo.getEntity() + File.separator + "%s" + suffixJava()), entityName);
                if (isCreate(entityFile)) {
                    writer(params, templateFilePath(template.getEntity()), entityFile);
                }
            }
            // Model.java
            String modelName = tableInfo.getModelName();
            if (modelName != null) {
                String modelFile = String.format((pathInfo.getModel() + File.separator + tableInfo.getModelName() + suffixJava()), entityName);
                if (isCreate(modelFile)) {
                    writer(params, templateFilePath(template.getModel()), modelFile);
                }
            }
            // tKMapper.java
            if (tableInfo.getMapperName() != null) {
                String mapperFile = String.format((pathInfo.getMapper() + File.separator + tableInfo.getMapperName() + suffixJava()), entityName);
                if (isCreate(mapperFile)) {
                    writer(params, templateFilePath(template.getMapper()), mapperFile);
                }
            }

            // MapperXML.java
            if (tableInfo.getMapperName() != null) {
                String mapperXmlFile = String.format((pathInfo.getMapper() + File.separator + tableInfo.getMapperName() + suffixXml()),entityName);
                if (isCreate(mapperXmlFile)) {
                    // params.put(params, ConstVal.MAPPER_XML_NAME);
                    writer(params, templateFilePath(template.getMapperXml()), mapperXmlFile);
                }
            }
            // Service.java
            if (tableInfo.getServiceName() != null) {
                String serviceFile = String.format((pathInfo.getService() + File.separator + tableInfo.getServiceName() + suffixJava()), entityName);
                if (isCreate(serviceFile)) {
                    writer(params, templateFilePath(template.getService()), serviceFile);
                    createPage = true;
                }
            }
            // ServiceImpl.java
            if (tableInfo.getServiceImplName() != null) {
                String implFile = String.format((pathInfo.getServiceImpl() + File.separator + tableInfo.getServiceImplName() + suffixJava()), entityName);
                if (isCreate(implFile)) {
                    writer(params, templateFilePath(template.getServiceImpl()), implFile);
                    createPage = true;
                }
            }

            // Controller.java
            if (tableInfo.getControllerName() != null) {
                String controllerFile = String.format((pathInfo.getController() + File.separator + tableInfo.getControllerName() + suffixJava()), entityName);
                if (isCreate(controllerFile)) {
                    writer(params, templateFilePath(template.getController()), controllerFile);
                    createAjax = true;
                }
            }
        }

        return this;
    }

    /**
     * 将模板内容写入到文件中
     *
     * @param params       渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @throws Exception
     */
    public abstract void writer(Map<String, Object> params, String templatePath, String outputFile);

    /**
     * 创建输出目录
     */
    public AbstractTemplateEngine mkDirs() {
        PathInfo pathInfo = getConfigBuilder().getPathInfo();
        for (Field field : pathInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String value;
            try {
                value = field.get(pathInfo).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("读取文件保存路径异常");
            }
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + value + "]");
                } else {
                    logger.debug("创建目录： [" + value + "]失败");
                }
            }
        }
        return this;
    }


    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        if (getConfigBuilder().getGlobalConfig().isAutoOpenDirectory() && StringUtils.isNotEmpty(outDir)) {
            try {
                String osName = System.getProperty("os.name");
                if (osName != null) {
                    if (osName.contains("Mac")) {
                        Runtime.getRuntime().exec("open " + outDir);
                    } else if (osName.contains("Windows")) {
                        Runtime.getRuntime().exec("cmd /c start " + outDir);
                    } else {
                        logger.debug("文件输出目录:" + outDir);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 渲染对象参数 信息
     *
     * @return ignore
     */
    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        params.put("config", config);
        params.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        params.put("author", globalConfig.getAuthor());
        params.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        params.put("swagger2", globalConfig.isSwagger2());
        params.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        params.put("baseResultMap", "true");
        params.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
        params.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
        params.put("superMapperClassPackage", config.getSuperMapperClass());
        params.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
        params.put("superControllerClassPackage", config.getSuperControllerClass());
        params.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
        return params;
    }


    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    private String getSuperClassName(String classPath) {
        if (OthersUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }


    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String templateFilePath(String filePath);


    /**
     * 检测文件是否存在
     *
     * @return 文件是否存在
     */
    protected boolean isCreate(String filePath) {
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            PackageHelper.mkDir(file.getParentFile());
        }
        return !exist || getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    /**
     * 文件后缀
     */
    protected String suffixJava() {
        return ConstVal.JAVA_SUFFIX;
    }

    /**
     * 文件后缀
     */
    protected String suffixXml() {
        return ConstVal.XML_SUFFIX;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}
