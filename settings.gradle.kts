dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven ("https://jitpack.io")
    }
}
rootProject.name = "Easy Calculator"
include (":app")
