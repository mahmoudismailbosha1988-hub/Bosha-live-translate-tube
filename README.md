# Bosha Live Translate Tube 🎬🌍

تطبيق أندرويد يترجم الكلام المسموع من فيديوهات YouTube **فوراً** إلى اللغة العربية باستخدام فقاعة طافية (Floating Overlay).

## ✨ المميزات

- 🎤 **التقاط الصوت الفوري** - التقاط صوت أي تطبيق (YouTube، Netflix، إلخ)
- 🧠 **التعرف على الكلام** - تحويل الصوت إلى نص باستخدام Whisper
- 🌐 **الترجمة الحية** - ترجمة فورية من الإنجليزية إلى العربية
- 💬 **فقاعة طافية** - عرض الترجمة بدون مقاطعة المشاهدة
- ⚡ **معالجة متزامنة** - معالجة سلسة دون تجميد التطبيق

## 🏗️ البنية المعمارية

```
app/src/main/java/com/arabicvideotranslator/
├── MainActivity.kt                 # الشاشة الرئيسية
├── OverlayService.kt              # خدمة الفقاعة الطافية
├── SubtitleManager.kt             # إدارة الترجمة
├── TranslationPipeline.kt         # خط أنابيب المعالجة المتكاملة
├── AppConfig.kt                   # الإعدادات العامة
├── AppConstants.kt                # الثوابت
├── audio/
│   ├── AudioCaptureManager.kt    # إدارة التقاط الصوت
│   └── AudioCaptureService.kt    # خدمة التقاط المستمرة
├── speech/
│   └── SpeechRecognitionEngine.kt # التعرف على الكلام
└── translation/
    └── TranslationEngine.kt       # محرك الترجمة
```

## 🔄 سير العمل

1. **التقاط الصوت** → AudioCaptureService
2. **التعرف على الكلام** → SpeechRecognitionEngine
3. **الترجمة** → TranslationEngine
4. **العرض** → OverlayService

## 🛠️ التقنيات المستخدمة

- **Kotlin** - لغة البرمجة الأساسية
- **Jetpack Compose** - واجهة المستخدم
- **Coroutines** - البرمجة غير المتزامنة
- **Whisper** - التعرف على الكلام
- **OkHttp** - طلبات الشبكة
- **Android Foreground Service** - العمل المستمر

## 📋 المتطلبات

- Android SDK 29+
- Android Studio 2022.1+
- Kotlin 2.0.21+
- Gradle 8.7.3+

## 🚀 كيفية البدء

### 1. استنساخ المشروع
```bash
git clone https://github.com/mahmoudismailbosha1988-hub/Bosha-live-translate-tube.git
cd Bosha-live-translate-tube
```

### 2. فتح المشروع في Android Studio
```bash
studio .
```

### 3. بناء المشروع
```bash
./gradlew build
```

### 4. تشغيل التطبيق
```bash
./gradlew installDebug
```

## 📱 الاستخدام

1. افتح التطبيق
2. اضغط على "ابدأ الترجمة" (Start Translation)
3. منح الأذونات المطلوبة (ميكروفون + الظهور فوق التطبيقات)
4. افتح أي فيديو على YouTube أو تطبيق آخر
5. ستظهر الترجمة تلقائياً في الفقاعة الطافية

## ⚙️ الإعدادات

### تغيير اللغة الهدف
عدّل الثابت في `AppConfig.kt`:
```kotlin
const val DEFAULT_TARGET_LANGUAGE = "ar"  # العربية
```

### تغيير سرعة المعالجة
عدّل الفاصل الزمني في `TranslationPipeline.kt`:
```kotlin
Thread.sleep(100)  # بالميلي ثانية
```

## 🐛 استكشاف الأخطاء

### المشكلة: لا تظهر الترجمة
- تأكد من منح الأذونات
- تحقق من أن الميكروفون يعمل
- تحقق من الاتصال بالإنترنت

### المشكلة: تأخير في الترجمة
- قلل الفاصل الزمني في `TranslationPipeline.kt`
- استخدم جهازاً أحدث
- قلل جودة الصوت إذا لزم الأمر

## 📈 المستقبل

- [ ] دعم لغات متعددة
- [ ] تحسين دقة الترجمة
- [ ] واجهة مستخدم محسّنة
- [ ] إعدادات مخصصة
- [ ] دعم النصوص المرئية (OCR)
- [ ] حفظ السجل (History)

## 🤝 المساهمة

نرحب بمساهماتك! يرجى:

1. عمل Fork للمشروع
2. إنشاء فرع للميزة (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add some AmazingFeature'`)
4. Push إلى الفرع (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

## 📄 الترخيص

هذا المشروع مرخص تحت MIT License - انظر ملف [LICENSE](LICENSE) للتفاصيل.

## 👨‍💻 المؤلف

**Mahmoud Ismail**
- GitHub: [@mahmoudismailbosha1988-hub](https://github.com/mahmoudismailbosha1988-hub)

## 💬 التواصل والدعم

للمزيد من المعلومات أو الدعم:
- قم بفتح [Issue](https://github.com/mahmoudismailbosha1988-hub/Bosha-live-translate-tube/issues)
- راجع [Discussions](https://github.com/mahmoudismailbosha1988-hub/Bosha-live-translate-tube/discussions)

---

**مصنوع بـ ❤️ في مصر**