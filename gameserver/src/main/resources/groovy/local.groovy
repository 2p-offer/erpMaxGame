import com.erp.core.groovy.GroovyScriptTemplate
import com.erp.gameserver.test.zkconfigtest.ZkTestConfig

class LocalGroovy extends GroovyScriptTemplate {

    @Override
    Object invoke() {
        return getBean(ZkTestConfig.class)
    }
}