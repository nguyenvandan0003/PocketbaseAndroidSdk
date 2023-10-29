plugins {
	id ("com.android.library")
	id("org.jetbrains.kotlin.android")
	kotlin("kapt")
	id("com.google.dagger.hilt.android")
}

android {
	namespace = "com.dannv.pocketbasesdk"
	compileSdk = 33

	defaultConfig {
		minSdk = 24
		targetSdk = 33


		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			buildConfigField("boolean", "ISLOG", "true")
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}

		debug {
			buildConfigField("boolean", "ISLOG", "false")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.9.0")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

	//retrofit
	implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation ("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

	// hilt
	implementation ("com.google.dagger:hilt-android:2.45")
	kapt("com.google.dagger:hilt-android-compiler:2.44")
	// commom base
	implementation ("com.github.nguyenvandan1234:AppUtils2:0.1.0")
	implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

}

// Allow references to generated code
kapt {
	correctErrorTypes = true
}