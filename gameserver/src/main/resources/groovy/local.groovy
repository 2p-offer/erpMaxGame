import com.erp.core.groovy.GroovyScriptTemplate
import com.erp.gameserver.controller.ZkConfigTestController

class LocalGroovy extends GroovyScriptTemplate {

    @Override
    Object invoke() {
        String value = getBean(ZkConfigTestController.class).returnBody();
        return value
    }
}