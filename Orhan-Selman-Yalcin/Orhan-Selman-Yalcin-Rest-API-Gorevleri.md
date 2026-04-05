# Orhan Selman Yalçın REST API Görevleri

**API Test videosu:** [video](https://www.youtube.com/watch?v=IfLOAxR5ef8)

# 1. Satıcı Şirket Bilgilerini Güncelleme (Update - Satıcı Profil)
Endpoint: PUT /api/sellers

Request Body: 
```json
{
"companyName": "Yeni Şirket Adı Ltd. Şti."
}
```

Authentication: Bearer Token gerekli (Satıcı - Seller Yetkisi)

Response: 204 No Content - Satıcı şirket bilgisi başarıyla güncellendi.

# 2. Tüm Satıcıları Listeleme (Read - Admin Paneli)
Endpoint: GET /api/admin/sellers

Query Parameters:

username (string, optional) - Satıcıyı kullanıcı adına göre aramak için

email (string, optional) - Satıcıyı e-posta adresine göre aramak için

company_name (string, optional) - Satıcıyı şirket adına göre aramak için

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 200 OK - Sistemdeki satıcıların detaylı listesi başarıyla getirildi. (Dönüş Obj.: SellerDetailedResponse dizisi)

# 3. Yeni Ürün Ekleme (Create - Satıcı Ürün Yönetimi)
Endpoint: POST /api/items

Request Body: 
```json
{
"name": "HD 600",
"title": "Sennheiser HD 600 Açık Tasarım Kulaklık",
"brand": "Sennheiser",
"description": "Profesyonel stüdyo kullanımı için yüksek çözünürlüklü kulaklık."
}
```

Authentication: Bearer Token gerekli (Satıcı - Seller Yetkisi)

Response: 201 Created - Ürün başarıyla eklendi. Header kısmında Location olarak yeni ürünün URI adresi yer alır. (Dönüş Obj.: ItemResponse)

# 4. Ürün Detayı Getirme (Read)
Endpoint: GET /api/items/{id}

Path Parameters: - id (long, required) - Görüntülenecek ürünün ID'si

Authentication: Bearer Token gerekli (Satıcı - Seller Yetkisi)

Response: 200 OK - Ürün detayları başarıyla getirildi. (Dönüş Obj.: ItemResponse - id, title, price, description, seller, images)

# 5. Ürün Bilgilerini Güncelleme (Update - Satıcı Ürün Yönetimi)
Endpoint: PUT /api/items/{id}

Path Parameters: - id (long, required) - Güncellenecek ürünün ID'si

Request Body: 
```json
{
"name": "HD 600 v2",
"title": "Sennheiser HD 600 (Güncel Versiyon)",
"brand": "Sennheiser",
"description": "Güncellenmiş kutu içeriği ile stüdyo kulaklığı."
}
```

Authentication: Bearer Token gerekli (Sadece ürünün sahibi olan Satıcı değiştirebilir)

Response: 204 No Content - Ürün bilgileri başarıyla güncellendi.

# 6. Ürün Silme (Delete - Satıcı Ürün Yönetimi)
Endpoint: DELETE /api/items/{id}

Path Parameters: - id (long, required) - Silinecek ürünün ID'si

Authentication: Bearer Token gerekli (Sadece ürünün sahibi olan Satıcı silebilir)

Response: 204 No Content - Ürün sistemden başarıyla silindi.

# 7. Satıcıya Göre Ürünleri Listeleme (Read - Admin Paneli)
Endpoint: GET /api/admin/sellers/{sellerId}/items

Path Parameters: - sellerId (long, required) - Ürünleri getirilecek satıcının ID'si

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 200 OK - Belirtilen satıcının ürünleri başarıyla getirildi. (Dönüş Obj.: ItemSummaryResponse listesi - id, title, price, images)

# 8. Ürün Görseli Yönetimi (Create/Update - Resim Ekleme)
Endpoint: POST /api/items/{id}/images

Path Parameters: - id (long, required) - Görsel eklenecek ürünün ID'si

Request Body: 
```json
{
"smallImageUrl": "https://www.google.com/search?q=https://cdn.example.com/small_image.webp",
"imageUrl": "https://www.google.com/search?q=https://cdn.example.com/large_image.webp"
}
```

Authentication: Bearer Token gerekli (Sadece ürünün sahibi olan Satıcı ekleyebilir)

Response: 204 No Content - Görsel URL'leri ürüne başarıyla eklendi.