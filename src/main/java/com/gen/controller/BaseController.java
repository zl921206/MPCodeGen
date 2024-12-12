package com.gen.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gen.common.constant.HttpStatus;
import com.gen.domain.AjaxResult;
import com.gen.page.TableDataInfo;
import com.gen.page.TableSupport;
import com.gen.util.DateUtils;
import com.gen.util.PageUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import java.beans.PropertyEditorSupport;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理
 *
 * @author zhanglei_18
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 清理分页的线程变量
     */
    protected void clearPage() {
        PageUtils.clearPage();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        if (CollectionUtils.isNotEmpty(list)) {
            // 因为PageHelper 默认的合理分页设置会将超出范围的页码调整到最后一页。这样会导致出现重复数据，为了规避该问题，所以此处判断页码，重置结果数据
            PageInfo<?> pageInfo = new PageInfo<>(list);
            if (TableSupport.buildPageRequest().getPageNum() > pageInfo.getPages()) {
                rspData.setRows(Collections.emptyList());
            }
            rspData.setTotal((int) pageInfo.getTotal());
            rspData.setPages(pageInfo.getPages());
        } else {
            rspData.setTotal(0);
            rspData.setPages(0);
        }
        return rspData;
    }

    /**
     * 返回成功
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(Object data) {
        return AjaxResult.success(data);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回警告消息
     */
    public AjaxResult warn(String message) {
        return AjaxResult.warn(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? success() : error();
    }
}
