package com.luan.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class TestPlugin extends Transform implements Plugin<Project> {

    @Override
    void apply(Project project) {
        log("init my plugin.")
//        def extensions = project.extensions
//        def appExtension = extensions.getByType(AppExtension.class)
//        appExtension.registerTransform(this)
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        log("start")
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider
        if (outputProvider != null)
            outputProvider.deleteAll()
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                handlerDirctoryInput(directoryInput, outputProvider)
            }
        }
        log("end")
    }

    static void handlerDirctoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        if (directoryInput.file.isDirectory()) {
            directoryInput.file.eachFileRecurse { File file ->
                def name = file.name
                log("class:"+name)
                if (name.endsWith(".class") && !name.startsWith("R\$")
                        && name == "R.class" && name == "BuildConfig.class"
                        && "android/support/v4/app/FragmentActivity.class" == name) {
//                    log("class:"+name)
                    def classReader = new ClassReader(file.bytes)
                    def classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    def adapter = new MethodClassAdapter()
                    classReader.accept(adapter,0)
                    def output = new FileOutputStream(file.parentFile.absolutePath + File.separator + name)
                    output.write(classWriter.toByteArray())
                    output.close()
                }
            }
        }
        def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file,dest)
    }

    @Override
    String getName() {
        return "MyPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }


    static void log(String content) {
        System.out.println("=======lucas=======")
        System.out.println(content)
        System.out.println("===================")
    }
}