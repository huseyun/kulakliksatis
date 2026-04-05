# 1. Üye Olma (Kayıt) Sayfası
API Endpoint: POST /api/auth/register

Görev: Dışarıya açık yeni müşteri (shopper) hesabı oluşturma arayüzü tasarımı ve entegrasyonu.

UI Bileşenleri:

Register.tsx sayfası

Kullanıcı Adı, E-posta ve Şifre için input alanları

"Hesap Oluştur" butonu (primary style) ve loading state göstergesi

Giriş sayfasına (Login) yönlendiren link

Kullanıcı Deneyimi & Validasyon:

Tüm alanlar (required) doldurulmadan form gönderiminin engellenmesi

İşlem sırasında butonun disabled (tıklanamaz) hale gelip "Kaydediliyor..." metnine dönüşmesi

Başarılı kayıt sonrası react-hot-toast ile başarı mesajı ve /login sayfasına otomatik yönlendirme

# 2. Müşteri (Shopper) Profil Sayfası
API Endpoint: GET /api/shoppers/me ve PUT /api/shoppers/me

Görev: Müşterilerin kendi ad, soyad gibi kişisel bilgilerini görüntüleyip güncelleyebildiği sayfanın yapılması.

UI Bileşenleri:

ShopperProfile.tsx sayfası

Ad ve Soyad düzenlenebilir inputları

Salt okunur (disabled) Kullanıcı Adı ve E-posta alanları

"Değişiklikleri Kaydet" onay butonu

Kullanıcı Deneyimi & Validasyon:

Sayfa açılışında GET isteği atılarak mevcut verilerin inputlara otomatik doldurulması (Fetching state)

Güncelleme sırasında loading spinner/text bildirimi

Başarılı PUT isteği sonrası yeni verilerin çekilerek arayüzün senkronize edilmesi

# 3. Şifre Değiştirme Modülü
API Endpoint: PUT /api/users

Görev: Kullanıcıların hesap güvenlikleri için şifrelerini güncelleyebileceği ortak ve tekrar kullanılabilir (reusable) component oluşturulması.

UI Bileşenleri:

PasswordChangeForm.tsx bileşeni (Profil sayfalarına entegre)

Yeni şifre ve şifre tekrar (confirm) inputları

Şifre güncelleme butonu

Kullanıcı Deneyimi & Validasyon:

Frontend tarafında "Şifreler eşleşmiyor" validasyonu (Toast error ile)

İşlem başarıyla sonuçlandığında form inputlarının güvenlik amacıyla otomatik olarak temizlenmesi

# 4. Admin Paneli: Kullanıcı Yönetimi ve Listeleme
API Endpoint: GET /api/admin/users, GET /api/admin/admins, GET /api/admin/shoppers

Görev: Sistemdeki üyelerin rol bazlı izolasyon ile listelendiği gelişmiş tablo arayüzü.

UI Bileşenleri:

AdminUsers.tsx sayfası

Kategori geçişi sağlayan Tab (Sekme) butonları (Tüm Kullanıcılar, Yönetici Seçkisi, Müşteriler vb.)

ID, Kullanıcı Adı, Rol ve İşlem sütunlarını barındıran dinamik veri tablosu

Rolleri belirten renkli badge (etiket) yapıları (Admin: Kırmızı, vb.)

Kullanıcı Deneyimi & Validasyon:

Veriler yüklenirken skeleton/pulse loading animasyonu

İlgili sekmede kimse yoksa boş (empty) state ekranı gösterimi

# 5. Admin Paneli: Kullanıcı Silme Akışı
API Endpoint: DELETE /api/admin/users/{id}

Görev: Yöneticinin platformdan tehlikeli yetkilerle kullanıcı kaldırmasını sağlayan silme mekanizması.

UI Bileşenleri:

Tablo satırlarında yer alan Trash2 (Kırmızı Çöp Kutusu) silme butonu

Kullanıcı Deneyimi & Validasyon:

Yanlışlıkla silmeleri önlemek için native window.confirm onay diyaloğu ("Emin misiniz?")

Silme işlemi tamamlandığında, sayfa yenilenmeden listenin fetchUsers tetiklenerek güncellenmesi

# 6. Admin Paneli: Yeni Yetkili ve Satıcı Oluşturma
API Endpoint: POST /api/admin/admins ve POST /api/admin/sellers

Görev: Admin tarafından sisteme özel yetkilerle yeni bir Yönetici veya Şirket (Satıcı) hesabı eklenmesi.

UI Bileşenleri:

"Satıcı Ekle" ve "Admin Ekle" aksiyon butonları

showAdminModal ve showSellerModal state'lerine bağlı açılır (popup) formlar

Şirket adı (sadece satıcılar için), email, username ve şifre form inputları

Kullanıcı Deneyimi & Validasyon:

Modal kapatıldığında veya işlem tamamlandığında state'lerin sıfırlanması (resetForm)

Başarılı POST işlemi sonrası ekran arkasındaki tablonun otomatik güncellenmesi