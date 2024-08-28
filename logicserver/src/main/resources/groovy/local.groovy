import com.erp.core.groovy.GroovyScriptTemplate
import com.erp.core.servernode.ServiceNodeHelper

class LocalGroovy extends GroovyScriptTemplate {

    @Override
    Object invoke() {
        return getBean(ServiceNodeHelper.class).getLogicServerNode();
    }
}