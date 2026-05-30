# Hüseyin Akaslan'ın Mobil Backend Görevleri
**Mobil Front-end ile Back-end Bağlanmış Test Videosu:** [Link buraya eklenecek](https://example.com)

## 1. Giriş (Login) Servisi
- **API Endpoint:** `POST /api/auth/login`
- **Görev:** Kullanıcı giriş işlemini gerçekleştiren servis entegrasyonu
- **İşlevler:**
  - Kullanıcı adı ve şifre bilgilerini alarak API'ye POST isteği gönderme
  - Başarılı yanıtta JWT token parse edilip güvenli depolamaya (expo-secure-store) kaydedilmesi
  - JWT payload decode edilerek kullanıcı rolünün (ADMIN / SELLER / SHOPPER) belirlenmesi
  - Role göre ilgili navigasyon stack'ine yönlendirme
  - Token süresi (24 saat) dolmuşsa otomatik login ekranına yönlendirme
  - Hata durumlarında (401 vb.) kullanıcı dostu mesaj gösterilmesi
- **Teknik Detaylar:**
  - HTTP Client: Axios (`src/api/client.ts`)
  - Axios request interceptor ile her istekte `Authorization: Bearer <token>` header'ı otomatik eklenir
  - Axios response interceptor ile API'den gelen HTML hata yanıtları (401/403 Spring default) JSON'a dönüştürülür
  - JWT decode için jwt-decode kütüphanesi (`src/utils/jwt.ts`)
  - Token yönetimi için Zustand store (`src/store/authStore.ts`)

## 2. Üye Olma (Kayıt) Servisi
- **API Endpoint:** `POST /api/auth/register`
- **Görev:** Mobil uygulamada shopper kullanıcısı kayıt işlemini gerçekleştiren servis entegrasyonu
- **İşlevler:**
  - Kullanıcı adı, e-posta ve şifre bilgilerini toplayarak API'ye POST isteği gönderme
  - Başarılı kayıt sonrası kullanıcıyı giriş ekranına yönlendirme
  - Hata durumlarını yakalayarak kullanıcıya gösterme (validasyon hataları, çakışma vb.)
- **Teknik Detaylar:**
  - Request body: `{ username, email, password }` (`src/api/auth.ts`)
  - API hata yanıtı `ErrorResponse` formatında: `{ statusCode, errorCode, message, validationErrors }`
  - Loading state yönetimi

## 3. Shopper Profil Görüntüleme ve Güncelleme Servisi
- **API Endpoint:** `GET /api/shoppers/me`, `PUT /api/shoppers/me`
- **Görev:** Giriş yapmış shopper kullanıcısının profil bilgilerini çekme ve güncelleme
- **İşlevler:**
  - JWT token üzerinden kimlik doğrulama ile mevcut kullanıcı bilgilerini getirme
  - Gelen veriyi (id, username, email, firstName, lastName) UI'da görüntüleme
  - Kullanıcının düzenlediği isim/soyisim bilgilerini API'ye PUT isteğiyle gönderme
  - Başarılı güncelleme sonrası kullanıcıya bildirim gösterme
- **Teknik Detaylar:**
  - `GET /api/shoppers/me` → `ShopperResponse` (`src/api/shoppers.ts`)
  - `PUT /api/shoppers/me` → Request body: `{ firstName, lastName }` → 204 No Content
  - useFocusEffect ile ekran her odaklandığında profil yeniden yüklenir

## 4. Şifre Değiştirme Servisi
- **API Endpoint:** `PUT /api/users`
- **Görev:** Giriş yapmış herhangi bir kullanıcının şifresini güvenli şekilde güncelleme
- **İşlevler:**
  - Yeni şifreyi alarak API'ye PUT isteği gönderme
  - Başarılı güncelleme sonrası kullanıcıya bildirim (204 No Content yanıtı)
  - Minimum uzunluk kontrolü (8 karakter) mobil tarafta yapılır
  - Hata durumlarını yakalayarak dialog üzerinde gösterme
- **Teknik Detaylar:**
  - Request body: `{ password }` (`src/api/password.ts`)
  - Her rol tipi bu endpoint'i kullanabilir (ADMIN, SELLER, SHOPPER) — ortak servis dosyası
  - API → 204 No Content başarılı yanıt

## 5. Admin Listesi Servisi
- **API Endpoint:** `GET /api/admin/admins`
- **Görev:** Sistemdeki tüm admin kullanıcılarının listesini API'den çekerek görüntüleme
- **İşlevler:**
  - Admin yetkisiyle kimlik doğrulama
  - Admin kullanıcılarının listesini (id, username, email) getirme
  - Gelen listeyi UI bileşenlerine aktarma
  - Hata durumunda kullanıcıya mesaj gösterme
- **Teknik Detaylar:**
  - `GET /api/admin/admins` → `AdminResponse[]` (`src/api/admins.ts`)
  - Endpoint yalnızca `/api/admin/**` URL pattern'i ile korunur (ADMIN role)
  - Axios request interceptor token header'ı otomatik ekler

## 6. Kullanıcı Detay Görüntüleme ve Silme Servisi
- **API Endpoint:** `GET /api/admin/users/{id}`, `DELETE /api/admin/users/{id}`
- **Görev:** Belirli bir kullanıcının detay bilgilerini çekme ve hesabı silme
- **İşlevler:**
  - Kullanıcı ID'si ile profil bilgilerini (username, email, userType) getirme
  - Kullanıcıyı sistemden kalıcı olarak silme
  - Başarılı silme sonrası önceki ekrana dönme
  - Hata durumlarını yakalama (404 kullanıcı bulunamadı vb.)
- **Teknik Detaylar:**
  - `GET /api/admin/users/{id}` → `UserResponse` (`src/api/users.ts`)
  - `DELETE /api/admin/users/{id}` → 204 No Content
  - `userType` alanı `Set<UserTypeResponse>` olarak gelir, arayüzde join ile gösterilir

## 7. Satıcı Oluşturma Servisi
- **API Endpoint:** `POST /api/admin/sellers`
- **Görev:** Admin tarafından yeni satıcı hesabı oluşturma işlemi
- **İşlevler:**
  - Kullanıcı adı, e-posta, şifre ve opsiyonel şirket adını alarak API'ye POST isteği gönderme
  - Başarılı yanıtta önceki ekrana dönme
  - Hata durumlarında form üzerinde hata mesajı gösterme
- **Teknik Detaylar:**
  - Request body: `{ username, email, password, companyName? }` (`src/api/sellers.ts`)
  - API 200 döner (201 değil — backend bug); bu durum hata olarak değerlendirilmez
  - Şirket adı opsiyonel, boş bırakılırsa request'e dahil edilmez

## 8. Satıcı Detay Görüntüleme Servisi
- **API Endpoint:** `GET /api/admin/sellers/{id}`
- **Görev:** Bir satıcının şirket bilgileri ve ürün portföyünü tek API çağrısıyla getirme
- **İşlevler:**
  - Satıcı ID'siyle detaylı bilgi (username, email, companyName, items) getirme
  - Ürün listesini (id, title, price, isRecommended, thumbnailImageUrl) UI'da listeleme
  - Hata durumunda mesaj gösterme
- **Teknik Detaylar:**
  - `GET /api/admin/sellers/{id}` → `SellerDetailedResponse` (`src/api/sellers.ts`)
  - Response içinde `items: ItemSummaryResponse[]` alanı bulunur, ayrı endpoint gerektirmez
  - FlatList ile performanslı listeleme
