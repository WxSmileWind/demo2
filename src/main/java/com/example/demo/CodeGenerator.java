package com.example.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //设置导出路径
        gc.setOutputDir(projectPath + "/src/main/java");
        //设置作者
        gc.setAuthor("吴啸");
        gc.setOpen(false);//生成后是否打开资源管理器
        gc.setFileOverride(true);//重新生成时文件是否覆盖
        gc.setServiceName("%sService");//去掉Service接口的首字母I

        /*AUTO(0, “数据库ID自增”),
        INPUT :INPUT(1, “用户输入ID”),
        ID_WORKER :ID_WORKER(2, “全局唯一ID”),
        UUID :UUID(3, “全局唯一ID”),
        NONE :NONE(4, “该类型为未设置主键类型”),
        ID_WORKER_STR :ID_WORKER_STR(5, “字符串全局唯一ID”);*/
        gc.setIdType(IdType.ID_WORKER_STR);//主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型

        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc=new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://10.68.128.150:3306/nbgrid?characterEncoding=UTF-8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("yyzx_view");
        dsc.setPassword("yyzx456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //4、包配置
        PackageConfig pc=new PackageConfig();
        pc.setModuleName("");     //模块名
        pc.setParent("com.example.demo");
        pc.setController("controller");
        pc.setEntity("pojo");
        pc.setService("service");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);


        //5、策略设置
        StrategyConfig strategy=new StrategyConfig();
        strategy.setInclude("grid_org_role");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix("_");

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setEntityLombokModel(true);//lombok 模型

        strategy.setRestControllerStyle(true);// restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true);//url中驼峰转连字符

        mpg.setStrategy(strategy);

        //6、执行
        mpg.execute();

    }
}
