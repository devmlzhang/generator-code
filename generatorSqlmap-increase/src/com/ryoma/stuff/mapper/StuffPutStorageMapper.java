package com.ryoma.stuff.mapper;

import com.ryoma.stuff.entity.StuffPutStorage;

public interface StuffPutStorageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StuffPutStorage record);

    int insertSelective(StuffPutStorage record);

    StuffPutStorage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StuffPutStorage record);

    int updateByPrimaryKey(StuffPutStorage record);
}