import jetbrains.buildServer.configs.kotlin.v2019_2.*

class MyBaseTest(btName: String, btDescription: String, init: BuildType.() -> Unit = {}) : BuildType({
    name = btName
    description = btDescription

    steps {

    }

    dependencies {
        snapshot(Settings.Backend_TryActiveMq) {}
    }

})