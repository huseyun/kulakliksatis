# Orhan Selman Yalçın Front-end Videoları

**API Test videosu:** [video](https://youtu.be/tSqcL06Nj2E?si=zB9Da8ZOwTfhDZdg)

# 1. Satıcı Şirket Profili Yönetimi
API Endpoint: PUT /api/sellers

Görev: Sisteme kayıtlı satıcının (seller) şirket adını (companyName) güncelleyebildiği sayfanın arayüz tasarımı.

UI Bileşenleri:

SellerProfile.tsx sayfası

Şirket Adı (Company Name) input alanı

"Güncelle" onay butonu ve ikonlu başlık tasarımı (Store icon)

Kullanıcı Deneyimi & Validasyon:

Güncelleme işlemi sırasında butonun inaktif (disabled) duruma geçip loading animasyonu göstermesi

API isteği başarılı olduğunda react-hot-toast ile ekranda yeşil bildirim (success message) gösterilmesi

# 2. Satıcı Ürün (İlan) Yönetimi: Listeleme ve Silme
API Endpoint: GET /api/sellers/me/items ve DELETE /api/items/{id}

Görev: Satıcının kendine ait ürünleri bir tablo halinde görebildiği ve sildiği ana yönetim ekranı.

UI Bileşenleri:

SellerItems.tsx sayfası

Ürün Görseli, Başlık (Title), Fiyat ve İşlemler (Düzenle/Sil) kolonlarından oluşan veri tablosu

"Yeni Ürün Ekle" ana aksiyon butonu

Kullanıcı Deneyimi & Validasyon:

Ürün görseli yoksa (veya kırık linkse) yerine gri bir ImageIcon (placeholder) gösterilmesi

Silme işlemi (Trash2 butonu) tetiklendiğinde window.confirm ile kazara silmeleri önleyen güvenlik sorusu

Ürün silindiğinde sayfanın yenilenmesine gerek kalmadan listenin güncellenmesi

# 3. Satıcı Ürün Yönetimi: Ekleme ve Güncelleme (Modal Akışı)
API Endpoint: POST /api/items, PUT /api/items/{id}, ve POST /api/items/{id}/images

Görev: Yeni ürün eklemek veya var olan bir ürünü düzenlemek için dinamik çalışan popup (modal) form sistemi.

UI Bileşenleri:

SellerItems.tsx içerisindeki açılır form modalı

Name, Brand, Title, Description için text/textarea inputları

Görsel entegrasyonu için "Küçük Görsel URL" ve "Büyük Görsel URL" inputları

Kullanıcı Deneyimi & Validasyon:

Tek bir Modal component'inin create ve edit (ekle/düzenle) statelerine göre dinamik olarak başlık ve buton ismi değiştirmesi

"Düzenle" butonuna basıldığında GET /api/items/{id} ucuna istek atılarak mevcut ürün bilgilerinin form inputlarına otomatik doldurulması (Pre-population)

Ürün oluşturulduktan hemen sonra eğer görsel URL'si girilmişse, otomatik olarak ikinci bir API çağrısı (/images) yapılarak resimlerin ürüne bağlanması

# 4. Admin Paneli: Satıcı Listeleme Entegrasyonu
API Endpoint: GET /api/admin/sellers

Görev: Hüseyin tarafından iskeleti oluşturulan Admin panelinde, satıcılara özel verilerin gösterildiği kısmın frontend entegrasyonu.

UI Bileşenleri:

AdminUsers.tsx sayfasındaki "Satıcı Konsolu" sekmesi (Tab butonu)

Tabloya sadece bu sekmeye özel olarak eklenen "Şirket Adı" (Company Name) sütunu

Kullanıcı Deneyimi & Validasyon:

Sekmeler arası geçişte state değişimi ile yalnızca satıcıların API'dan çekilmesi

Şirket adlarının arayüzde diğer tablolardan ayrışacak şekilde vurgulu (renkli) gösterimi

# 5. Admin Paneli: Satıcı Vitrinini İnceleme Modalı
API Endpoint: GET /api/admin/sellers/{id}/items

Görev: Adminin, bir satıcının yüklediği tüm ürünleri tek bir tıkla, sayfa değiştirmeden denetleyebileceği vitrin modülü.

UI Bileşenleri:

Tabloda yer alan "Mağaza Vitrinini Gör" (Package icon) butonu

Satıcının ilanlarını Grid (ızgara) yapısında listeleyen showItemsModal pop-up'ı

Kullanıcı Deneyimi & Validasyon:

Modala tıklandığında ürünlerin yüklenme durumunu belirten text animasyonu (itemsLoading state)

Satıcının hiç ürünü yoksa "Bu satıcının vitrininde ürün bulunamadı" şeklinde boş durum (empty state) uyarısı

Ürün başlıklarının çok uzun olması durumunda arayüzün bozulmasını engelleyen truncate (üç nokta ile kesme) stili