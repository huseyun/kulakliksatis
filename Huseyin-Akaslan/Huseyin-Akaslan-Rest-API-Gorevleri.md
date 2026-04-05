# Hüseyin Akaslan REST API videolar

**API Test videosu:** [video](https://www.youtube.com/watch?v=M7SUWZCo5a8)

# 1. Sisteme Yeni Shopper Kaydı (Create - Site Kayıt)
Endpoint: POST /api/auth/register

Request Body: 
```json
{
"username": "kullanici_adi",
"password": "Guvenli123!",
"email": "kullanici@example.com"
}
```

Response: 201 Created - Kullanıcı başarıyla oluşturuldu. (Dönüş Obj.: ShopperResponse - id, username, email, firstName, lastName)

# 2. Kullanıcı Detaylarını Getirme (Read - Admin Paneli)
Endpoint: GET /api/admin/users/{id}

Path Parameters: - id (long, required) - Kullanıcı ID'si

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 200 OK - Kullanıcı bilgileri başarıyla getirildi. (Dönüş Obj.: UserResponse - id, username, email, userType listesi)

# 3. Shopper Bilgilerini Güncelleme (Update - Profil)
Endpoint: PUT /api/shoppers/me

Request Body: 
```json
{
"firstName": "Hüseyin",
"lastName": "Akaslan"
}
```

Authentication: Bearer Token gerekli (Shopper Yetkisi)

Response: 204 No Content - Müşteri bilgileri başarıyla güncellendi.

# 4. Kullanıcı Silme (Delete - Admin Paneli)
Endpoint: DELETE /api/admin/users/{id}

Path Parameters: - id (long, required) - Silinecek Kullanıcı ID'si

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 204 No Content - Kullanıcı başarıyla silindi.

# 5. Şifre Değiştirme (Update - Genel Güvenlik)
Endpoint: PUT /api/users

Request Body:
```json
{
"password": "YeniGuvenliSifre456!"
}
```

Authentication: Bearer Token gerekli (Kimliği doğrulanmış tüm kullanıcılar)

Response: 204 No Content - Şifre başarıyla güncellendi.

# 6. Admin Listeleme (Read)
Endpoint: GET /api/admin/admins

Query Parameters:

username (string, optional) - Spesifik bir admini kullanıcı adına göre aramak için

email (string, optional) - Spesifik bir admini e-posta adresine göre aramak için

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 200 OK - Admin listesi başarıyla getirildi. (Dönüş Obj.: AdminResponse dizisi veya tekil nesne)

# 7. Yeni Satıcı Oluşturma (Create - Admin Paneli)
Endpoint: POST /api/admin/sellers

Request Body: 
```json
{
"username": "satici_kullanici",
"password": "SaticiPassword123",
"email": "satici@firma.com",
"companyName": "Ödemiş Ltd. Şti."
}
```

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 200 OK - Satıcı başarıyla sisteme eklendi.

# 8. Satıcı Detaylarını Görüntüleme (Read - Admin Paneli)
Endpoint: GET /api/admin/sellers/{id}

Path Parameters: - id (long, required) - Satıcı ID'si

Authentication: Bearer Token gerekli (Admin Yetkisi)

Response: 200 OK - Satıcı detayları başarıyla getirildi. (Dönüş Obj.: SellerDetailedResponse - id, username, email, companyName ve satıcıya ait ItemSummaryResponse ürün listesi)