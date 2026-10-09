LUMINARIA - ANDROID APP (APK)
Read. Count. Shine.

This project wraps the exact Luminaria app (same screens, same buttons) in an
Android app that works fully OFFLINE. It uses no internet permission.
The phone's own voice is used for the "Read aloud" buttons.

=== EASIEST: build the APK online (no Android Studio, no coding) ===
1. Create a free account at github.com and make a new empty repository.
2. Upload ALL files and folders from this project (keep the folder structure,
   including the hidden ".github" folder).
3. Open the repository's "Actions" tab > "Build Luminaria APK" > "Run workflow".
4. Wait about 5 minutes. Open the finished run and download "Luminaria-APK".
   Unzip it to get  app-debug.apk.

=== OR: build with Android Studio ===
1. Install Android Studio (free) and choose File > Open > this folder.
2. Wait for "Gradle sync" to finish (needs internet once).
3. Menu: Build > Build App Bundle(s) / APK(s) > Build APK(s).
4. Click "locate" to find app-debug.apk
   (app/build/outputs/apk/debug/app-debug.apk).

=== INSTALL ON AN ANDROID PHONE ===
1. Send app-debug.apk to the phone (USB, Bluetooth, Google Drive, messaging).
2. Tap the file. If asked, allow "Install unknown apps" for that app.
3. Tap Install, then Open. Luminaria appears on the home screen with the logo.
4. No internet needed after installing.

=== NOTES ===
- Works on Android 5.0 and above.
- To change the app, replace app/src/main/assets/index.html and rebuild.
- app-debug.apk installs on any phone. To publish on Google Play you need a
  signed release build (Build > Generate Signed Bundle / APK). The 512 px store
  icon is included: ic_launcher-playstore.png
- If the Filipino voice is silent, install "Google Text-to-speech" voices:
  Settings > System > Languages > Text-to-speech output.
