plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    // Đọc app/google-services.json (cấu hình project Firebase, không phải mật khẩu)
    alias(libs.plugins.google.services)
}

android {
    namespace = "vn.edu.umt.tour"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "vn.edu.umt.tour"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Giao diện: Fragment, danh sách, kéo để làm mới
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.swiperefreshlayout)

    // ViewModel và coroutine gắn với vòng đời
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Firebase: đăng nhập (Authentication) và dữ liệu (Firestore). BoM chọn phiên bản cho từng thư viện.
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // Cơ sở dữ liệu trên máy
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Chạy nền (nhắc lịch, theo dõi đơn)
    implementation(libs.androidx.work.runtime.ktx)

    // Tải ảnh từ URL
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
