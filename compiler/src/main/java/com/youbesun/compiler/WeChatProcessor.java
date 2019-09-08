package com.youbesun.compiler;

import com.youbesun.annotations.AppRegisterGenerator;
import com.youbesun.annotations.PayEntryGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wfy
 * <p>
 * <p>
 * PackageElement 表示一个包程序元素
 * TypeElement 表示一个类或接口程序元素
 * VariableElement 表示一个字段、enum 常量、方法或构造方法参数、局部变量或异常参数
 * ExecutableElement 表示某个类或接口的方法、构造方法或初始化程序（静态或实例），包括注解类型元素
 * TypeParameterElement 表示一般类、接口、方法或构造方法元素的泛型参数
 * <p>
 * <p>
 * <p>
 * 什么是 AnnotationMirror？
 * 我们自定义 annotation 时常见的@Retention(RetentionPolicy.RUNTIME) 就是一个 AnnotationMirror。
 * <p>
 * 它通过方法 getAnnotationType 来获得具体注解的 DeclaredType 申明类型。
 * 这个注解的申明类型就是 java.lang.annotation.Retention
 * 它通过方法 getElementValues 来获得一个 ExecutableElement - AnnotationValue 列表。
 * 其实这个注解有个隐藏 ExecutableElement：@Retention(value = RetentionPolicy.RUNTIME)，即 value，它对应的 AnnotationValue 是 RetentionPolicy.RUNTIME。
 */
@SuppressWarnings("unused")
public final class WeChatProcessor extends AbstractProcessor {

    /*Types是一个用来处理TypeMirror的工具*/
    private Types typeUtils;

    private Elements elementUtils;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportAnnotations) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }


    @Override
    public synchronized boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        generatePayEntryCode(env);
        generateAppRegisterCode(env);
        return true;
    }

    private void scan(RoundEnvironment env,
                      Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor) {

        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {


            //注解镜像环境
            final List<? extends AnnotationMirror> annotationMirrors =
                    typeElement.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {

                //ExecutableElement 表示注解类型元素
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        : elementValues.entrySet()) {
                    AnnotationValue annotationValue = entry.getValue();
                    //通过注解访问器访问注解的值
                    annotationValue.accept(visitor, null);
                }
            }
        }
    }

    private void generatePayEntryCode(RoundEnvironment env) {
        final PayEntryVisitor payEntryVisitor =
                new PayEntryVisitor(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, payEntryVisitor);
    }

    private void generateAppRegisterCode(RoundEnvironment env) {
        final AppRegisterVisitor appRegisterVisitor =
                new AppRegisterVisitor(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, appRegisterVisitor);
    }
}
