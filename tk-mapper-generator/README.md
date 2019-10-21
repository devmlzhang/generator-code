#通用Mapper逆向工程代码生成器

**一、使用方式**  
    1.创建代码生成器  
        AutoGenerator generator = new AutoGenerator();   
    2.策略配置    
        StrategyConfig strategy = new StrategyConfig()  
            //指定逆向的表名  
            .setInclude("member","goods");  
        generator.setStrategy(strategy);  
    3.全局配置  
        GlobalConfig globalConfig = new GlobalConfig()  
            //覆盖已有文件  
            .setFileOverride(true)  
            //类和接口的创建人信息  
            .setAuthor("开发人员")  
            //开启Swagger2接口文档  
            .setSwagger2(true)  
            //指定数据库时间字段对应的java类型  
            .setDateType(DateType.ONLY_DATE);  
        generator.setGlobalConfig(globalConfig);  
    3.包配置  
        PackageConfig packageConfig=new PackageConfig();  
        //父级包，可以指定各层包的包名  
        packageConfig.setParent("com.test");  
        generator.setPackageConfig(packageConfig);  
    4.数据源配置（支持多种数据库，需要本地项目添加数据库驱动依赖）  
        DataSourceConfig dataSourceConfig = new DataSourceConfig()  
            .setUrl("标准url链接")  
            .setDriverName("驱动")  
            .setUsername("账户")  
            .setPassword("密码");  
        generator.setDataSource(dataSourceConfig);  
    5.开始逆向  
        generator.execute();  
二、参数配置  
    1.策略配置参数  
        isCapitalMode: 是否大写命名 默认false  
        skipView :跳过视图 默认false  
        naming: 数据库表映射到实体的命名策略，默认下划线转驼峰 
        columnNaming：数据库表字段映射到实体的命名策略，默认下划线转驼峰  
        superMapperClass：Mapper父类，默认通用Mapper的Mapper接口  
        superControllerClass：Controller父类，默认无  
        include:逆向的表名（与exclude二选一）  
        exclude:排除的表名（与include二选一）  
    2.全局配置参数  
        outputDir：文件输出目录，默认项目根目录  
        fileOverride：是否覆盖文件，默认覆盖  
        autoOpenDirectory：文件生成后自动打开目录  
        author：开发人员信息  
        swagger2：开启swagger2  
        dateType：指定数据库时间字段对应的java类型  
        /**  
         * 各层文件名称方式，例如： %sController 生成 UserController  
         * %s 为占位符  
         */  
        entityName : "%sEntity";  
        mapperName : "%sMapper";  
        serviceName : "%sService";  
        serviceImplName : "%sServiceImpl";  
        controllerName : "%sController";  
    3.包名配置  
        parent : 父包名。如果为空,则生成到项目包 根目录  
        entity : "entity" 实体类包名  
        service : "service" 服务层包名  
        serviceImpl : "service.impl" 服务实现层包名  
        mapper : "mapper" mapper包名  
        controller : "controller" 控制层包名  
        core : "core" 自动生成的核心对象包名  
    4.数据源配置  
        url:链接路径  
        username:用户名  
        password:密码  
        driverClassName:数据库驱动  
