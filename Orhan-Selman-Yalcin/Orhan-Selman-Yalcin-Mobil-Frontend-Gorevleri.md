# Orhan Selman Yalçın'nın Mobil Frontend Görevleri
**Mobile Front-end Demo Videosu:** [Link buraya eklenecek](https://example.com)

## 1. Admin — Satıcı Listesi Ekranı
- **API Endpoint:** `GET /api/admin/sellers`
- **Görev:** Sistemdeki tüm satıcıların listelendiği admin ekranı tasarımı ve implementasyonu
- **UI Bileşenleri:**
  - FlatList ile satıcı kartları listesi
  - Her kart: şirket adı (yoksa kullanıcı adı), kullanıcı adı ve e-posta
  - Boş liste durumu mesajı ("Satıcı bulunamadı.")
  - Loading göstergesi
  - Turuncu FAB butonu ("Satıcı Ekle")
- **Kullanıcı Deneyimi:**
  - Karta tıklanınca satıcı detay ekranına gidilir
  - useFocusEffect ile her geri dönüşte liste yenilenir
  - FAB ile satıcı oluşturma ekranına geçiş

## 2. Satıcı Profil Ekranı
- **API Endpoint:** `PUT /api/sellers`, `PUT /api/users`
- **Görev:** Satıcı kullanıcısının şirket bilgilerini güncelleme ve şifre değiştirme ekranı
- **UI Bileşenleri:**
  - Kullanıcı adı ve "Satıcı Hesabı" etiketini gösteren lacivert bilgi kartı
  - Şirket adı input alanı (mevcut değerle dolu)
  - "Kaydet" butonu
  - "Şifre Değiştir" butonu (modal açar)
  - Şifre değiştirme dialog'u (yeni şifre alanı)
  - Başarı mesajı bileşeni (yeşil arka plan)
  - "Çıkış Yap" butonu
- **Kullanıcı Deneyimi:**
  - Güncelleme başarılıysa yeşil başarı mesajı gösterilir
  - Şifre değiştirme için ayrı dialog, minimum 8 karakter kontrolü
  - Çıkış yapınca token silinir ve login ekranına yönlendirilir
- **Teknik Detaylar:**
  - `PUT /api/users` şifre servisi Shopper profiliyle ortak kullanılır (DRY)
  - Portal bileşeniyle modal yönetimi (React Native Paper)

## 3. Satıcı — Ürünlerim Ekranı
- **API Endpoint:** `GET /api/sellers/me/items`
- **Görev:** Satıcıya ait ürün portföyünün listelendiği ana ekran
- **UI Bileşenleri:**
  - FlatList ile ürün kartları (görsel, başlık, fiyat)
  - Boş liste durumu mesajı ("Henüz ürün eklemediniz.")
  - Loading göstergesi
  - Turuncu FAB butonu ("Ürün Ekle")
- **Kullanıcı Deneyimi:**
  - Karta tıklanınca ürün detay ekranına gidilir
  - useFocusEffect ile ürün ekleyip geri dönüldüğünde liste otomatik yenilenir
  - FAB ile ürün oluşturma ekranına geçiş

## 4. Ürün Ekleme Ekranı
- **API Endpoint:** `POST /api/items`
- **Görev:** Satıcının yeni ürün oluşturabileceği form ekranı
- **UI Bileşenleri:**
  - Ürün ismi, başlık, marka ve açıklama input alanları
  - "Ürün Oluştur" butonu
  - API bilgi notu (fiyat alanının henüz desteklenmediğini belirtir)
  - Hata mesajı bileşeni
  - Loading indicator
- **Form Validasyonu:**
  - İsim, başlık ve marka zorunlu alan kontrolü
- **Kullanıcı Deneyimi:**
  - Başarılı oluşturma sonrası ürün listesine dönülür
  - ScrollView ile klavye açıldığında form kaybolmaz

## 5. Ürün Detay, Düzenleme ve Silme Ekranı
- **API Endpoint:** `GET /api/items/{id}`, `PUT /api/items/{id}`, `DELETE /api/items/{id}`
- **Görev:** Tek ürünün detay görünümü, düzenleme formu ve silme akışı
- **UI Bileşenleri:**
  - Ürün görseli (varsa) veya görsel placeholder
  - "Görsel Yönet" butonu (görsel yükleme ekranına gider)
  - Ürün başlığı, fiyatı (varsa) ve açıklaması
  - "Düzenle" butonu → inline form açılır
  - Düzenleme formunda: isim, başlık, marka, açıklama alanları
  - "Kaydet" ve "İptal" butonları
  - "Ürünü Sil" butonu (kırmızı)
  - Silme onay dialog'u ("Bu işlem geri alınamaz.")
  - Başarı mesajı bileşeni
- **Kullanıcı Deneyimi:**
  - Düzenle butonuna basınca form alanları görünür, detay görünümü gizlenir
  - Kaydetme başarılıysa form kapanır, güncel veriler yeniden yüklenir
  - Silme dialog'unda İptal ve Sil seçenekleri sunulur
  - Başarılı silme sonrası ürün listesine dönülür
- **Teknik Detaylar:**
  - editMode boolean state ile detay/form görünümü değiştirilir
  - useLocalSearchParams ile URL'den ürün ID'si alınır

## 6. Ürün Görsel Yükleme Ekranı
- **API Endpoint:** `POST /api/items/{id}/images`
- **Görev:** Satıcının ürününe galeri fotoğraflarını yükleyebildiği ekran
- **UI Bileşenleri:**
  - "Fotoğraf Seç" butonu (galeriyi açar)
  - Seçilen görsellerin 3'lü ızgara önizlemesi
  - Her görsel için kaldırma (X) butonu
  - "N Görseli Yükle" butonu (seçim yapılınca görünür)
  - Başarı mesajı bileşeni
  - Hata mesajı bileşeni
- **Kullanıcı Deneyimi:**
  - Çoklu fotoğraf seçimi desteklenir
  - Seçim sonrası önizleme anında gösterilir
  - X'e basınca görsel seçim listesinden kaldırılır
  - Upload sonrası seçim listesi temizlenir
- **Teknik Detaylar:**
  - expo-image-picker ile galeri erişimi
  - FormData ile multipart/form-data upload
  - `files` field adıyla API'ye gönderilir

## 7. Shopper — Önerilen Ürünler (Ana Sayfa) Ekranı
- **API Endpoint:** `GET /api/items/recommended`
- **Görev:** Shopper kullanıcısının gördüğü keşfet/ana sayfa ekranı
- **UI Bileşenleri:**
  - "🎧 Önerilen Ürünler" başlığı
  - FlatList ile ürün kartları (görsel, başlık, fiyat)
  - Boş liste durumu mesajı
  - Loading göstergesi
- **Kullanıcı Deneyimi:**
  - Public endpoint, token olmasa da çalışır
  - Karta tıklanınca ürün detay ekranına gidilir
  - useFocusEffect ile her geri dönüşte liste yenilenir

## 8. Shopper — Ürün Detay Ekranı
- **API Endpoint:** `GET /api/items/{id}`
- **Görev:** Shopper'ın ürün detaylarını görüntüleyebildiği salt okunur ekran
- **UI Bileşenleri:**
  - Ürün görseli (tam genişlikte, 260px yükseklikte)
  - Ürün başlığı ve fiyatı
  - Açıklama metni
  - Satıcı bilgi kartı (şirket adı veya kullanıcı adı)
  - AutoEQ destekli ürünlerde "AutoEQ Destekli" chip etiketi
- **Kullanıcı Deneyimi:**
  - Görsel yoksa placeholder gösterilir
  - ScrollView ile uzun içerik kaydırılabilir
