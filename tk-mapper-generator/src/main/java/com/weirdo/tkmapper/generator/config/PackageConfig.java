package com.weirdo.tkmapper.generator.config;


import com.weirdo.tkmapper.generator.common.PathInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *   跟包相关的配置项
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class PackageConfig {

    /**
     * 父包名。如果为空,则生成到项目包 根目录
     */
    private String parent = "";
    /**
     * Entity包名
     */
    private String entity = "entity";
    /**
     * Model包名
     */
    private String model = "model";
    /**
     * Service包名
     */
    private String service = "service";
    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    /**
     * Mapper包名
     */
    private String mapper = "mapper";
    /**
     * Controller包名
     */
    private String controller = "controller";
    /**
     * 核心对象包名
     */
    private String core = "core";
    /**
     * 路径配置信息
     */
    private PathInfo pathInfo;
}
