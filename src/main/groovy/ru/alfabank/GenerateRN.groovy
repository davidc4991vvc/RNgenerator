package ru.alfabank
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * Created by Alexander Sapelkin on 31.05.2017.
 */
class GenerateRN implements Plugin<Project> {
    void apply(Project project) {
        project.task('generateReleaseNotes', type: GenRelease)
    }
}
