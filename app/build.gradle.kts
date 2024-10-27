plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "ma.ensa.projetws.to_do_list"
    compileSdk = 34

    defaultConfig {
        applicationId = "ma.ensa.projetws.to_do_list"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.core.ktx)

    // JUnit 4 (par défaut pour les tests Android instrumentés)
    testImplementation(libs.junit)

    // AndroidX pour les tests instrumentés
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // JUnit 5 pour les tests unitaires
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // Mockito pour les mocks dans les tests unitaires
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")

}

// Configuration de Gradle pour utiliser JUnit 5
tasks.withType<Test> {
    useJUnitPlatform() // Utiliser JUnit Platform pour exécuter JUnit 5
}
