package com.weirdo.tkmapper.generator.my;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *  包信息
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Data
@Accessors(chain = true)
public class PackageInfo {
    private String entity;
    private String model;
    private String service;
    private String serviceImpl;
    private String mapper;
    private String controller;
    private String core;
}
