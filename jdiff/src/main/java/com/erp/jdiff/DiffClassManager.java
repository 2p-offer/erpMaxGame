package com.erp.jdiff;

import com.alibaba.fastjson.annotation.JSONField;
import com.erp.jdiff.obj.IDiffObject;
import com.erp.jdiff.path.DiffPath;
import com.erp.jdiff.util.DiffPathUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>Diff class 管理类</p>
 * <p></p>
 * <p>1. 记录Diff类中, 生成 diff 操作需要忽略的属性</p>
 * <p>2. 记录Diff类中, 生成 update query 操作需要忽略的属性</p>
 * <p></p>
 *
 * @author wanggang
 * @date 2021/11/8 4:03 下午
 **/
public class DiffClassManager {
    private static final Logger logger = LoggerFactory.getLogger(DiffClassManager.class);
    /**
     * <p>JOMT的数据类Field缓存</p>
     * <p>Map<类名, 属性map<属性名, 属性对象{@link Field}>></p>
     */
    private static final Map<Class<? extends IDiffObject>, Map<String, Field>> CLASS_FIELD_MAP = new HashMap<>();
    /**
     * <p>客户端忽略的属性</p>
     * <p></p>
     * <p>标记{@link com.alibaba.fastjson.annotation.JSONField}的属性, 并且serialize==false, 对应的path路径</p>
     * <p>例: "AClassName|fieldName"</p>
     */
    private static final Set<String> DIFF_IGNORE_SET = new HashSet<>();
    /**
     * <p>数据入库忽略的属性</p>
     * <p></p>
     * <p>标记{@link org.springframework.data.annotation.Transient}的属性对应的path路径</p>
     * <p>例: "AClassName|fieldName"</p>
     */
    private static final Set<String> DB_IGNORE_SET = new HashSet<>();

    /**
     * 初始化Diff Class缓存数据
     *
     * @param packageName  包路径
     * @param packageNames 包路径数组
     */
    public static void init(String packageName, String... packageNames) throws UnsupportedOperationException {
        var configuration = new ConfigurationBuilder()
                .forPackages(packageName)
                .forPackages(packageNames)
                .addScanners(new SubTypesScanner());
        cacheDiffClasses(new Reflections(configuration).getSubTypesOf(IDiffObject.class));
    }

    public static boolean isDiffIgnore(DiffPath diffPath) {
        return diffPath.getNodeList().stream().anyMatch(diffNode -> DIFF_IGNORE_SET.contains(diffNode.toString()));
    }

    public static boolean isDbIgnore(DiffPath diffPath) {
        return diffPath.getNodeList().stream().anyMatch(diffNode -> DB_IGNORE_SET.contains(diffNode.toString()));
    }

    public static Field getField(Class<?> clz, String fieldName) {
        if (!CLASS_FIELD_MAP.containsKey(clz)) {
            throw new IllegalArgumentException(String.format("cannot find field cache for %s.%s", clz.getName(), fieldName));
        }
        return CLASS_FIELD_MAP.get(clz).get(fieldName);
    }

    private static void judgeSameAttribute(Set<Class<? extends IDiffObject>> classes) throws UnsupportedOperationException {
        //获取同名类
        Map<String, List<Class<? extends IDiffObject>>> judgeMap = new HashMap<>(classes.size());
        for (var clz : classes) {
            judgeMap.computeIfAbsent(clz.getSimpleName(), classValue -> new ArrayList<>()).add(clz);
        }

        //获取同名字段
        Map<Map<String, List<Field>>, Map<String, List<String>>> classFieldMap = new HashMap<>();
        for (String className : judgeMap.keySet()) {
            Map<String, List<Field>> sameFieldMap = new HashMap<>();
            Map<String, List<String>> sameClassMap = new HashMap<>();
            List<Class<? extends IDiffObject>> sameClassList = judgeMap.get(className);
            if (sameClassList.size() == 1) {
                continue;
            }

            for (Class<? extends IDiffObject> sameClass : sameClassList) {
                String fullClassName = sameClass.getName();
                Arrays.stream(FieldUtils.getAllFields(sameClass)).forEach(field -> {
                    //sameClassMap为同名class，sameFieldMap为field，便于最后出现异常时打印error日志
                    sameFieldMap.computeIfAbsent(field.getName(), fields -> new ArrayList<>()).add(field);
                    sameClassMap.computeIfAbsent(field.getName(), sameClasses -> new ArrayList<>()).add(fullClassName);
                });
            }
            classFieldMap.put(sameFieldMap, sameClassMap);
        }

        //对同名字段进行判断
        judgeSameField(classFieldMap);
    }

    private static void judgeSameField(Map<Map<String, List<Field>>, Map<String, List<String>>> classFieldMap) throws UnsupportedOperationException {
        AtomicBoolean isSameField = new AtomicBoolean(false);
        classFieldMap.forEach((sameFieldMap, sameClassMap) -> {
            for (String fieldName : sameFieldMap.keySet()) {
                List<Field> sameFieldList = sameFieldMap.get(fieldName);
                int diffIgnoreCount = sameFieldList.size();
                int dbIgnoreCount = diffIgnoreCount;

                if (diffIgnoreCount == 1) {
                    continue;
                }

                for (Field sameField : sameFieldList) {
                    final JSONField jsonField = sameField.getAnnotation(JSONField.class);
                    if (jsonField != null && !jsonField.serialize()) {
                        diffIgnoreCount--;
                    }

                    if (!sameField.isAnnotationPresent(Transient.class)) {
                        dbIgnoreCount--;
                    }
                }

                final int sameFiledSize = sameFieldList.size();
                if (dbIgnoreCount < sameFiledSize && dbIgnoreCount > 0) {
                    logger.error("Db ignore. same Classes: {}, same Field: {}", sameClassMap.get(fieldName), fieldName);
                    isSameField.set(true);
                }
                if (diffIgnoreCount < sameFiledSize && diffIgnoreCount > 0) {
                    logger.error("Diff ignore. same Classes: {}, same Field: {}", sameClassMap.get(fieldName), fieldName);
                    isSameField.set(true);
                }
            }
        });

        if (isSameField.get()) {
            throw new UnsupportedOperationException("don't allow same Field in same Class");
        }
    }

    private static void cacheDiffClasses(Set<Class<? extends IDiffObject>> classes) throws UnsupportedOperationException {
        //起服前的检查
        judgeSameAttribute(classes);

        for (var clz : classes) {
            var fields = FieldUtils.getAllFields(clz);
            Map<String, Field> fieldMap = Maps.newHashMapWithExpectedSize(fields.length);

            for (var field : fields) {
                // 记录客户端忽略属性
                String fPath = DiffPathUtil.getClassFieldPath(clz, field.getName());
                final JSONField jsonField = field.getAnnotation(JSONField.class);
                if (jsonField != null && !jsonField.serialize()) {
                    logger.debug("client ignore [{}]", fPath);
                    DIFF_IGNORE_SET.add(fPath);
                }

                // 记录数据入库忽略属性
                if (field.isAnnotationPresent(Transient.class)) {
                    logger.debug("db ignore [{}]", fPath);
                    DB_IGNORE_SET.add(fPath);
                }

                fieldMap.put(field.getName(), field);
            }
            CLASS_FIELD_MAP.put(clz, fieldMap);
        }
    }
}
