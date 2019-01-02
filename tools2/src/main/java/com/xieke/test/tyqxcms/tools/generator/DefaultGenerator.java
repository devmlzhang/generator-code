package com.xieke.test.tyqxcms.tools.generator;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class DefaultGenerator {

    private AutoGenerator autoGenerator = new AutoGenerator();

    private void execute() {
        Assert.notNull(this.autoGenerator.getGlobalConfig());
        Assert.notNull(this.autoGenerator.getDataSource());
        Assert.notNull(this.autoGenerator.getPackageInfo());
        if (this.autoGenerator.getStrategy() == null) {
            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            this.autoGenerator.setStrategy(strategy);
        }
        this.autoGenerator.getStrategy().setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("update_user", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("create_user", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        this.autoGenerator.getStrategy().setTableFillList(tableFillList);
        this.autoGenerator.getStrategy().setRestControllerStyle(false);
        this.autoGenerator.setConfig(null);
        this.autoGenerator.execute();
    }

    public void createClass(String... args) {
        if (args == null) {
            return;
        }
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity(null);
        tc.setMapper(null);
        tc.setController(null);
        tc.setService(null);
        tc.setServiceImpl(null);
        tc.setXml(null);
        List<String> list = Arrays.asList(args);
        if (list.contains("controller")) {
            tc.setController("/templates/controller.java.vm");
        }
        if (list.contains("mapper")) {
            tc.setMapper("/templates/mapper.java.vm");
        }
        if (list.contains("entity")) {
            tc.setEntity("/templates/entity.java.vm");
        }
        if (list.contains("service")) {
            tc.setService("/templates/service.java.vm");
        }
        if (list.contains("service.impl")) {
            tc.setServiceImpl("/templates/serviceImpl.java.vm");
        }
        if (list.contains("Mapper")) {
            tc.setXml("/templates/mapper.xml.vm");
        }
        this.autoGenerator.setTemplate(tc);
        this.execute();
    }

    public void setDataSource(DataSourceConfig dataSource) {
        this.autoGenerator.setDataSource(dataSource);
    }

    public void setOutputDir(String outputDir) {
        if (this.autoGenerator.getGlobalConfig() == null) {
            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            gc.setFileOverride(true);
            gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
            gc.setEnableCache(false);// XML 二级缓存
            gc.setBaseResultMap(true);// XML ResultMap
            gc.setBaseColumnList(false);// XML columList
            gc.setAuthor("Auto Generator");
            gc.setOpen(false);
            this.autoGenerator.setGlobalConfig(gc);
        }
        this.autoGenerator.getGlobalConfig().setOutputDir(outputDir);
    }

    public void setPackageInfo(PackageConfig pc) {
        this.autoGenerator.setPackageInfo(pc);
    }

    public void setIncludeTables(String tablePrefix, String... tables) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(tablePrefix);
        strategy.setInclude(tables);
        this.autoGenerator.setStrategy(strategy);
    }
}