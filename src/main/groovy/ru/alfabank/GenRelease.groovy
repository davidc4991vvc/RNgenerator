package ru.alfabank

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by Alexander Sapelkin on 31.05.2017.
 */
class GenRelease extends DefaultTask{
    @TaskAction
    def genRelease(){
        def e = project.extensions
        def rn = "# Release Notes \n"
        rn+="##Модуль $e.ext.retailDescription\n"
        rn+="###$e.ext.enhMnemonic\n"
        rn+="###$e.ext.taskID\n"
        rn+="## Инсталляция\n"
        rn+="## **До инсталляции**\n"
        rn+="###Не требуется\n"
        rn+="## **Установка**\n"
        rn+="  *Перенести файлы с исходными текстами $e.ext.srcPath \n"
        rn+="  *Войти авторизованным пользователем в юнит «xxx»\n"
        rn+="  *Откомпилировать и запустить программу: $e.installation.installerName\n"
        rn+="  *Проверить, что появились новые и замещаемые объекты:\n"

        if(project.hasProperty("as400programs")){
            rn+="###Программы\n"
            rn+="```javascript\n"
            e.as400programs.each{
                rn+="$it.installLib/$it.name\n"
            }
            rn+="```\n"
        }

        if(project.hasProperty("as400displays")){
            rn+="###Дисплейные файлы\n"
            rn+="```javascript\n"
            e.as400displays.each{
                rn+="$it.installLib/$it.name\n"
            }
            rn+="```\n"
        }

        if(project.hasProperty("as400pf")){
            rn+="###Физические файлы\n"
            rn+="```javascript\n"
            e.as400pf.each{
                rn+="$it.installLib/$it.name\n"
            }
            rn+="```\n"
        }

        if(project.hasProperty("as400lf")){
            rn+="###Логические файлы\n"
            rn+="```javascript\n"
            e.as400lf.each{
                rn+="$it.installLib/$it.name\n"
            }
            rn+="```\n"
        }
        rn+="##После инсталляции\n"
        rn+="Не требуется"
        def md = new File('MD.md')
        md.write(rn)
        print(rn)
    }
}
