package com.weirdo.generator;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <p>
 *   Mapper
 * </p>
 *
 * @author ML.Zhang
 * @since  2019-08-16
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
