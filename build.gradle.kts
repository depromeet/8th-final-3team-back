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

val fuelVersion = "2.3.0"
val fuelResultVersion = "3.1.0"
val swagger2Version = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Modules
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Fuel
    implementation("com.github.kittinunf.fuel:fuel:$fuelVersion")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:$fuelVersion")
    implementation("com.github.kittinunf.result:result:$fuelResultVersion")

    // Swagger
    implementation("io.springfox:springfox-boot-starter:$swagger2Version")

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
