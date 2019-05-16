package org.geeksword.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Author: zhoulinshun
 * @Description: 添加javax注解
 * @Date: Created in 2019-05-15 12:56
 */
public class JavaxAnnotationPlugin extends PluginAdapter {


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns.stream().anyMatch(c -> c.equals(introspectedColumn))) {
//                topLevelClass.addImportedType("javax.persistence.Id");
//                field.addAnnotation("@Id");
        }
        if (!introspectedColumn.isNullable()) {
            topLevelClass.addImportedType("javax.validation.constraints.NotNull");
            field.addAnnotation("@NotNull");
        }

        return true;
    }

}
