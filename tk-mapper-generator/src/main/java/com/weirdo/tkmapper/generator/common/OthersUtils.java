package com.weirdo.tkmapper.generator.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author ML.Zhang
 * @since  2019/10/21
 */
public class OthersUtils {
    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return "".equals(object);
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection) object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map) object).size() == 0;
        } else {
            return false;
        }
    }
}
