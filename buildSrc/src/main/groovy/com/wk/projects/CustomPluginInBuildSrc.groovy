package com.wk.projects


import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPluginInBuildSrc implements Plugin<Project>{
    @Override
    void apply(Project project) {
        println "CustomPluginInBuildSrc apply"
        def extension=project.extensions.create('customPluginExtension',CustomPluginExtension)
//        def extension1=project.extensions.create('customPluginExtension1',com.wk.projects.CustomPluginExtension1)

        project.task('CustomPluginInBuildSrc') {
            doLast {
                println extension.msg
//                println extension1.msg
            }
        }
    }

}