/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.5/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    id("com.codedifferently.studycrm.java-library")
}

group = "com.codedifferently.studycrm.organization-service.domain"

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api");
    implementation("jakarta.transaction:jakarta.transaction-api");
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}