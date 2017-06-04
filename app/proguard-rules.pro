# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android_sdk\Android\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#----------------------------------------框架混淆_start---------------------------------------------#
    #---------------------------------RxJava_start----------------------------------------#
    -dontwarn sun.misc.**
    -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
       long producerIndex;
       long consumerIndex;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode producerNode;
    }
    -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode consumerNode;
    }
    #----------------------------------RxJava_end----------------------------------------#


    #----------------------------------Retrofit_start------------------------------------#
    -dontwarn retrofit2.**
    -keep class retrofit2.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions
    #----------------------------------Retrofit_end--------------------------------------#


    #----------------------------------Okhttp3_start-------------------------------------#
    -dontwarn com.squareup.okhttp3.**
    -keep class com.squareup.okhttp3.** { *;}
    -dontwarn okio.**
    #----------------------------------Okhttp3_end---------------------------------------#


    #-----------------------------------Gson_start---------------------------------------#
    -keepattributes Signature-keepattributes *Annotation*
    -keep class sun.misc.Unsafe { *; }
    -keep class com.google.gson.stream.** { *; }
    # Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类
    -keep class com.example.bean.** { *; }
    #-----------------------------------Gson_end-----------------------------------------#


    #-----------------------------------Fresco_start-----------------------------------------#
    -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
    -keep @com.facebook.common.internal.DoNotStrip class *
    -keepclassmembers class * {
        @com.facebook.common.internal.DoNotStrip *;
    }
    -keepclassmembers class * {
        native <methods>;
    }
    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -dontwarn okhttp3.**
    -dontwarn javax.annotation.**
    -dontwarn com.android.volley.toolbox.**
    -dontwarn com.facebook.infer.**
    #-----------------------------------Fresco_end-----------------------------------------#


    #--------------------------------Butterknife_start-------------------------------------#
    -keep class butterknife.** { *; }
    -dontwarn butterknife.internal.**
    -keep class **$$ViewBinder { *; }
    -keepclasseswithmembernames class * {
        @butterknife.* <fields>;
    }
    -keepclasseswithmembernames class * {
        @butterknife.* <methods>;
    }
    #--------------------------------Butterknife_end---------------------------------------#


    #---------------------------------Eventbus_start---------------------------------------#
    -keepattributes *Annotation*
    -keepclassmembers class ** {
        @org.greenrobot.eventbus.Subscribe <methods>;
    }
    -keep enum org.greenrobot.eventbus.ThreadMode { *; }

    # Only required if you use AsyncExecutor
    -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(Java.lang.Throwable);
    }
    #---------------------------------Eventbus_end----------------------------------------#

#--------------------------------------框架混淆_end-------------------------------------------------#




#---------------------------------除框架之外的其他混淆_start----------------------------------------#
    #---------------------------------Webview相关_start------------------------------------#
    #---------------------------------Webview相关_end--------------------------------------#

    #---------------------------------Webview相关_end--------------------------------------#
    #---------------------------------Webview相关_end--------------------------------------#


#---------------------------------除框架之外的其他混淆_end------------------------------------------#





