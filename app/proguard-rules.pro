-keepattributes LineNumberTable,SourceFile
-renamesourcefileattribute SourceFile
-dontobfuscate

-keepclasseswithmembers public class androidx.recyclerview.widget.RecyclerView { *; }
-keep class com.zk.cluster.auth.ui.fragments.preferences.*
-keep class com.zk.cluster.auth.importers.** { *; }
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }

-dontwarn javax.naming.**
