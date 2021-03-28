import groovy.util.Node
import org.gradle.api.publish.maven.MavenPublication

fun MavenPublication.configurePom(block: BasePom.() -> Unit) {
    pom {
        withXml{
            asNode().run(::BasePom).run(block)
        }
    }
}

class BasePom(private val node: Node) {
    fun name(name: String) {
        node.appendNode("name", name)
    }

    fun description(description: String) {
        node.appendNode("description", description)
    }

    fun developers(block: Developers.() -> Unit) {
        node.appendNode("developers").run(::Developers).run(block)
    }
}

class Developers(private val node: Node) {
    fun developer(block: Developer.() -> Unit) {
        node.appendNode("developer").run(::Developer).run(block)
    }
}

class Developer(private val node: Node) {
    fun name(name: String) {
        node.appendNode("name", name)
    }

    fun email(email: String) {
        node.appendNode("email", email)
    }
}