/**
 * Created by sapelkinav on 17/05/2017.
 */
interface Element {
    fun render(builder: StringBuilder, indent: String)
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text")
    }
}

@DslMarker
annotation class MarkdownMarker

@MarkdownMarker
abstract class MarkdownElement(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initMDelement(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$name ")
        for (c in children) {
            c.render(builder, indent)
        }
        builder.append("\n")

    }

    private fun renderAttributes(): String {
        val builder = StringBuilder()
        for ((attr, value) in attributes) {
            builder.append(" $attr=\"$value\"")
        }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

@MarkdownMarker
abstract class MarkdownElementWithEnding(val name: String) : Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initMDelement(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$name ")
        for (c in children) {
            c.render(builder, indent)
        }
        builder.append(" $indent$name")
        builder.append("")

    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}


abstract class MarkdownElementWithText(name: String) : MarkdownElement(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}
abstract class MarkdownElementWithEndingWithText(name: String) : MarkdownElementWithEnding(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class MD : MarkdownElementWithText("") {
    fun h1(init: H1.() -> Unit) = initMDelement(H1(), init)
    fun h2(init: H2.() -> Unit) = initMDelement(H2(), init)
    fun h3(init: H3.() -> Unit) = initMDelement(H3(), init)
    fun h4(init: H4.() -> Unit) = initMDelement(H4(), init)
    fun pl(init: PointList.() -> Unit) = initMDelement(PointList(), init)
    fun bld(init: Bald.() -> Unit) = initMDelement(Bald(), init)



    fun t(init: T.() -> Unit) = initMDelement(T(), init)


}

class H1 : MarkdownElementWithText("#")
class H2 : MarkdownElementWithText("##"){

}
class H3 : MarkdownElementWithText("###")
class H4 : MarkdownElementWithText("####")
class PointList :MarkdownElementWithText("*"){
    fun bld(init: Bald.() -> Unit) = initMDelement(Bald(), init)
}
class T: MarkdownElementWithText(""){
    fun bld(init: Bald.() -> Unit) = initMDelement(Bald(), init)
}

class Bald: MarkdownElementWithEndingWithText("**")

fun md(init: MD.() -> Unit): MD {
    val md = MD()
    md.init()
    return md
}

fun main(args: Array<String>) {
    val markdown = md {

        bld{
            +"hello bitch"
        }
        t{
            +"azazaza"
        }

    }
    print(markdown)
}