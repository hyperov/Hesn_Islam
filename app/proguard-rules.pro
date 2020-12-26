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

-keep class com.islam.hesn.myapplication.bible.model.response.bible.BibleResponse { *; }
-keep class com.islam.hesn.myapplication.bible.model.response.bible.Book { *; }
-keep class com.islam.hesn.myapplication.bible.model.response.bible.Chapter { *; }
-keep class com.islam.hesn.myapplication.bible.model.response.bible.Verse { *; }

-keep class com.islam.hesn.myapplication.bible.model.response.translation.BibleVerseTranslationResponse { *; }
-keep class com.islam.hesn.myapplication.bible.model.response.translation.Book { *; }

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