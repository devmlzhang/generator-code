import com.weirdo.tkmapper.generator.AutoGenerator;
import com.weirdo.tkmapper.generator.config.DataSourceConfig;
import com.weirdo.tkmapper.generator.config.GlobalConfig;
import com.weirdo.tkmapper.generator.config.PackageConfig;
import com.weirdo.tkmapper.generator.config.StrategyConfig;
import com.weirdo.tkmapper.generator.config.rules.DateType;
import com.weirdo.tkmapper.generator.engine.AbstractTemplateEngine;

import java.util.Map;

/**
 * <p>
 *  主函数
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public class MybatisCodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 策略配置
        StrategyConfig strategy = new StrategyConfig()
            .setInclude("tb_category");
        generator.setStrategy(strategy);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig()
            .setFileOverride(true)
            .setAuthor("weirdo")
            .setSwagger2(true)
            .setDateType(DateType.ONLY_DATE);
        generator.setGlobalConfig(globalConfig);
        PackageConfig packageConfig=new PackageConfig();
        packageConfig.setParent("com.weirdo.finacel");
        generator.setPackageConfig(packageConfig);
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
            .setUrl("jdbc:mysql://127.0.0.1:3306/test")
            .setDriverName("com.mysql.cj.jdbc.Driver")
            .setUsername("root")
            .setPassword("root");
        generator.setDataSource(dataSourceConfig);
        generator.execute();
    }
}
