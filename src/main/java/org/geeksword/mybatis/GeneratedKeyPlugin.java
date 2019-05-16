package org.geeksword.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @Author: zhoulinshun
 * @Description: 对于自增主键，insert方法添加主键的返回值放到实体类中
 * @Date: Created in 2019-05-14 15:19
 */
public class GeneratedKeyPlugin extends PluginAdapter {

    private final Attribute useGeneratedKeys;

    public GeneratedKeyPlugin() {
        useGeneratedKeys = new Attribute("useGeneratedKeys", "true");
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        addGeneratedKeys(element, introspectedTable);
        return super.sqlMapInsertElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        addGeneratedKeys(element, introspectedTable);
        return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
    }

    private void addGeneratedKeys(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns != null && primaryKeyColumns.size() > 0) {
            if (primaryKeyColumns.size() > 1) {
                return;
            }
            IntrospectedColumn introspectedColumn = primaryKeyColumns.get(0);
            if (introspectedColumn.isAutoIncrement()) {
                element.addAttribute(useGeneratedKeys);
                element.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                element.addAttribute(new Attribute("keyColumn", introspectedColumn.getActualColumnName()));
            }
        }
    }
}
