plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "top.inept.mediapreview"
    compileSdk = 35

    defaultConfig {
        applicationId = "top.inept.mediapreview"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Voyager
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.tab.navigator)
    implementation(libs.voyager.bottom.sheet.navigator)

    //Media3
    implementation("androidx.media3:media3-exoplayer:1.7.1")    //ExoPlayer 核心播放引擎
    implementation("androidx.media3:media3-ui:1.7.1")           //Views 基础播放控件
    implementation("androidx.media3:media3-common:1.7.1")       //通用基础设施（日志、异常、状态枚举、事件回调等）
    implementation("androidx.media3:media3-session:1.7.1")      //媒体会话与控制（锁屏、通知、Android Auto）

    //Coli
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")        //网络加载
    implementation("me.saket.telephoto:zoomable-image-coil3:0.16.0")    //缩放

    //Scale
    implementation("com.jvziyaoyao.scale:image-viewer:1.1.0-alpha.7")

    implementation("androidx.compose.material:material-icons-extended:1.7.8")
}