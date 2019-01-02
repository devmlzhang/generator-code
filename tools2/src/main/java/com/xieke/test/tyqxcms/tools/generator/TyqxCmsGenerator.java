package com.xieke.test.tyqxcms.tools.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;

/**
 * 代码生成器
 */
public class TyqxCmsGenerator {

    // 根据命名规范，只修改此常量值即可
    private static String MODULE = "weirdo";
    private static String TABLE_PREFIX = "sys_";
    private static String PARENT_PACKAGE_NAME = "com.weirdo.test";

    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/sys-blog?useUnicode=true&amp;characterEncoding=UTF-8&amp;tinyInt1isBit=false";
    private static String JDBC_USERNAME = "root";
    private static String JDBC_PASSWORD = "root";

    private static String OUTPUT_API_DIR = "/Users/weirdo/tyqx-cms/api/src/main/java";
    private static String OUTPUT_SERVICE_DIR = "/Users/weirdo/tyqx-cms/provider/src/main/java";
    private static String OUTPUT_CONTROLLER_DIR = "/Users/weirdo/tyqx-cms/consumer/src/main/java";

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) {

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(JDBC_DRIVER);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        dsc.setUrl(JDBC_URL);

        PackageConfig pc = new PackageConfig();
        pc.setParent(PARENT_PACKAGE_NAME);
        pc.setModuleName(MODULE);

        DefaultGenerator defaultGenerator = new DefaultGenerator();
        defaultGenerator.setDataSource(dsc);
        defaultGenerator.setPackageInfo(pc);
        defaultGenerator.setIncludeTables(TABLE_PREFIX,"sys_log","blog");

        // 生成API
        defaultGenerator.setOutputDir(OUTPUT_API_DIR);
        defaultGenerator.createClass(
                "entity"
                ,"service"
        );
        // 生成服务
        defaultGenerator.setOutputDir(OUTPUT_SERVICE_DIR);
        defaultGenerator.createClass(
                "Mapper"
                , "mapper"
                , "service.impl"
        );
        // 生成Controller
        defaultGenerator.setOutputDir(OUTPUT_CONTROLLER_DIR);
        defaultGenerator.createClass("controller");
    }

}
