package com.keith.test.boottest.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis反向生成工具类
 *
 * @author keith
 * @since 2021-03-03
 */
public class MybatisGeneratorUtil {
    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        //如果这里出现空指针，直接写绝对路径即可。
        String genCfg = "/generator/mybatis_generator_config.xml";
        File configFile = new File(MybatisGeneratorUtil.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException | XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            myBatisGenerator.generate(null);
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
