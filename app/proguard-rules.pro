# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-printusage usage.txt

-keep class com.islam.hesn.myapplication.bible.model.response.bible.Book { *; }
-keep class com.islam.hesn.myapplication.bible.model.response.bible.Chapter { *; }
-keep class com.islam.hesn.myapplication.bible.model.response.bible.Verse { *; }

-keep class com.islam.hesn.myapplication.bible.model.response.search.BibleSearchRes { *; }

-keep class com.islam.hesn.myapplication.quran.model.response.arabic.QuranBaseResponse { *; }
-keep class com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem { *; }

-keep class com.islam.hesn.myapplication.quran.model.response.translation.AyaTranslationItem { *; }
-keep class com.islam.hesn.myapplication.quran.model.response.translation.Aya { *; }

-keep class com.islam.hesn.myapplication.youtube.model.response.YoutubeVideosResponse { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.Video { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.Snippet { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.ResourceId { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.Thumbnails { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.ImageRes { *; }

-keep class com.islam.hesn.myapplication.youtube.model.response.YoutubeSearchVideosResponse { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.SearchVideo { *; }

-keep class com.islam.hesn.myapplication.youtube.model.response.ErrorRes { *; }
-keep class com.islam.hesn.myapplication.youtube.model.response.Error { *; }

-keep class com.shockwave.**

-keep class com.pierfrancescosoffritti.androidyoutubeplayer.** { *; }

-keepnames class com.pierfrancescosoffritti.youtubeplayer.*

-dontwarn org.jetbrains.annotations.**

# for google play services
-dontwarn com.google.android.gms.auth.GoogleAuthException
-dontwarn com.google.android.gms.auth.GooglePlayServicesAvailabilityException
-dontwarn com.google.android.gms.auth.UserRecoverableAuthException
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

# for retrofit becaude gradle 8.1.+
# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
