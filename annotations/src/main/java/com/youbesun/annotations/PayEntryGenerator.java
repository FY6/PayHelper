package com.youbesun.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wfy
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PayEntryGenerator {
    String packageName();
}
