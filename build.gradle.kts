plugins {
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `java-library`
    application
}

repositories {
    mavenCentral()
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("com.cgvsu.rasterizationfxapp.RasterizationApplication")
}

group = "com.cgvsu"
version = "0.1.0"
description = "TriangleRasterization"
java.sourceCompatibility = JavaVersion.VERSION_18

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
