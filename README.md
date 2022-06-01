# FileSelector
一个简单的文件选择框架
# 使用方式
[![](https://jitpack.io/v/polaris-ni/FileSelector.svg)](https://jitpack.io/#polaris-ni/FileSelector)

- 依赖导入**settings.gradle**
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.polaris-ni:FileSelector:Tag'
}
```
- 需要请求储存读写权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
- Android10以上
```
android:requestLegacyExternalStorage="true"
```
