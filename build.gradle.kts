plugins {
    id("com.diffplug.spotless") version "7.2.1"
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

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.mockito:mockito-core:5.1.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.1.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

spotless {
    format("misc") {
        target("*.gradle", ".gitattributes", ".gitignore")

        trimTrailingWhitespace()
        leadingSpacesToTabs()
        endWithNewline()
    }

    java {
        target("src/*/java/**/*.java")

        googleJavaFormat()
            .aosp()
            .reflowLongStrings()
            .skipJavadocFormatting()

        formatAnnotations()
        importOrder()
        removeUnusedImports()
    }
}

tasks.test {
    useJUnitPlatform()
}
