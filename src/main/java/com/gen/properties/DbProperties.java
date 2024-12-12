package com.gen.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zhanglei_18
 */

@Data
@ToString
@Component
@PropertySource(value = {"file:application.properties"})
public class DbProperties {

    @Value("${mysql.username}")
    private String userName;

    @Value("${mysql.password}")
    private String password;

    @Value("${mysql.connectionUrl}")
    private String connectionUrl;

    @Value("${mysql.driverClass}")
    private String driverClassName;
}
