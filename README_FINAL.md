KUTUMAS WebView APK (built assets)

Features included:
- Splash screen with KUTUMAS text
- Offline page (assets/offline.html) shown when no internet
- Exit confirmation popup when back pressed
- Auto-update check: app requests http://modalcircle.sytes.net/app_version.json and expects JSON like {"version": 2, "url": "http://modalcircle.sytes.net/kutumas.apk"}
  - If version in JSON is greater than app CURRENT_VERSION (1), a prompt appears to download new APK.
  - Host your APK at http://modalcircle.sytes.net/kutumas.apk (upload the signed APK to your server)

How to build signed APK using GitHub Actions:
- Use the auto-build ZIP workflow previously provided (or build locally with Android Studio).
- After building, upload the resulting APK to http://modalcircle.sytes.net/kutumas.apk to enable in-app update prompt.

Notes:
- The app cannot auto-install the downloaded APK silently; users must allow install from unknown sources and install manually after download. The app will open the APK link in browser to initiate download.
- If you want full auto-update + silent install, you'd need system-level permissions (not available to regular apps).

