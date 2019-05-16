package org.geeksword.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Author: zhoulinshun
 * @Description: 添加lombok注解
 * @Date: Created in 2019-01-03 14:49
 */
public class LombokAnnotationPlugin extends PluginAdapter {
    private final String data = "@Data";
    private final String builder = "@Builder";
    private final String allArgs = "@AllArgsConstructor";
    private final String noArgs = "@NoArgsConstructor";
    private final FullyQualifiedJavaType allArgsJavaType;
    private final FullyQualifiedJavaType noArgsJavaType;
    private final FullyQualifiedJavaType fullyQualifiedJavaType;
    private final FullyQualifiedJavaType builderJavaType;

    public LombokAnnotationPlugin() {
        fullyQualifiedJavaType = new FullyQualifiedJavaType("lombok.Data");
        builderJavaType = new FullyQualifiedJavaType("lombok.Builder");
        allArgsJavaType = new FullyQualifiedJavaType("lombok.AllArgsConstructor");
        noArgsJavaType = new FullyQualifiedJavaType("lombok.NoArgsConstructor");
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {

        addLombokAnnotation(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addLombokAnnotation(topLevelClass, introspectedTable);
        return true;
    }

    private void addLombokAnnotation(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(fullyQualifiedJavaType);
        topLevelClass.addImportedType(builderJavaType);
        topLevelClass.addImportedType(allArgsJavaType);
        topLevelClass.addImportedType(noArgsJavaType);
        topLevelClass.addAnnotation(data);
        topLevelClass.addAnnotation(builder);
        topLevelClass.addAnnotation(allArgs);
        topLevelClass.addAnnotation(noArgs);
    }

    /**
     * 该方法在生成每一个属性的getter方法时候调用，如果我们不想生成getter，直接返回false即可；
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }


    /**
     *     该方法在生成每一个属性的setter方法时候调用，如果我们不想生成setter，直接返回false即可；
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

}
