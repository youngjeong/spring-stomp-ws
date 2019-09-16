import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		"classpath"(group = "org.springframework.boot", name = "spring-boot-gradle-plugin", version = "2.1.0.RELEASE")
		"classpath"(group = "org.jetbrains.kotlin", name = "kotlin-gradle-plugin", version = "1.3.31")
		"classpath"(group = "org.jetbrains.kotlin", name = "kotlin-allopen", version = "1.3.31")
	}
}

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.3.31"
	id("org.jetbrains.kotlin.plugin.spring") version "1.3.31"
	id("org.springframework.boot") version "2.1.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "kr.coinone"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
		freeCompilerArgs = listOf("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-amqp")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.webjars:webjars-locator-core")
	implementation("org.webjars:sockjs-client:1.0.2")
	implementation("org.webjars:stomp-websocket:2.3.3")
	implementation("org.webjars:bootstrap:3.3.7")
	implementation("org.webjars:jquery:3.1.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
