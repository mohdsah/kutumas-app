KUTUMAS WebView Final Fixed (v1.2)

Features:
- Splash screen, offline page, exit confirm dialog, auto-update prompt
- Auto-reload when network becomes available
- AdMob test banner integrated (App ID: ca-app-pub-3940256099942544~3347511713, Banner ID: ca-app-pub-3940256099942544/6300978111)
- GitHub Actions workflow included for auto-build signed APK

Build & deploy:
1. Upload this project to GitHub (push all files).
2. Add your keystore as GitHub secret KEYSTORE_BASE64, and add secrets KEYSTORE_PASS, KEY_ALIAS, KEY_PASS.
3. In Actions tab, run the workflow 'Build KUTUMAS APK' to generate signed APK.
4. Download APK from Actions artifacts and upload to your hosting at http://modalcircle.sytes.net/kutumas.apk to enable in-app update checks.
