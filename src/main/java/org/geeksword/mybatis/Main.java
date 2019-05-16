package org.geeksword.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhoulinshun
 * @Description:
 * @Date: Created in 2019-01-03 14:25
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.generator();
    }

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        // 指定配置文件
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
