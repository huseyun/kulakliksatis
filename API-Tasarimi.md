OpenAPI Spesifikasyon Dosyası: [ardaguler.yaml](ardaguler.yaml)

Bu doküman, OpenAPI Specification (OAS) 3.0 standardına göre hazırlanmış Kulaklık E-Ticaret API tasarımını içermektedir.

## OpenAPI Specification

```yaml
openapi: 3.0.3
info:
  title: Kulaklık E-Ticaret API
  description: |
    Kulaklık satışına odaklı e-ticaret platformu için RESTful API tasarımı.
    
    ## Özellikler
    - Müşteri (Shopper), Satıcı (Seller) ve Admin yönetimi
    - Satıcı ve mağaza yönetimi
    - Kulaklık/Ürün katalog ve görsel yönetimi
    - JWT tabanlı kimlik doğrulama
  version: 1.0.0
  contact:
    name: API Destek Ekibi
    email: api-support@yazmuh.com
    url: https://api.yazmuh.com/support

servers:
  - url: http://localhost:8080/api/v1
    description: Development server (Spring Boot)
  - url: https://api.ecommerce.com/v1
    description: Production server

tags:
  - name: auth
    description: Kimlik doğrulama ve kayıt işlemleri
  - name: users
    description: Kullanıcı (Shopper) ve Admin işlemleri
  - name: sellers
    description: Satıcı ve mağaza yönetimi işlemleri
  - name: products
    description: Ürün (Kulaklık) ve görsel işlemleri

paths:
  # 1. Sisteme Yeni Shopper Kaydı (Create)
  /auth/register:
    post:
      tags:
        - auth
      summary: Yeni shopper (müşteri) kaydı
      description: Dışarıya açık endpoint ile sisteme yeni bir müşteri hesabı oluşturur.
      operationId: registerShopper
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/UserRegistration'
      responses:
        '201':
          description: Kullanıcı başarıyla oluşturuldu
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
        '400':
          $ref: '#/components/responses/BadRequest'

  # 6. Admin Listeleme (Read)
  /users/admins:
    get:
      tags:
        - users
      summary: Admin listesi
      description: Sistemdeki tüm yöneticileri (adminleri) listeler.
      operationId: listAdmins
      security:
        - bearerAuth: []
      responses:
        '200':
          description: Başarılı
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/User'
        '401':
          $ref: '#/components/responses/Unauthorized'

  /users/{userId}:
    # 2. Kullanıcı Detaylarını Getirme (Read)
    get:
      tags:
        - users
      summary: Kullanıcı detayı
      description: Belirli bir kullanıcının ID üzerinden tüm bilgilerinin (roller dahil) getirilmesi.
      operationId: getUserById
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/UserIdParam'
      responses:
        '200':
          description: Başarılı
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
        '404':
          $ref: '#/components/responses/NotFound'
    
    # 3. Shopper Bilgilerini Güncelleme (Update)
    put:
      tags:
        - users
      summary: Kullanıcı bilgilerini güncelle
      description: Müşterinin isim, soyisim ve e-posta gibi kişisel verilerinin güncellenmesi.
      operationId: updateUser
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/UserIdParam'
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/UserUpdate'
      responses:
        '200':
          description: Başarıyla güncellendi
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
    
    # 4. Kullanıcı Silme (Delete)
    delete:
      tags:
        - users
      summary: Kullanıcı sil
      description: Admin tarafından bir kullanıcının sistemden tamamen kaldırılması.
      operationId: deleteUser
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/UserIdParam'
      responses:
        '204':
          description: Başarıyla silindi
        '403':
          $ref: '#/components/responses/Forbidden'

  # 5. Şifre Değiştirme (Update)
  /users/{userId}/password:
    patch:
      tags:
        - users
      summary: Şifre güncelleme
      description: Güvenlik için mevcut kullanıcının sadece şifresini güncelleyebileceği endpoint.
      operationId: updatePassword
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/UserIdParam'
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PasswordUpdate'
      responses:
        '200':
          description: Şifre başarıyla değiştirildi

  /sellers:
    # 10. Tüm Satıcıları Listeleme (Read)
    get:
      tags:
        - sellers
      summary: Satıcı listesi
      description: Sistemdeki aktif tüm satıcıların özet bilgilerinin listelenmesi.
      operationId: listSellers
      responses:
        '200':
          description: Başarılı
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Seller'
    
    # 7. Yeni Satıcı Oluşturma (Create)
    post:
      tags:
        - sellers
      summary: Yeni satıcı ekle
      description: Admin tarafından sisteme yeni bir mağaza/satıcı eklenmesi.
      operationId: createSeller
      security:
        - bearerAuth: []
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/SellerCreate'
      responses:
        '201':
          description: Satıcı başarıyla oluşturuldu
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Seller'

  /sellers/{sellerId}:
    # 8. Satıcı Detaylarını Görüntüleme (Read)
    get:
      tags:
        - sellers
      summary: Satıcı detayı
      description: Bir satıcının şirket adı ve sattığı ürünlerle birlikte getirilmesi.
      operationId: getSellerById
      parameters:
        - $ref: '#/components/parameters/SellerIdParam'
      responses:
        '200':
          description: Başarılı
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/SellerDetails'
        '404':
          $ref: '#/components/responses/NotFound'

  # 9. Satıcı Şirket Bilgilerini Güncelleme (Update)
  /sellers/{sellerId}/company:
    patch:
      tags:
        - sellers
      summary: Satıcı şirket bilgisini güncelle
      description: Satıcının sadece companyName gibi ticari bilgilerinin güncellenmesi.
      operationId: updateSellerCompany
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/SellerIdParam'
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                companyName:
                  type: string
                  example: "Odyofil Ses Sistemleri A.Ş."
      responses:
        '200':
          description: Şirket bilgileri güncellendi

  # 15. Satıcıya Göre Ürünleri Listeleme (Read)
  /sellers/{sellerId}/products:
    get:
      tags:
        - products
      summary: Satıcının ürünlerini listele
      description: Belirli bir satıcının tüm portföyünün getirilmesi.
      operationId: listProductsBySeller
      parameters:
        - $ref: '#/components/parameters/SellerIdParam'
      responses:
        '200':
          description: Başarılı
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Product'

  /products:
    # 11. Yeni Ürün Ekleme (Create)
    post:
      tags:
        - products
      summary: Yeni ürün ekle
      description: Bir satıcının kendi hesabına yeni bir kulaklık/ürün eklemesi.
      operationId: createProduct
      security:
        - bearerAuth: []
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ProductCreate'
      responses:
        '201':
          description: Ürün başarıyla oluşturuldu

  /products/{productId}:
    # 12. Ürün Detayı Getirme (Read)
    get:
      tags:
        - products
      summary: Ürün detayı
      description: Ürünün fiyatı, ismi ve görselleriyle birlikte tekil olarak getirilmesi.
      operationId: getProductById
      parameters:
        - $ref: '#/components/parameters/ProductIdParam'
      responses:
        '200':
          description: Başarılı
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Product'
        '404':
          $ref: '#/components/responses/NotFound'
    
    # 13. Ürün Bilgilerini Güncelleme (Update)
    put:
      tags:
        - products
      summary: Ürün güncelle
      description: Mevcut bir ürünün fiyatının veya adının değiştirilmesi.
      operationId: updateProduct
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/ProductIdParam'
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ProductUpdate'
      responses:
        '200':
          description: Ürün güncellendi
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Product'
    
    # 14. Ürün Silme (Delete)
    delete:
      tags:
        - products
      summary: Ürün sil
      description: Stoktan kalkan veya satılmayan bir ürünün veri tabanından silinmesi.
      operationId: deleteProduct
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/ProductIdParam'
      responses:
        '204':
          description: Ürün başarıyla silindi

  # 16. Ürün Görseli Yönetimi (Update/Delete)
  /products/{productId}/images:
    post:
      tags:
        - products
      summary: Ürüne görsel ekle
      description: Item içindeki images setine yeni URL eklenmesi.
      operationId: addProductImage
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/ProductIdParam'
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                imageUrl:
                  type: string
                  format: uri
                  example: "https://example.com/images/sennheiser-momentum3.jpg"
      responses:
        '201':
          description: Görsel eklendi
    
    delete:
      tags:
        - products
      summary: Üründen görsel sil
      description: Item içindeki images setinden belirtilen URL'in silinmesi.
      operationId: deleteProductImage
      security:
        - bearerAuth: []
      parameters:
        - $ref: '#/components/parameters/ProductIdParam'
        - name: imageUrl
          in: query
          required: true
          description: Silinecek görselin URL'i
          schema:
            type: string
            format: uri
      responses:
        '204':
          description: Görsel başarıyla silindi

components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT

  parameters:
    UserIdParam:
      name: userId
      in: path
      required: true
      schema:
        type: string
        format: uuid
    SellerIdParam:
      name: sellerId
      in: path
      required: true
      schema:
        type: string
        format: uuid
    ProductIdParam:
      name: productId
      in: path
      required: true
      schema:
        type: string
        format: uuid

  schemas:
    User:
      type: object
      properties:
        id:
          type: string
          format: uuid
        email:
          type: string
          format: email
        firstName:
          type: string
        lastName:
          type: string
        role:
          type: string
          enum: [SHOPPER, ADMIN, SELLER]
          example: "SHOPPER"
    
    UserRegistration:
      type: object
      required: [email, password, firstName, lastName]
      properties:
        email:
          type: string
          format: email
        password:
          type: string
          format: password
        firstName:
          type: string
        lastName:
          type: string
    
    UserUpdate:
      type: object
      properties:
        firstName:
          type: string
        lastName:
          type: string
        email:
          type: string
          format: email

    PasswordUpdate:
      type: object
      required: [oldPassword, newPassword]
      properties:
        oldPassword:
          type: string
          format: password
        newPassword:
          type: string
          format: password

    Seller:
      allOf:
        - $ref: '#/components/schemas/User'
        - type: object
          properties:
            companyName:
              type: string
              example: "Audio Store A.Ş."

    SellerCreate:
      type: object
      required: [email, password, companyName]
      properties:
        email:
          type: string
          format: email
        password:
          type: string
          format: password
        companyName:
          type: string
    
    SellerDetails:
      allOf:
        - $ref: '#/components/schemas/Seller'
        - type: object
          properties:
            products:
              type: array
              items:
                $ref: '#/components/schemas/Product'

    Product:
      type: object
      properties:
        id:
          type: string
          format: uuid
        sellerId:
          type: string
          format: uuid
        name:
          type: string
          example: "Sennheiser IE 200 Kulak İçi Kulaklık"
        price:
          type: number
          format: float
          example: 4500.00
        images:
          type: array
          items:
            type: string
            format: uri
          example: ["https://example.com/ie200-1.jpg", "https://example.com/ie200-2.jpg"]

    ProductCreate:
      type: object
      required: [name, price]
      properties:
        name:
          type: string
        price:
          type: number
          format: float
        images:
          type: array
          items:
            type: string
            format: uri

    ProductUpdate:
      type: object
      properties:
        name:
          type: string
        price:
          type: number
          format: float

  responses:
    BadRequest:
      description: Geçersiz istek
    Unauthorized:
      description: Yetkisiz erişim
    Forbidden:
      description: Erişim reddedildi
    NotFound:
      description: Kaynak bulunamadı
```