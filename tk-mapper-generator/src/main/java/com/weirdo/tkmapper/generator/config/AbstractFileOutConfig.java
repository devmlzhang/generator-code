package com.weirdo.tkmapper.generator.config;

import com.weirdo.tkmapper.generator.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *   输出文件配置
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public abstract class AbstractFileOutConfig {

    /**
     * 模板
     */
    private String templatePath;

    public AbstractFileOutConfig() {
        // to do nothing
    }

    public AbstractFileOutConfig(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 输出文件
     * @param tableInfo
     * @return
     */
    public abstract String outputFile(TableInfo tableInfo);
}
