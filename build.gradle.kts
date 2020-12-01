import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.spring") version "1.4.0"
}

group = "com.harry"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

val swagger2Version = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Modules
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Swagger
    implementation("io.springfox:springfox-boot-starter:$swagger2Version")

    // HttpComponents
    implementation(group = "org.apache.httpcomponents", name = "httpclient", version = "4.5.1")
    implementation(group = "org.apache.httpcomponents", name = "httpcore", version = "4.4")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.bootJar {
    mainClassName = "com.harry.depromeet.ApplicationKt"
    archiveFileName.set("harry.jar")
}