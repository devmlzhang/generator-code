package com.weirdo.tkmapper.generator.config;

import com.weirdo.tkmapper.generator.config.rules.DateType;
import com.weirdo.tkmapper.generator.my.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *  全局配置
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class GlobalConfig {

    /**
     * 生成文件的输出目录【默认 java根目录】
     */
    private String outputDir = System.getProperty("user.dir") + "/src/main/java";

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;

    /**
     * 是否打开输出目录
     */
    private boolean autoOpenDirectory = false;

    /**
     * 开发人员
     */
    private String author;

    /**
     * 开启 swagger2 模式
     */
    private boolean swagger2 = false;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 各层文件名称方式，例如： %sController 生成 UserController
     * %s 为占位符
     */
    private String entityName = "%sEntity";
    private String modelName = "%sModel";
    private String mapperName = "%sMapper";
    private String serviceName = "%sService";
    private String serviceImplName = "%sServiceImpl";
    private String controllerName = "%sController";
    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType;
}
