KUTUMAS WebView Final v1.3 (Build-ready)

What's new in v1.3:
- Gradle build action updated to use gradle/gradle-build-action (no gradlew required)
- Auto-reload on network reconnect
- Splash screen (green theme)
- Offline page and exit confirmation
- Auto-update prompt from http://modalcircle.sytes.net/app_version.json
- AdMob test banner integrated (banner ID: ca-app-pub-3940256099942544/6300978111)

How to build:
1. Upload all files to GitHub repo (root). Ensure .github/workflows/android-build.yml exists (it does).
2. Add Actions secrets: KEYSTORE_BASE64, KEYSTORE_PASS, KEY_ALIAS, KEY_PASS (use keystore and base64 I provided earlier).
3. In Actions tab, run 'Build KUTUMAS APK' workflow. Wait ~10 minutes.
4. Download artifact 'kutumas-release-apk' and install on device.
5. Upload the APK to http://modalcircle.sytes.net/kutumas.apk and setup app_version.json to enable in-app update prompt.

Notes:
- The app uses AdMob test IDs. Replace with your AdMob IDs before publishing.
- The app opens http://modalcircle.sytes.net by default. Change in MainActivity if needed.
