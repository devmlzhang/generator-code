package com.weirdo.generator;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by cai_tb on 2018/6/25.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
