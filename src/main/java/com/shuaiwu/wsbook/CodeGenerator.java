package com.shuaiwu.wsbook;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.sql.Types;

public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create(
                "jdbc:mysql://127.0.0.1:3306/ws-book?characterEncoding=utf8&useSSL=false", "root",
                "2wsx#EDC")
            .globalConfig(builder -> {
                builder.author("shuaiwu") // 设置作者
                    .outputDir("E:\\MybatisPlus"); // 指定输出目录
            })
            .dataSourceConfig(
                builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }))
            .packageConfig(builder -> {
                builder.parent("com.shuaiwu") // 设置父包名
                    .moduleName("wsbook") // 设置父包模块名
                ;
            })
            .strategyConfig(builder -> {
                builder.addInclude("book_catalog") // 设置需要生成的表名
                    .entityBuilder().enableLombok().enableTableFieldAnnotation().enableFileOverride()
                    .controllerBuilder().enableRestStyle().enableFileOverride()
                    .serviceBuilder().enableFileOverride()
                    .mapperBuilder().enableFileOverride()
//              .addTablePrefix("t_", "c_")
                ; // 设置过滤表前缀
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }
}
