package org.geeksword.mybatis;


import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Properties;

/**
 * @Author: zhoulinshun
 * @Description: 对于mybatis生成的所有实体类添加序列化接口
 * @Date: Created in 2019-03-27 14:38
 */
public class SerializablePlugin extends org.mybatis.generator.plugins.SerializablePlugin {


    private final FullyQualifiedJavaType serializable;
    private final FullyQualifiedJavaType gwtSerializable;
    private Boolean addGWTInterface;
    private Boolean suppressJavaInterface;
    private final FullyQualifiedJavaType javaType;

    public SerializablePlugin() {
        super();
        serializable = new FullyQualifiedJavaType("java.io.Serializable"); //$NON-NLS-1$
        gwtSerializable = new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable"); //$NON-NLS-1$
        javaType = new FullyQualifiedJavaType("long");
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface")); //$NON-NLS-1$
        suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface")); //$NON-NLS-1$
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        super.makeSerializable(topLevelClass, introspectedTable);
        /**
         * 针对内部类 添加序列化接口
         */
        List<InnerClass> innerClasses = topLevelClass.getInnerClasses();
        for (InnerClass innerClass : innerClasses) {
            if (addGWTInterface) {
                innerClass.addSuperInterface(gwtSerializable);
            }

            if (!suppressJavaInterface) {
                innerClass.addSuperInterface(serializable);

                Field field = new Field();
                field.setFinal(true);
                field.setInitializationString("1L"); //$NON-NLS-1$
                field.setName("serialVersionUID"); //$NON-NLS-1$
                field.setStatic(true);
                field.setType(javaType); //$NON-NLS-1$
                field.setVisibility(JavaVisibility.PRIVATE);

                if (introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3_DSQL) {
                    context.getCommentGenerator().addFieldAnnotation(field, introspectedTable,
                            topLevelClass.getImportedTypes());
                } else {
                    context.getCommentGenerator().addFieldComment(field, introspectedTable);
                }

                innerClass.addField(field);
            }

        }

    }

}
