package com.gen.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 * 
 * @author zhanglei_18
 */
@Component
@ToString
@PropertySource(value = {"file:application.properties"})
@Data
public class GenConfig
{
    /** 作者 */
    @Value("${gen.author}")
    public String author;

    /** 生成包路径 */
    @Value("${gen.packageName}")
    public String packageName;

    /** 自动去除表前缀，默认是false */
    public Boolean autoRemovePre;

    /** 表前缀(类名不会包含表前缀) */
    @Value("${gen.tablePrefix}")
    public String tablePrefix;

    /** 表名字*/
    @Value("${gen.tableName}")
    private String tableName;

    public Boolean getAutoRemovePre() {
        return autoRemovePre;
    }

    @Value("${gen.autoRemovePre}")
    public void setAutoRemovePre(String autoRemovePre) {
        this.autoRemovePre = Boolean.parseBoolean(autoRemovePre);
    }
}
