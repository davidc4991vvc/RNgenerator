/**
 * Created by Alexander Sapelkin on 11.05.2017.
 */

package ru.alfabank.releaseNotes


import com.sun.xml.internal.ws.client.sei.ResponseBuilder
import md
import net.steppschuh.markdowngenerator.text.Text
import net.steppschuh.markdowngenerator.text.heading.Heading
import org.gradle.api.*;
import org.gradle.api.plugins.*
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.wrapper.*
import java.io.File
import java.util.*
import RNGenTask

class RnGeneratorPlugin  : Plugin<Project>{
    var project: Project? = null


    override fun apply(project: Project?) {
        val groovyTask: groovy.lang.Closure<Any?>
        //project?.task(hashMapOf("type" to RNGenTask()),"generateRn")
        project?.tasks?.create("genRn",RNGenTask::class.java)
        if(project?.extensions?.extraProperties?.properties!= null){
            try{
                var properties = project?.extensions?.extraProperties?.properties
                var ReleaseNotes: StringBuilder = StringBuilder().
                        append("Release Notes\n" +
                                "=============\n" +
                                "${properties?.get("retailDescription")}\n" +
                                "--------------------------------------------------------\n" +
                                "## ${properties?.get("mnemonic")}\n" +
                                "### ${properties?.get("taskID")}\n" +
                                "ЗДЕСЬ БУДЕТ ИСТОРИЯ\n" +
                                "--------------------------------------------------------\n" +
                                "## Описание" +
                                "${properties?.get("retailDescription")}\n" +
                                "##ЗДЕСЬ БУДУТ ЗАВИСИМОСТИ\n" +
                                "--------------------------------------------------------\n" +
                                "## Инсталяция\n" +
                                "### До инсталляции\n" +
                                "Не требуется" +
                                "### Установка" +
                                "* Перенести файлы с исходными текстами **${properties?.get("mnemonic")}**\n" +
                                "* Войти авторизованным пользователем в юнит «xxx»\n" +
                                "* Откомпилировать и запустить программу: **${properties?.get("installerName")}**\n" +
                                "* Проверить, что появились новые и замещаемые объекты:\n\n" +
                                "В библиотеке ****" )
                //File("TEST.md").writeText(ReleaseNotes.toString())

                val ReleaseNote= md {
                    h1{+"Release Notes"}
                    h2{properties?.get("retailDescription")}
                    h2{properties?.get("mnemonic")}
                    h3{properties?.get("taskID")}
                    h2{+"ЗДЕСЬ БУДУТ ЗАВИСИМОСТИ"}
                    t{properties?.get("as400Dependencies")}
                    h2{+"Инсталляция"}
                    h3 {+ "До инсталляции" }
                    t{+"Не требуется"}
                    h3{+"Установка"}
                    pl{+"Перенести файлы с исходными текстами **${properties?.get("mnemonic")}"}
                    pl{+"Войти авторизованным пользователем в юнит «xxx»"}
                    pl{+"Откомпилировать и запустить программу: **${properties?.get("installerName")}**"}

                }
                File("TEST.md").writeText(ReleaseNote.toString())


                //println(project?.extensions?.extraProperties?.get("as400programs"))

            }catch (e: Exception){
                e.printStackTrace()
            }



        }
        project?.extensions?.extraProperties?.properties?.forEach(::println)

    }

}

