package com.ryoma.stuff.mapper;

import com.ryoma.stuff.entity.StuffPutStorageRel;

public interface StuffPutStorageRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StuffPutStorageRel record);

    int insertSelective(StuffPutStorageRel record);

    StuffPutStorageRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StuffPutStorageRel record);

    int updateByPrimaryKey(StuffPutStorageRel record);
}