import com.finance.hodl.FlavorDimension
import com.finance.hodl.SnapcastFlavor

plugins {
    id("hodl.android.application")
    id("hodl.android.application.compose")
    id("hodl.android.hilt")
}

android {
    namespace = "com.finance.hodl"
    defaultConfig {
        applicationId = "com.finance.hodl"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        missingDimensionStrategy(FlavorDimension.contentType.name, SnapcastFlavor.demo.name)
    }

    flavorDimensions += "poc"
    productFlavors {
        create("demo") {
            dimension = "poc"
        }
        create("prod") {
        }
    }
    
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":designsystem"))
    implementation(libs.core.ktx)
    implementation(libs.lifecycler)
    implementation(libs.activity)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.testing)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}