package com.allong.centerstation;

import com.allong.centerstation.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Copyright (C), 2015-2020, 杭州奥朗信息科技有限公司
 * FileName: TestGenerator
 * Author:   YJ
 * Date:     2020/12/7 16:07
 * Description: 代码生成器
 * History:
 * <author>          <time>          <version>          <desc>
 * YJ       2020/12/7 16:07        1.0              描述
 */
public class TestGenerator {

    /**
     * 一键生成代码
     *
     * @param args
     */
    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();

        //配置全局
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E:\\SourceCode\\CenterStation\\center-station\\app\\src\\main\\java")
                .setAuthor("杨建")
                .setOpen(true)
                .setFileOverride(true)
                .setActiveRecord(true)
                .setDateType(DateType.ONLY_DATE)
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setIdType(IdType.AUTO);
        //配置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql:///center_station?serverTimezone=UTC")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("allong")
                .setDbType(DbType.MYSQL);

        //配置策略
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("sys_")
                .setEntityLombokModel(true)
                .setSuperEntityClass(BaseEntity.class);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.allong.centerstation")
                .setEntity("domain")
                .setMapper("mapper")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setXml("mapper");

        generator.setGlobalConfig(gc)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        generator.execute();
    }
}
