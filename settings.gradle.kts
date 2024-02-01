pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
    includeBuild("build-logic")
}

rootProject.name = "study-crm"

includeBuild("platforms")

includeBuild("auth-service/auth-service-api-messaging")
includeBuild("auth-service/auth-service-api-web")
includeBuild("auth-service/auth-service-domain")
includeBuild("auth-service/auth-service-main")
includeBuild("auth-service/auth-service-messaging")
includeBuild("auth-service/auth-service-persistence")
includeBuild("auth-service/auth-service-web")
includeBuild("common/common-domain")
includeBuild("common/common-web")
includeBuild("contact-service/contact-service-api-web")
includeBuild("contact-service/contact-service-domain")
includeBuild("contact-service/contact-service-main")
includeBuild("contact-service/contact-service-persistence")
includeBuild("contact-service/contact-service-web")
includeBuild("organization-service/organization-service-api-messaging")
includeBuild("organization-service/organization-service-api-web")
includeBuild("organization-service/organization-service-domain")
includeBuild("organization-service/organization-service-main")
includeBuild("organization-service/organization-service-persistence")
includeBuild("organization-service/organization-service-sagas")
includeBuild("organization-service/organization-service-web")