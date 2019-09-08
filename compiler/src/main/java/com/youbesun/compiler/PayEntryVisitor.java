package com.youbesun.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import java.io.IOException;

/**
 * @author wfy
 */
final class PayEntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {

    private final Filer FILER;
    private String mPackageName = null;

    PayEntryVisitor(Filer FILER) {
        this.FILER = FILER;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        generateJavaCode();
        return p;
    }


    private void generateJavaCode() {
        final TypeSpec targetActivity =
                TypeSpec.classBuilder(Constant.GENERATOR_WXPAYENTRYACTIVITY)
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .superclass(
                                ClassName.get(Constant.TEMPPATE_PACKAGE,
                                        Constant.WXPAY_ENTRY_TEMPPATE_CALSS_NAME)
                        ).build();
        final JavaFile javaFile = JavaFile
                .builder(mPackageName + Constant.KEY_WXAPI, targetActivity)
                .addFileComment("微信支付入口文件")
                .build();
        try {
            javaFile.writeTo(FILER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
