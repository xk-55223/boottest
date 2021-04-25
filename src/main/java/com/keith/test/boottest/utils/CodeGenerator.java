package com.keith.test.boottest.utils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author keith
 * @since 2021-03-03
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("keith");
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setFileOverride(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/dotdotbuy?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.keith.test.boottest");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                //map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("abc", "abcTestValue");
                this.setMap(map);
            }
        };

        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(filePath);
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });

        List<FileOutConfig> focList = getFileOutConfigs(projectPath, pc);
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
         templateConfig.setXml("mybatistemplate/mapper.xml");
         templateConfig.setMapper("mybatistemplate/mapper.java");
         templateConfig.setService("mybatistemplate/service.java");
         templateConfig.setServiceImpl("mybatistemplate/serviceImpl.java");
         templateConfig.setController("mybatistemplate/controller.java");
         templateConfig.setEntity("mybatistemplate/entity.java");

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.keith.test.boottest.entity.BaseEntity");
        // 使用lombok模板
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("create_time", "update_time", "del_flag", "data_code", "create_by", "update_by");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        //strategy.setLogicDeleteFieldName("del_flag");
        strategy.setEntitySerialVersionUID(false);
        mpg.setStrategy(strategy);
        // 如果模板引擎是 freemarker
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static List<FileOutConfig> getFileOutConfigs(String projectPath, PackageConfig pc) {
        String mapperTemplatePath = "mybatistemplate/mapper.java.ftl";
        String entityTemplatePath = "mybatistemplate/entity.java.ftl";
        String controllerTemplatePath = "mybatistemplate/controller.java.ftl";
        String serviceTemplatePath = "mybatistemplate/service.java.ftl";
        String serviceImplTemplatePath = "mybatistemplate/serviceImpl.java.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        String xmlTemplatePath = "mybatistemplate/mapper.xml.ftl";
        focList.add(new FileOutConfig(xmlTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java" + pc.getModuleName()
                        + "mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(entityTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java" + pc.getModuleName()
                        + "entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(serviceTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java" + pc.getModuleName()
                        + "service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig(serviceImplTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java" + pc.getModuleName()
                        + "service/impl/" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig(controllerTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java" + pc.getModuleName()
                        + "controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        return focList;
    }

}
