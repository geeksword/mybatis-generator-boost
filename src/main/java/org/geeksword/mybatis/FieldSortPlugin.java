package org.geeksword.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @Author: zhoulinshun
 * @Description: 字段排序，静态字段放前方
 * @Date: Created in 2019-01-03 15:54
 */
public class FieldSortPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        fieldSort(topLevelClass.getFields());
        topLevelClass.getInnerClasses().parallelStream().map(InnerClass::getFields).forEach(this::fieldSort);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        fieldSort(topLevelClass.getFields());
        topLevelClass.getInnerClasses().parallelStream().map(InnerClass::getFields).forEach(this::fieldSort);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        fieldSort(topLevelClass.getFields());
        topLevelClass.getInnerClasses().parallelStream().map(InnerClass::getFields).forEach(this::fieldSort);
        return true;
    }

    private void fieldSort(List<Field> fields) {
        fields.sort((o2, o1) -> {
            if (o1.isStatic()) {
                if (o2.isStatic()) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (o2.isStatic()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }
}
