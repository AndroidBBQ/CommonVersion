package com.sss.plugin

private object KotlinVersion {
    val kotlin_version = "1.4.10"
    val koin_version = "3.0.2"
}

/**
 * kotlin 相关依赖
 */
object Kotlin {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${KotlinVersion.kotlin_version}"
    val kotlin_stdlib_jdk7 =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinVersion.kotlin_version}"
    val kotlin_stdlib_jdk8 =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinVersion.kotlin_version}"

    //协程
    val kotlinx_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${KotlinVersion.kotlin_version}"

    //koin
    val koin_core = "io.insert-koin:koin-core:${KotlinVersion.koin_version}"
    val koin_core_ext = "io.insert-koin:koin-core-ext:${KotlinVersion.koin_version}"
    val koin_android = "io.insert-koin:koin-android:${KotlinVersion.koin_version}"
    val koin_android_ext = "io.insert-koin:koin-android-ext:${KotlinVersion.koin_version}"
    val koin_androidx_workmanager =
        "io.insert-koin:koin-androidx-workmanager:${KotlinVersion.koin_version}"
    val koin_androidx_compose = "io.insert-koin:koin-androidx-compose:${KotlinVersion.koin_version}"
    val koin_android_viewmodel = "org.koin:koin-android-viewmodel:${KotlinVersion.koin_version}"
}