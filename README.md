## 前言

长期以来我们在开发项目都有很会遇到一些依赖库的问题，1.在项目中依赖okhttp库，我需先到github上搜okhttp库，然后粘贴依赖到项目中。2.项目中的依赖有各种各有的版本，造成了项目体积的变大和编译速度的变慢。3. 如果我们使用的是google推荐的ext方式管理依赖库，由于as不会智能提示，每次新增依赖都需要在ext文件中找半天自己需要的库。这些问题都让我们在开发过程中非常难受😣😣。

* 对于2问题的解决：统一版本依赖，现如今流行的方式有3种，ext管理、buildSrc、ComposingBuilds(组合构建)。

* 对于1问题的解决：将项目开发经常要用到的库的最新版本放到统一版本管理，需要那个直接用哪个，这个问题我已经帮大家解决了，我将最常用的，最新的库都放入版本库中了，只要简单的配置就能轻松的使用。[CommonVersion](https://github.com/AndroidBBQ/CommonVersion)

* 对应3问题的解决：使用buildSrc或ComposingBuilds

### 管理gradle依赖

1. 手动管理，这种就不推荐了，容易造成上面所说的第二种问题

2. ext管理，这是google推荐的方式[Android官方的ext方式](https://developer.android.com/studio/build/gradle-tips#groovy),这种方式的好处就是稳定，坏处就是上面的问题3，没有提示，这点比较头疼。看最新的库，比如okhttp等，慢慢的都不用这种方式了。

3. buildSrc，这种方式现在比较流行，可以有提示，现在一些新的库都在用它。但实际用起来会有点问题，后面说

4. ComposingBuilds，⭐️复合构建的方式，这种方式和buildSrc非常类似，但是性能要比builSrc高些。后面说

   

   后两种方式的目的都是为了能够方便的提示,就像这种效果，用起来非常nice。

   ![](https://user-gold-cdn.xitu.io/2018/5/31/163b57650aacdc7d?imageslim)

### 使用(先使用，然后再讲原理和对比)

#### ComposingBuilds  推荐指数⭐️⭐️⭐️⭐️⭐️

1. 在项目中新建 version(名字随便取) 的Android model，语言选kotlin，修改version模块下的build.gradle如下

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        // 因为使用的 Kotlin 需要需要添加 Kotlin 插件
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10"
    }
}

apply plugin: 'kotlin'
apply plugin: 'java-gradle-plugin'

repositories {
    jcenter()
}

dependencies {
    implementation gradleApi()
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10"

}

compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


gradlePlugin {
    plugins {
        version {
          // 这下面的两个根据自己的模块的包名和类名称变化
            //在模块中需要通过这个id来找到这个插件
            id = 'com.sss.plugin'
            // 插件对应的类
            implementationClass = 'com.sss.plugin.DepsPlugin'
        }
    }
}
```

2. 新建DepsPlugin.kt(这个文件要和上面配置的对的上)文件，然后让它继承Plugin

```kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 这地方如果报红的话，可以不用管
 */
class DepsPlugin:Plugin<Project> {
    override fun apply(target: Project) {

    }
}
```

3. 这样就可以新建自己依赖文件了，比如我想统一 compileSdkVersion ，我就可以新建一个BuildVersion.kt 文件

```kotlin
object BuildVersion {
    val compileSdkVersion = 30
}
```

4. 在setting.gradle中配置

```groovy
includeBuild 'version'
```

5. 在项目的 build.gradle 中配置，让所有的模块全部都添加 `com.sss.plugin` 这个插件

```groovy
plugins {
    id "com.sss.plugin" apply false
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}
subprojects { subproject ->
    // 默认应用所有子模块中
    apply plugin: 'com.sss.plugin'
}
```

6. 在模块中使用,可以看到已经有提示了

![](https://i.loli.net/2021/06/17/RcHdh1rINDvE5Wo.png)



我已经将version模块封装好了，里面还封装了常用的最新的库依赖，项目在[CommonVersion](https://github.com/AndroidBBQ/CommonVersion)中，你可以将里面的version模块拷贝到你的项目目录下，然后再配置4、5、6步，就可以啦。棒棒哒～

#### buildSrc  推荐指数⭐️⭐️⭐️⭐️

1. 新建一个kotlin model,起个名字叫`buildSrc` 一定要叫这个名字，创建好后将setting.gradle中的`·include ':buildSrc'` 给删除掉
2. 然后将buildSrc下的 build.gradle 重命名成 `build.gradle.kts` ,然后将里面的内容全部删除，并新增如下内容

```kotlin
plugins {
    `kotlin-dsl`
}
repositories{
    jcenter()
}
```

3. 前两步其实就已经完成了buildSrc了，然后新建自己依赖文件了，比如我想统一 compileSdkVersion ，我就可以新建一个BuildVersion.kt 文件

```kotlin
object BuildVersion {
    val compileSdkVersion = 30
}
```

4. 然后点击Sync Project with Gradle Files，完成。

![](https://i.loli.net/2021/06/18/6vXaiqzlPhySjJt.png)

5. 在模块中使用,可以看到已经有提示了

![](https://i.loli.net/2021/06/17/RcHdh1rINDvE5Wo.png)

可以看到buildSrc的方式对比ComposingBuilds的方式更加简单哦，但它也有自己的缺点后面说。

我已经将buildSrc模块封装好了，里面还封装了常用的最新的库依赖，项目在[CommonVersion](https://github.com/AndroidBBQ/CommonVersion)中，你可以将里面的buildSrc模块拷贝到你的项目目录下，然后再配置4、5步，就可以啦。棒棒哒～

#### ext依赖 推荐指数⭐️⭐️

这个google推荐的方式，相对来说比较稳定，更加简单，我们只需要将依赖放到一个依赖文件中，然后项目的build.gradle通过 `apply from:"config.gradle"` 来引用，就可以在项目中使用了，但没有依赖提示，会造成上面说的3问题，但仍然是个非常好的方式。

我已经将这个 config.gradle 封装好了，里面还封装了常用的最新的库依赖，项目在[CommonVersion](https://github.com/AndroidBBQ/CommonVersion)中

，你可以将其中的 `config.gradle` 文件拷贝到你的项目中，然后在项目的 build.gradle 增加下面一行代码，项目就可以使用了

```groovy
apply from: "config.gradle"

//引用了之后在要使用的模块中，通过这样的方式引用
implementation rootProject.ext.AndroidX.annotation
```

### 原理和对比

##### buildSrc

我们来看看gradle官方给出的 [gradle文档](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#declare_properties_in_gradle_properties_file) ,buildSrc会优先于脚本插件的。

![](https://i.loli.net/2021/06/18/TI6UGonmXhgMRWe.png)

buildSrc也会有一些缺点，可以看到，如果buildSrc有些改动，比如我将一个依赖从1.0升级到了2.0会导致整个项目过时，需要重新构建整个项目。

![](https://i.loli.net/2021/06/18/mRPelHOhZ2Xkwiz.png)

##### ComposingBuilds 

我们来看看gradle官方文档[gradle文档](https://docs.gradle.org/current/userguide/composite_builds.html) ,它和buildSrc相比较，它并不会重新构建整个项目，所以对比下来性能更高些。

![](https://i.loli.net/2021/06/18/LO49kYuVtdHJ1wn.png)

