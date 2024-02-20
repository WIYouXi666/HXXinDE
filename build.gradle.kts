buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository") }
        maven { url = uri("https://jitpack.io") }
    }

    val kotlinVersion = "1.5.21"

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
    }
}

group = "meow0x7e"
version = "v0.2.2"

val mindustryVersion = "v146"
val jabelVersion = "93fde537c7"
val sdkHome: String? = (System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT"))

plugins {
    kotlin("jvm") version ("1.5.21") apply true
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    api(kotlin("stdlib"))
    compileOnly("com.github.Anuken.Arc:arc-core:${mindustryVersion}")
    compileOnly("com.github.Anuken.Mindustry:core:${mindustryVersion}")

    annotationProcessor("com.github.Anuken:jabel:$jabelVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

sourceSets {
    main {
        java {
            srcDirs("src")
        }
    }
}

val jar = tasks.jar.get()

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${project.name}Desktop.jar")

    from(
        configurations.runtimeClasspath.get().files()
            .map { if (it.isDirectory) it else zipTree(it) })
    from(rootDir) { include("LICENSE", "mod.hjson") }
    from("assets/") { include("**") }
}

tasks.register("jarAndroid") {
    dependsOn("jar")
    doLast {
        if (!File(sdkHome!!).exists()) throw GradleException("No valid Android SDK found. Ensure that ANDROID_HOME is set to your Android SDK directory.")

        val platformRoot =
            File("${sdkHome}/platforms/").listFiles()?.find { File(it!!, "android.jar").exists() }
                ?: throw GradleException("No android.jar found. Ensure that you have an Android platform installed.")

        val buildToolRoot =
            File("${sdkHome}/build-tools/").listFiles()?.find { File(it, "d8").exists() }
                ?: throw GradleException("No d8 found. Ensure that you have an Android build-tool installed.")

        //dex and desugar files - this requires d8 in your PATH
        project.exec {
            commandLine(ArrayList<String>().apply {
                add(File(buildToolRoot, "d8").absolutePath)
                //collect dependencies needed for desugaring
                addAll(listOf(
                    *configurations.compileClasspath.get().toList().toTypedArray(),
                    *configurations.runtimeClasspath.get().toList().toTypedArray(),
                    File(platformRoot, "android.jar")
                ).flatMap {
                    listOf("--classpath", it.absolutePath)
                })
                add("--min-api")
                add("14")
                add("--output")
                add("${project.name}Android.jar")
                add("${project.name}Desktop.jar")
            })
            workingDir = File("${buildDir}/libs/")
        }
    }
}

tasks.register("deploy", Jar::class) {
    dependsOn(tasks.getByName("jarAndroid"))
    dependsOn("jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("${project.name}-${project.version}.jar")

    from(
        zipTree("${buildDir}/libs/${project.name}Desktop.jar"),
        zipTree("${buildDir}/libs/${project.name}Android.jar")
    )

    doLast {
        delete("${buildDir}/libs/${project.name}Desktop.jar")
        delete("${buildDir}/libs/${project.name}Android.jar")
    }
}
