import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.vaadin") version "23.2.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "runstatic"
version = "1.0.beta"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
//    maven {
//        setUrl("https://maven.aliyun.com/repository/public/")
//    }
//    maven {
//        setUrl("https://maven.aliyun.com/repository/spring/")
//    }
    mavenCentral()
}

ext {
    this["vaadin.version"] = "23.2.0"
    this["log4j2.version"] = "2.18.0"
    this["zxing.version"] = "3.5.0"
}


dependencies {
    implementation(platform("org.apache.logging.log4j:log4j-bom:${property("log4j2.version")}"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.session:spring-session-core")
    implementation("com.vaadin:vaadin-spring-boot-starter:${property("vaadin.version")}")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java:8.0.29")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    // https://mvnrepository.com/artifact/com.google.zxing/core
    implementation("com.google.zxing:core:${property("zxing.version")}")
    // https://mvnrepository.com/artifact/com.google.zxing/javase
    implementation("com.google.zxing:javase:${property("zxing.version")}")
//    implementation("io.springfox:springfox-boot-starter:3.0.0")
    // https://mvnrepository.com/artifact/com.github.mvysny.karibudsl/karibu-dsl-v23
    implementation("com.github.mvysny.karibudsl:karibu-dsl:1.1.3")
//    implementation("org.springframework.security:spring-security-config")

}

dependencyManagement {
    imports {
        mavenBom("com.vaadin:vaadin-bom:${property("vaadin.version")}")
    }
}
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.logging.log4j") {
            useVersion(property("log4j2.version") as String)
        }
    }
}

//
//tasks.withType<GradleBuild> {
//    setProperty("vaadin.productionMode", true)
//}

springBoot {
    buildInfo {
        generateBuildProperties()
    }
}

val env = System.getProperty("profile") ?: "dev"

if (env == "prod") {
    vaadin {
        productionMode = true
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
