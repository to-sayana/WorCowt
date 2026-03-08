# Supabase
-keep class io.github.jan.supabase.** { *; }
-keep class io.ktor.** { *; }

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.worcowt.app.**$$serializer { *; }
-keepclassmembers class com.worcowt.app.** { *** Companion; }
-keepclasseswithmembers class com.worcowt.app.** { kotlinx.serialization.KSerializer serializer(...); }
