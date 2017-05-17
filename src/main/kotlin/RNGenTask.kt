import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Created by sapelkinav on 17/05/2017.
 */
open class RNGenTask : DefaultTask(){
    @TaskAction
    fun genRN(){
        try{
            val properties = project?.extensions?.extraProperties?.properties

            val ReleaseNote= md {
                h1{+"Release Notes"}
                h2{+"${properties?.get("retailDescription")}"}
                h2{+"${properties?.get("mnemonic")}"}
                h3{+"${properties?.get("taskID")}"}
                h2{+"ЗДЕСЬ БУДУТ ЗАВИСИМОСТИ"}
                h2{+"Инсталляция"}
                h3 {+ "До инсталляции" }
                t{+"Не требуется"}
                h3{+"Установка"}
                pl{+"Перенести файлы с исходными текстами **${properties?.get("mnemonic")}"}
                pl{+"Войти авторизованным пользователем в юнит «xxx»"}
                pl{+"Откомпилировать и запустить программу: **${properties?.get("installerName")}**"}

            }
            File("TEST.md").writeText(ReleaseNote.toString())

            println(project?.extensions?.extraProperties?.get("as400programs"))

        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}