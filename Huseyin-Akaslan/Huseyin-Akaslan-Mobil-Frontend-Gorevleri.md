# Hüseyin Akaslan'ın Mobil Frontend Görevleri
**Mobile Front-end Demo Videosu:** [Link buraya eklenecek](https://example.com)

## 1. Giriş (Login) Ekranı
- **API Endpoint:** `POST /api/auth/login`
- **Görev:** Kullanıcı giriş işlemi için mobil ekran tasarımı ve implementasyonu
- **UI Bileşenleri:**
  - Uygulama logosu ve başlığı (KulaklıkSatış)
  - Kullanıcı adı input alanı
  - Şifre input alanı (göster/gizle toggle ile)
  - "Giriş Yap" butonu
  - "Hesabınız yok mu? Kayıt olun" linki
  - Loading indicator (giriş işlemi sırasında)
  - Hata mesajı bileşeni
- **Kullanıcı Deneyimi:**
  - Boş alan kontrolü (form gönderilmeden önce)
  - Hata durumlarında kullanıcı dostu mesajlar
  - Başarılı girişte JWT token'a göre otomatik rol tespiti (Admin / Satıcı / Shopper)
  - Her role özel sekme navigasyonuna yönlendirme
  - Token süresi dolmuşsa otomatik login ekranına yönlendirme
  - Uygulama yeniden açıldığında token hafızada varsa otomatik giriş (SecureStore)
  - KeyboardAvoidingView ile klavye açıldığında form kaybolmaz
- **Teknik Detaylar:**
  - Platform: Android (React Native / Expo)
  - JWT token decode edilerek kullanıcı rolü (ADMIN / SELLER / SHOPPER) belirlenir
  - Token expo-secure-store ile güvenli saklanır
  - Zustand ile auth state yönetimi
  - Expo Router ile rol tabanlı navigasyon (/(admin)/, /(seller)/, /(shopper)/)

## 2. Üye Olma (Kayıt) Ekranı
- **API Endpoint:** `POST /api/auth/register`
- **Görev:** Shopper hesabı oluşturma ekranı tasarımı ve implementasyonu
- **UI Bileşenleri:**
  - Kullanıcı adı input alanı
  - E-posta input alanı (keyboard type: email)
  - Şifre input alanı (göster/gizle toggle)
  - "Kayıt Ol" butonu
  - "Zaten hesabınız var mı? Giriş Yap" linki
  - Loading indicator
  - Hata mesajı bileşeni
- **Form Validasyonu:**
  - Tüm alanlar zorunlu kontrolü
  - Hata mesajları kullanıcıya gösterilir
- **Kullanıcı Deneyimi:**
  - Başarılı kayıt sonrası login ekranına yönlendirme
  - API hata mesajları kullanıcıya aktarılır
  - ScrollView ile klavye açıldığında içerik kaybolmaz

## 3. Shopper Profil Ekranı
- **API Endpoint:** `GET /api/shoppers/me`, `PUT /api/shoppers/me`, `PUT /api/users`
- **Görev:** Shopper kullanıcısının kişisel bilgilerini görüntüleme ve güncelleme ekranı
- **UI Bileşenleri:**
  - Kullanıcı adı ve e-posta bilgilerini gösteren lacivert bilgi kartı
  - İsim ve soyisim input alanları (mevcut değerle dolu)
  - "Kaydet" butonu
  - "Şifre Değiştir" butonu (modal açar)
  - Şifre değiştirme dialog'u (yeni şifre alanı)
  - Başarı mesajı bileşeni (yeşil arka plan)
  - "Çıkış Yap" butonu
  - Loading göstergesi
- **Kullanıcı Deneyimi:**
  - Ekran açıldığında mevcut profil bilgileri otomatik yüklenir (useFocusEffect)
  - Güncelleme başarılıysa yeşil başarı mesajı gösterilir
  - Şifre değiştirme için ayrı dialog, minimum 8 karakter kontrolü
  - Çıkış yapınca token silinir ve login ekranına yönlendirilir
- **Teknik Detaylar:**
  - useFocusEffect ile her sekmeye dönüşte profil yeniden yüklenir
  - Portal bileşeniyle modal yönetimi (React Native Paper)
  - Zustand logout() ile token temizleme ve yönlendirme

## 4. Admin — Admin Listesi Ekranı
- **API Endpoint:** `GET /api/admin/admins`
- **Görev:** Sistemdeki tüm admin kullanıcılarının listelendiği ekran
- **UI Bileşenleri:**
  - FlatList ile admin kartları listesi
  - Her kart: kullanıcı adı, e-posta, rol etiketi
  - Boş liste durumu mesajı
  - Loading göstergesi
  - Turuncu FAB butonu ("Satıcı Ekle")
  - Header'da çıkış (logout) ikonu
- **Kullanıcı Deneyimi:**
  - Karta tıklanınca kullanıcı detay ekranına gidilir
  - FAB ile satıcı oluşturma ekranına geçiş
  - useFocusEffect ile her geri dönüşte liste yenilenir

## 5. Admin — Kullanıcı Detay ve Silme Ekranı
- **API Endpoint:** `GET /api/admin/users/{id}`, `DELETE /api/admin/users/{id}`
- **Görev:** Belirli bir kullanıcının detay bilgilerini görüntüleme ve silme işlemi
- **UI Bileşenleri:**
  - Kullanıcı adı, e-posta, roller ve ID bilgileri kartı
  - "Kullanıcıyı Sil" butonu (kırmızı)
  - Onay dialog'u ("Bu işlem geri alınamaz." uyarısıyla)
  - Loading göstergesi
- **Kullanıcı Deneyimi:**
  - Sil butonuna basınca dialog açılır, İptal veya Sil seçenekleri sunulur
  - Başarılı silme sonrası önceki ekrana dönülür
  - Hata durumunda ekranda hata mesajı gösterilir
- **Teknik Detaylar:**
  - useLocalSearchParams ile URL'den ID alınır
  - Portal ile dialog yönetimi

## 6. Admin — Satıcı Oluşturma Ekranı
- **API Endpoint:** `POST /api/admin/sellers`
- **Görev:** Admin tarafından yeni satıcı hesabı oluşturma formu
- **UI Bileşenleri:**
  - Kullanıcı adı, e-posta, şifre input alanları
  - Şirket adı input alanı (opsiyonel)
  - "Satıcı Oluştur" butonu
  - Hata mesajı bileşeni
  - Loading indicator
- **Form Validasyonu:**
  - Kullanıcı adı, e-posta ve şifre zorunlu kontrolü
- **Kullanıcı Deneyimi:**
  - Başarılı oluşturma sonrası önceki ekrana dönülür
  - API 200 yanıtı döner (201 değil), uygulama bu durumu hata saymaz
  - Şifre alanında göster/gizle toggle

## 7. Admin — Satıcı Detay Ekranı
- **API Endpoint:** `GET /api/admin/sellers/{id}`
- **Görev:** Bir satıcının tüm bilgilerini ve ürün portföyünü görüntüleme
- **UI Bileşenleri:**
  - Şirket adı, kullanıcı adı, e-posta ve ID bilgileri kartı
  - Satıcıya ait ürün listesi (ürün adı + fiyat chip'i)
  - Ürün sayısı başlığı
  - "Kullanıcıyı Görüntüle / Sil" butonu (kullanıcı detay ekranına yönlendirir)
  - Loading göstergesi
- **Kullanıcı Deneyimi:**
  - Ürün listesi ve satıcı bilgileri tek API çağrısıyla (SellerDetailedResponse) yüklenir
  - Ürün yoksa "Bu satıcının ürünü yok." mesajı gösterilir
  - FlatList ile listeleme (büyük listelerde performanslı)
