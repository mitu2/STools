import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.vaadin") version "22.0.4"
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
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
    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/spring/")
    }
    mavenCentral()
}

extra["vaadinVersion"] = "22.0.4"
extra["log4j2.version"] = "2.17.1"
extra["zxingVersion"] = "3.5.0"


dependencies {
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.17.1"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.session:spring-session-core")
    implementation("com.vaadin:vaadin-spring-boot-starter:22.0.4")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java:8.0.29")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
    implementation("org.springframework.boot:spring-boot-starter-security")
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    // https://mvnrepository.com/artifact/com.google.zxing/core
    implementation("com.google.zxing:core:${property("zxingVersion")}")
    // https://mvnrepository.com/artifact/com.google.zxing/javase
    implementation("com.google.zxing:javase:${property("zxingVersion")}")
//    implementation("io.springfox:springfox-boot-starter:3.0.0")
    // https://mvnrepository.com/artifact/com.github.mvysny.karibudsl/karibu-dsl-v23
    implementation("com.github.mvysny.karibudsl:karibu-dsl:1.1.3")
//    implementation("org.springframework.security:spring-security-config")

}

dependencyManagement {
    imports {
        mavenBom("com.vaadin:vaadin-bom:${property("vaadinVersion")}")
    }
}
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.logging.log4j") {
            useVersion("2.17.1")
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
