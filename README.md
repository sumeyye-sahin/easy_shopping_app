# FirebaseKotlin

Amaç: Staj sürecinde öğrenilen Android Kotlin , Firabase yapılarının tek bir uygulamada
kullanarak pekişmesini sağlamak.

Bu kapsamda yapılan proje mobil alışveriş uygulaması şeklinde tasarlanmıştır. Uygulamanın
işlevleri şu şekildedir:

1- Uygulamanın her açılmasında gösterilmesi için Splash Screen eklenir.

2- Kullanıcı kaydı oluşturulup giriş yapması halinde doğrulama sağlanarak aktif kullanıcı
bilgisi Firebase aracılığı ile tutulur.

3- Giriş yapıldıktan sonra Retrofit kullanılarak Api ile DummyJson’dan çekilen verilerin
RecycleView yapısı ile aktif bir şekilde gösterimi gerçekleştirilir.

4- Her bir ürünün listelenmesi sonrasında istenilen ürünün detayına bakılması için detay
sayfası tasarlanır ve Api ile alınan ürün bilgileri gösterilir.

5- Favori ürünleri tutmak için kullanıcı id ‘si ve ürün id’sinden Firebase aracılığı ile
faydalanılır. Ve her kullanıcıya ait favori ürün kaydı firebase realtime database’ine eklenir.

6- Menü çubuğundaki Logout item’ına tıklanılması sonucu aktif kullanıcı uygulamadan
çıkartılır ve giriş ekranına yönlendirilir.

7- Yeni kayıt olmak isteyen kullanıcılar ‘Sign Up’ sayfasına geçerek kayıt olur.

8- Menü çubuğundaki ‘Favorites’ item’ına tıklandığında favori ürünler listelenir.

9- Kullanıcılara bildirim gönderilmesi Firabase Mesaaging ile sağlanır

10- Search işlemi ürünün ismine göre yapılır.
