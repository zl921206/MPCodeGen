package com.gen.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * 
 * @author zhanglei_18
 */
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 删除标志:0-正常；1-删除
     */
    private Integer delFlag = 0;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建用户
     */
    private String createUser;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 更新用户
     */
    private String updateUser;
}
