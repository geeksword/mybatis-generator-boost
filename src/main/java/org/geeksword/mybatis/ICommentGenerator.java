package org.geeksword.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhoulinshun
 * @Description: 添加字段和表注释
 * @Date: Created in 2019-01-03 13:55
 */
public class ICommentGenerator extends DefaultCommentGenerator {

    private boolean suppressAllComments;
    private Pattern pattern = Pattern.compile("Enum[a-zA-Z0-9_]+");


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        if (field.isStatic()) {
            return;
        }
        String remarks = introspectedColumn.getRemarks();
        if (remarks == null || remarks.isEmpty()) {
            return;
        }
        Matcher matcher = pattern.matcher(remarks);
        if (matcher.find()) {
            remarks = matcher.replaceAll("{@link $0}");
        }

        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(remarks);
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addClassComment(topLevelClass, introspectedTable, false);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        if (!(innerClass instanceof TopLevelClass)) {
            return;
        }
        if (innerClass.getType().getShortName().endsWith("Example")) {
            return;
        }

        String remarks = introspectedTable.getRemarks();
        StringBuilder comment = new StringBuilder();
        comment.append("/** \n");
        comment.append(" * @Author: mybatis generator \n");
        comment.append(" * @Description: ");

        if (StringUtility.stringHasValue(remarks)) {
            comment.append(remarks);
        }
        comment.append("\n");
        comment.append(" * @Date: ").
                append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).
                append("\n").
                append(" */\n");
        innerClass.addJavaDocLine(comment.toString());
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        addClassComment(innerClass, introspectedTable, false);
    }

}
