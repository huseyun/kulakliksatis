# Web Frontend Görev Dağılımı ve Prensipleri

**Web Frontend Adresi:** [frontend.famanas.xyz](https://famanas.xyz)

**Teknoloji Yığını:** `[React, TailwindCSS, Axios]`

Bu doküman, web uygulamasının kullanıcı arayüzü (UI) geliştirme süreçlerini, temel prensipleri ve grup üyelerinin sayfa bazlı sorumluluklarını özetlemektedir.

## 👥 Görev Dağılımı

Her ekip üyesi, kendisine atanan bileşenlerin/sayfaların tasarımından, apilerle bağlanmasından ve düzgün çalışmasından sorumludur.

1. [Hüseyin Akaslan Web Frontend Görevleri](Huseyin-Akaslan/Huseyin-Akaslan-Web-Frontend-Gorevleri.md)
2. [Orhan Selman Yalçın Web Frontend Görevleri](Orhan-Selman-Yalcin/Orhan-Selman-Yalcin-Web-Frontend-Gorevleri.md)

---

## 🏗️ Genel Prensipler

### 1. Tasarım ve Kullanıcı Deneyimi (UI/UX)
* **Mobil Öncelikli (Mobile-First):** Tüm sayfalar öncelikle mobil cihazlara uygun tasarlanmalı, ardından tablet ve masaüstü ekranlar için genişletilmelidir.
* **Tutarlılık:** Proje genelinde belirlenen ortak CSS framework'ü (veya kütüphanesi) dışına çıkılmamalı, renk paleti ve tipografi standart tutulmalıdır.

### 2. State Yönetimi ve API İletişimi
* **Merkezi İstekler:** Backend ile iletişimde HTTP istemcisi (örn. Axios) tek bir merkezden yapılandırılmalı; hata yönetimi (error handling) interceptor'lar aracılığıyla global olarak ele alınmalıdır.
* **Veri Yönetimi:** Sayfalar arası paylaşılan veriler (kullanıcı oturumu, tema vb.) global state (Context API, Redux vb.) ile, sadece bir bileşeni ilgilendiren veriler ise local state ile yönetilmelidir.

### 3. Performans ve Erişilebilirlik
* **Optimizasyon:** Görseller sıkıştırılarak kullanılmalı, gereksiz paket/kütüphane kurulumundan kaçınılarak "bundle" boyutu küçük tutulmalıdır.
* **Kullanılabilirlik:** Semantik HTML etiketleri kullanılmalı, görsellere `alt` etiketleri eklenerek temel erişilebilirlik (Accessibility) ve SEO kurallarına uyulmalıdır.

### 4. Geliştirme ve Yayınlama (Deployment)
* **Klasör Yapısı:** Bileşenler (components), sayfalar (pages) ve yardımcı fonksiyonlar (utils) proje klasör yapısına uygun, düzenli bir şekilde ayrılmalıdır.
* **Sürüm Kontrolü ve Build:** Kodlar ana branch'e (main/master) gönderilmeden önce çalışır durumda olduğundan emin olunmalı, yayınlama işlemleri CI/CD süreçleri (Vercel, Netlify vb.) ile otomatikleştirilmelidir.