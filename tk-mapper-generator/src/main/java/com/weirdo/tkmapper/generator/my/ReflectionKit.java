package com.weirdo.tkmapper.generator.my;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
@Slf4j
public class ReflectionKit {

    /**
     * class field cache
     */
    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);
    }

    /**
     * <p>
     * 反射 method 方法名，例如 getId
     * </p>
     *
     * @param field
     * @param str   属性字符串内容
     */
    public static String getMethodCapitalize(Field field, final String str) {
        Class<?> fieldType = field.getType();
        // fix #176
        return StringUtils.concatCapitalize(boolean.class.equals(fieldType) ? "is" : "get", str);
    }

    /**
     * <p>
     * 反射 method 方法名，例如 setVersion
     * </p>
     *
     * @param field Field
     * @param str   String JavaBean类的version属性名
     * @return version属性的setter方法名称，e.g. setVersion
     * @deprecated 3.0.8
     */
    @Deprecated
    public static String setMethodCapitalize(Field field, final String str) {
        return StringUtils.concatCapitalize("set", str);
    }

    /**
     * <p>
     * 获取 public get方法的值
     * </p>
     *
     * @param cls    ignore
     * @param entity 实体
     * @param str    属性字符串内容
     * @return Object
     */
    public static Object getMethodValue(Class<?> cls, Object entity, String str) {
        Map<String, Field> fieldMaps = getFieldMap(cls);
        try {
            Assert.notEmpty(fieldMaps, "Error: NoSuchField in %s for %s.  Cause:", cls.getSimpleName(), str);
            Method method = cls.getMethod(getMethodCapitalize(fieldMaps.get(str), str));
            return method.invoke(entity);
        } catch (NoSuchMethodException e) {
            throw ExceptionUtils.mpe("Error: NoSuchMethod in %s.  Cause:", e, cls.getSimpleName());
        } catch (IllegalAccessException e) {
            throw ExceptionUtils.mpe("Error: Cannot execute a private method. in %s.  Cause:", e, cls.getSimpleName());
        } catch (InvocationTargetException e) {
            throw ExceptionUtils.mpe("Error: InvocationTargetException on getMethodValue.  Cause:" + e);
        }
    }

    /**
     * <p>
     * 获取 public get方法的值
     * </p>
     *
     * @param entity 实体
     * @param str    属性字符串内容
     * @return Object
     */
    public static Object getMethodValue(Object entity, String str) {
        if (null == entity) {
            return null;
        }
        return getMethodValue(entity.getClass(), entity, str);
    }

    /**
     * <p>
     * 反射对象获取泛型
     * </p>
     *
     * @param clazz 对象
     * @param index 泛型所在位置
     * @return Class
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            log.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            log.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                clazz.getSimpleName(), params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            log.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                clazz.getSimpleName()));
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     *
     * @param clazz 反射类
     */
    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        List<Field> fieldList = getFieldList(clazz);
        return !OthersUtils.isEmpty(fieldList) ? fieldList.stream().collect(Collectors.toMap(Field::getName, field -> field)) : Collections.emptyMap();
    }

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     *
     * @param clazz 反射类
     */
    public static List<Field> getFieldList(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return Collections.emptyList();
        }
        List<Field> fields = CLASS_FIELD_CACHE.get(clazz);
        if (OthersUtils.isEmpty(fields)) {
            synchronized (CLASS_FIELD_CACHE) {
                fields = doGetFieldList(clazz);
                CLASS_FIELD_CACHE.put(clazz, fields);
            }
        }
        return fields;
    }

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     *
     * @param clazz 反射类
     */
    public static List<Field> doGetFieldList(Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            List<Field> fieldList = Stream.of(clazz.getDeclaredFields())
                /* 过滤静态属性 */
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                /* 过滤 transient关键字修饰的属性 */
                .filter(field -> !Modifier.isTransient(field.getModifiers()))
                .collect(toCollection(LinkedList::new));
            /* 处理父类字段 */
            Class<?> superClass = clazz.getSuperclass();
            /* 排除重载属性 */
            return excludeOverrideSuperField(fieldList, getFieldList(superClass));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * <p>
     * 排序重置父类属性
     * </p>
     *
     * @param fieldList      子类属性
     * @param superFieldList 父类属性
     */
    public static List<Field> excludeOverrideSuperField(List<Field> fieldList, List<Field> superFieldList) {
        // 子类属性
        Map<String, Field> fieldMap = fieldList.stream().collect(toMap(Field::getName, identity()));
        superFieldList.stream().filter(field -> !fieldMap.containsKey(field.getName())).forEach(fieldList::add);
        return fieldList;
    }

    /**
     * 获取字段get方法
     *
     * @param cls   class
     * @param field 字段
     * @return Get方法
     */
    public static Method getMethod(Class<?> cls, Field field) {
        try {
            return cls.getDeclaredMethod(ReflectionKit.getMethodCapitalize(field, field.getName()));
        } catch (NoSuchMethodException e) {
            throw ExceptionUtils.mpe("Error: NoSuchMethod in %s.  Cause:", e, cls.getName());
        }
    }

    /**
     * 判断是否为基本类型或基本包装类型
     *
     * @param clazz class
     * @return 是否基本类型或基本包装类型
     */
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return (clazz.isPrimitive() || PRIMITIVE_WRAPPER_TYPE_MAP.containsKey(clazz));
    }
}
