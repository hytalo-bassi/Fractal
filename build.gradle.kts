plugins {
    java
    application
}

group = "com.fractal"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("Main")
}

tasks.test {
    useJUnitPlatform()
}
