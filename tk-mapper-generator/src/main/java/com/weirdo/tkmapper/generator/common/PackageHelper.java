package com.weirdo.tkmapper.generator.common;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
/**
 * <p>
 *    包扫描辅助类
 * </p>
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public class PackageHelper {

    /**
     * 别名通配符设置
     *
     * @param typeAliasesPackage 类别名包路径
     * @return
     */
    public static String[] convertTypeAliasesPackage(String typeAliasesPackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String pkg = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/*.class";
        /*
         * 将加载多个绝对匹配的所有Resource
         * 将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分，然后进行遍历模式匹配，排除重复包路径
         */
        try {
            Set<String> set = new HashSet<>();
            Resource[] resources = resolver.getResources(pkg);
            if (resources != null && resources.length > 0) {
                MetadataReader metadataReader;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        set.add(ClassUtils.getPackageName(metadataReader.getClassMetadata().getClassName()));
                    }
                }
            }
            if (!set.isEmpty()) {
                return set.toArray(new String[]{});
            }
            return new String[0];
        } catch (Exception e) {
            throw ExceptionUtils.mpe("not find typeAliasesPackage: %s", e, pkg);
        }
    }


    /**
     * 扫描获取指定包路径所有类
     *
     * @param typePackage 扫描类包路径
     * @return ignore
     */
    public static Set<Class<?>> scanTypePackage(String typePackage) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String pkg = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(typePackage) + "/*.class";
        /*
         * 将加载多个绝对匹配的所有Resource
         * 将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分，然后进行遍历模式匹配，排除重复包路径
         */
        try {
            Set<Class<?>> set = new HashSet<>();
            Resource[] resources = resolver.getResources(pkg);
            if (resources != null && resources.length > 0) {
                MetadataReader metadataReader;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        set.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                    }
                }
            }
            return set;
        } catch (Exception e) {
            throw ExceptionUtils.mpe("not find scanTypePackage: %s", e, pkg);
        }
    }

    /**
     * 新建文件目录
     *
     * @param file 文件
     */
    public static void mkDir(File file) {
        file.mkdirs();
    }
}

