import com.erp.core.groovy.GroovyScriptTemplate

class LocalGroovy extends GroovyScriptTemplate {

    @Override
    Object invoke() {
        return getBean(SayHelloService.class).say("我是王岩")
    }
}