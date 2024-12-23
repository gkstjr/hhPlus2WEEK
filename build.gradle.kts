plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    id("jacoco")
    kotlin("plugin.jpa") version "1.9.0" // JPA 플러그인 추가
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    group = property("app.group").toString()
}

dependencyManagement {
    imports {
        mavenBom(libs.spring.cloud.dependencies.get().toString())
    }
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.spring.boot.configuration.processor)
    testImplementation(libs.spring.boot.starter.test)
    //프로젝트 설정파일 수정 안하기 위해 libs로 통일 x
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.assertj:assertj-core:3.24.2")
    // Spring Boot JPA 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Database Driver (H2, MySQL 등)
    implementation("com.h2database:h2") // H2 데이터베이스 사용 시
    implementation("mysql:mysql-connector-java:8.0.33") // 적절한 버전 사용

}

// about source and compilation
java {
    sourceCompatibility = JavaVersion.VERSION_17
}

with(extensions.getByType(JacocoPluginExtension::class.java)) {
    toolVersion = "0.8.7"
}

// bundling tasks
tasks.getByName("bootJar") {
    enabled = true
}
tasks.getByName("jar") {
    enabled = false
}
// test tasks
tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
}
