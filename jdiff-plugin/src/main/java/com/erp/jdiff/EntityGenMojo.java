package com.erp.jdiff;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "entity-gen", defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class EntityGenMojo extends AbstractMojo {

    public static List<String> fileName = new ArrayList<>();

    @Parameter(defaultValue = "${project.build.outputDirectory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            List<File> classFiles = getClassFiles(outputDirectory);
            for (File file : classFiles) {
                System.out.println("jdiff-plugin >> start: " + file.getName());

                fileName.remove(file.getName());
                weaveClass(file);
            }
        } catch (Exception e) {
            System.out.println("error "+fileName);
            throw new MojoExecutionException("Error weaving classes", e);
        }
    }

    /**
     * 获取所有的 class 文件
     */
    private List<File> getClassFiles(File dir) {
        List<File> classFiles = new ArrayList<>();
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println("file:" + file.getName());
                fileName.add(file.getName());
                if (file.isDirectory()) {
                    classFiles.addAll(getClassFiles(file));
                } else if (file.getName().endsWith(".class")) {
                    classFiles.add(file);
                }
            }
        }

        return classFiles;
    }

    private void weaveClass(File file) throws IOException {
        System.out.println("deal: " + file.getAbsolutePath());
        FileInputStream fis = new FileInputStream(file);
        ClassReader cr = new ClassReader(fis);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        CustomClassVisitor cv = new CustomClassVisitor(cw);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        fis.close();

        if (cv.isSubClassDiffObject()) {
            System.out.println("isSubClassDiffObject:" + file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(cw.toByteArray());
            fos.close();
        }
    }

}
