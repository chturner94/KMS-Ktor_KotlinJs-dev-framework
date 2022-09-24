package com.christianhturner.application.templates

import io.ktor.server.html.*
import kotlinx.css.body
import kotlinx.css.nav
import kotlinx.html.*

public class LayoutTemplate: Template<HTML> {
    override fun HTML.apply() {
        val header = Placeholder<FlowContent>()
        val content = TemplatePlaceholder<FlowContent>()
        head {
            link(rel = "stylesheet", href = "/styles.css", type = "text/css")
            script(src="/static/KMS-v2.js") {}
        }
        body {
            div {
                nav("navbar navbar-expand-lg navbar-light bg-light") {
                    a("navbar-brand") {
                        href = "/"
                        +"Christian H. Turner"
                    }
                    a("nav-link") {
                        href = "/about"
                        +"About"
                    }
                    a("nav-link") {
                        href = "/contact"
                        +"Contact"
                    }
                }
            }
            h1 {
                insert(header)
                insert(ContentTemplate(), content)
            }
        }
    }
}

public class ContentTemplate: Template<FlowContent> {
    val articleTitle = Placeholder<FlowContent>()
    val articleText = Placeholder<FlowContent>()
    override fun FlowContent.apply() {
        article {
            h2 {
                insert(articleTitle)
            }
            p {
                insert(articleText)
            }
        }
    }
}