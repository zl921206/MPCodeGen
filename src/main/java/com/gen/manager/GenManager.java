package com.gen.manager;

import com.gen.core.text.Convert;
import com.gen.domain.GenTable;
import com.gen.mapper.GenTableMapper;
import com.gen.properties.GenConfig;
import com.gen.service.IGenTableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanglei_18
 */
@Component
@Slf4j
public class GenManager {

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private GenConfig genConfig;

    @Autowired
    private GenTableMapper genTableMapper;

    public void generate() {
        //第一步：检查是否存在历史数据并删除
        String tableName = genConfig.getTableName();
        GenTable req=new GenTable();
        req.setTableName(tableName);
        List<GenTable> genTables = genTableMapper.selectGenTableList(req);
        if (!CollectionUtils.isEmpty(genTables)) {
            log.warn("表{}生成数据已存在，即将删除历史数据", tableName);
            Long[] array = new Long[genTables.size()];
            genTables.stream().map(GenTable::getTableId).collect(Collectors.toList()).toArray(array);
            genTableService.deleteGenTableByIds(array);
        }
        //第二步：导入表结构
        String[] tableNames = Convert.toStrArray(tableName);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);

        //第三步：生成压缩文件
        byte[] data = genTableService.downloadCode(tableNames);
        genCode("genCode.zip", data);
    }

    /**
     * 生成zip文件
     *
     * @param fileName 目标压缩文件名
     * @param data     字节数组
     */
    public void genCode(String fileName, byte[] data) {
        try {
            IOUtils.write(data, new FileOutputStream(new File(fileName)));
        } catch (IOException e) {
            log.error("", e);
        }
    }

}
