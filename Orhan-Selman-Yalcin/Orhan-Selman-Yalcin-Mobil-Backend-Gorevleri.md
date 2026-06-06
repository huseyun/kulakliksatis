# Orhan Selman Yalçın'nın Mobil Backend Görevleri
**Mobil Front-end ile Back-end Bağlanmış Test Videosu:** [Link buraya eklenecek](https://example.com)

## 1. Tüm Satıcıları Listeleme Servisi
- **API Endpoint:** `GET /api/admin/sellers`
- **Görev:** Sistemdeki tüm satıcıların listesini API'den çekerek admin ekranında görüntüleme
- **İşlevler:**
  - Admin yetkisiyle kimlik doğrulama
  - Satıcı listesini (id, username, email, companyName) getirme
  - Gelen listeyi UI bileşenlerine aktarma
  - Hata durumunda kullanıcıya mesaj gösterme
- **Teknik Detaylar:**
  - `GET /api/admin/sellers` → `SellerResponse[]` (`src/api/sellers.ts`)
  - Query param gönderilmeden çağrılır; param gönderilirse API tek obje döner (backend davranışı)
  - Axios request interceptor ile Authorization header otomatik eklenir
  - useFocusEffect ile ekran her odaklandığında liste yenilenir

## 2. Satıcı Şirket Bilgilerini Güncelleme Servisi
- **API Endpoint:** `PUT /api/sellers`
- **Görev:** Giriş yapmış satıcının şirket adı bilgisini güncelleme
- **İşlevler:**
  - Şirket adını alarak API'ye PUT isteği gönderme
  - Başarılı güncelleme sonrası kullanıcıya bildirim gösterme (204 No Content)
  - Hata durumlarında form üzerinde mesaj gösterme
- **Teknik Detaylar:**
  - Request body: `{ companyName }` (`src/api/sellers.ts` → `updateSeller()`)
  - API → 204 No Content başarılı yanıt
  - Şifre değiştirme fonksiyonu (`PUT /api/users`) Shopper profiliyle ortak `src/api/password.ts` dosyasından kullanılır

## 3. Yeni Ürün Ekleme Servisi
- **API Endpoint:** `POST /api/items`
- **Görev:** Satıcının kendi hesabına yeni bir ürün eklemesini sağlayan servis entegrasyonu
- **İşlevler:**
  - İsim, başlık, marka ve açıklama bilgilerini alarak API'ye POST isteği gönderme
  - Başarılı yanıtta (201 Created) ürün listesine dönme
  - Hata durumlarında form üzerinde hata mesajı gösterme
- **Teknik Detaylar:**
  - Request body: `{ name, title, brand, description? }` (`src/api/items.ts` → `createItem()`)
  - `price` alanı API'de mevcut değil (backend eksikliği) — form'a dahil edilmez
  - Satıcı kimliği JWT token üzerinden backend tarafında belirlenir, client göndermez

## 4. Ürün Detayı Getirme Servisi
- **API Endpoint:** `GET /api/items/{id}`
- **Görev:** Belirli bir ürünün tüm detaylarını API'den çekerek görüntüleme
- **İşlevler:**
  - Ürün ID'siyle detaylı bilgi (title, price, description, seller, images, autoeqId) getirme
  - Gelen görsel anahtarlarını (S3 key) tam URL'ye dönüştürme
  - Satıcı bilgisini ve ürün açıklamasını UI'da gösterme
  - Hata durumunda mesaj gösterme (404 Ürün bulunamadı vb.)
- **Teknik Detaylar:**
  - `GET /api/items/{id}` → `ItemResponse` (`src/api/items.ts` → `getItem()`)
  - API görsel URL değil S3 key döner → `buildImageUrl(key)` ile tam URL oluşturulur (`src/utils/image.ts`)
  - `images` array'inden `isThumbnail: true` olan görsel öncelikli gösterilir

## 5. Ürün Güncelleme Servisi
- **API Endpoint:** `PUT /api/items/{id}`
- **Görev:** Mevcut bir ürünün bilgilerini güncelleme işlemini gerçekleştirme
- **İşlevler:**
  - Düzenleme formundan gelen isim, başlık, marka ve açıklamayı API'ye PUT isteğiyle gönderme
  - Başarılı güncelleme sonrası başarı mesajı gösterme ve ürün verilerini yeniden yükleme
  - Hata durumunda form üzerinde hata mesajı gösterme
- **Teknik Detaylar:**
  - Request body: `{ name, title, brand, description? }` (`src/api/items.ts` → `updateItem()`)
  - API → 204 No Content başarılı yanıt
  - Sahiplik kontrolü backend tarafından yapılır; farklı satıcının ürününe erişilirse hata döner

## 6. Ürün Silme Servisi
- **API Endpoint:** `DELETE /api/items/{id}`
- **Görev:** Satıcının kendi ürününü sistemden kalıcı olarak silme işlemi
- **İşlevler:**
  - Kullanıcıya onay dialog'u gösterme
  - API'ye DELETE isteği gönderme
  - Başarılı silme sonrası ürün listesine geri dönme
  - Hata durumunda ekranda hata mesajı gösterme
- **Teknik Detaylar:**
  - `DELETE /api/items/{id}` → 204 No Content (`src/api/items.ts` → `deleteItem()`)
  - Silme işlemi geri alınamaz, destructive action dialog ile onaylanır
  - Başarılı silme sonrası `router.back()` ile liste ekranına dönülür

## 7. Satıcıya Göre Ürünleri Listeleme Servisi
- **API Endpoint:** `GET /api/admin/sellers/{id}/items`
- **Görev:** Admin ekranında belirli bir satıcının ürün portföyünü listeleme
- **İşlevler:**
  - Satıcı ID'siyle o satıcıya ait tüm ürünleri getirme
  - Ürün listesini (id, title, price, thumbnailImageUrl) admin satıcı detay ekranında gösterme
  - Hata durumunda mesaj gösterme
- **Teknik Detaylar:**
  - Admin satıcı detay ekranında `GET /api/admin/sellers/{id}` yanıtından `items` alanı zaten gelir
  - `SellerDetailedResponse.items: ItemSummaryResponse[]` olarak parse edilir (`src/api/sellers.ts`)
  - Ürün listesi FlatList ile gösterilir, ayrı endpoint çağrısı gerekmez

## 8. Ürün Görsel Yükleme Servisi
- **API Endpoint:** `POST /api/items/{id}/images`
- **Görev:** Satıcının ürününe galeri fotoğraflarını multipart upload ile yükleme
- **İşlevler:**
  - expo-image-picker ile galeriden çoklu fotoğraf seçimi
  - Seçilen dosyaları FormData formatında paketleme
  - API'ye multipart/form-data POST isteği gönderme
  - Başarılı yükleme sonrası seçim listesini temizleme ve başarı mesajı gösterme
  - Hata durumunda mesaj gösterme (storage servisi erişilemiyorsa 500)
- **Teknik Detaylar:**
  - `POST /api/items/{id}/images` → `multipart/form-data`, field adı: `files` (`src/api/items.ts` → `uploadItemImages()`)
  - `isThumbnail` query param opsiyonel; gönderilmezse backend varsayılan davranışla işler
  - Dosya URI, name ve mimeType bilgisi FormData'ya eklenir
  - Content-Type header `multipart/form-data` olarak ayarlanır
