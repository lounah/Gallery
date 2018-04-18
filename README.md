# Gallery
Приложение-галерея для Школы мобильной разработки Яндекса 2018. Использует  <a href="https://tech.yandex.ru/disk/">API Яндекс.Диска</a>

<li>Фотографии можно сохранять у себя в галерее</li>
<li>Кэширует фотографии, позволяя просматривать их даже в режиме офлайн</li>
<li>Фотографии можно удалять, а затем легко восстанавливать из Корзины</li>
<li>Фотографии можно открывать полном размере</li>
<li>Поддерживает portrait/landscape ориентацию экрана</li>
<li>Поддерживает анимацию добавления/удаления фотографии</li>

# Скриншоты
<p>
<a href="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523874903.png" target="_blank">
  <img src="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523874903.png" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875012.png" target="_blank">
  <img src="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875012.png" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875034.png" target="_blank">
  <img src="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875034.png" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875125.png" target="_blank">
  <img src="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875125.png" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
<a href="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875178.png" target="_blank">
  <img src="https://github.com/Lounah/Gallery/blob/master/Screenshot_1523875178.png" width="270" height="480" alt="Screenshot" style="max-width:100%;">
</a>
</p>

# Сборка проекта
Для сборки необходимо получить <a href="https://oauth.yandex.ru">токен</a> и записать его в build.gradle

# Использованные библиотеки

<li> <a href="">Retrofit 2</a></li>
<li> <a href="">RxJava 2</a></li>
<li> <a href="">Dagger 2</a></li>
<li> <a href="">Google Architecture Components</a></li>
<li> <a href="">Gson</a></li>
<li> <a href="">Glide</a></li>
<li> <a href="">Leak Canary</a></li>
<li> <a href="">Timber</a></li>

# TBD 
Тесты

# Примечания
В приложении много недостатков, как на уровне взаимодействия с пользователем, так и на уровне архитектуры. Так, например, на данный момент не реализована возможность зумирования изображения, что, по-моему, является одной из самых важных функций приложений-галерей. Также во фрагменте с просмотром изображения нет popup меню в тулбаре, что довольно неудобно со стороны пользователя. Также большим минусом приложения является то, что оно не сохраняет скролл после пересоздания активити
Что касается архитектуры приложения, то код содержит довольно много повторений (все dialog fragments похожи друг на друга), много rx и почти не использовались встроенные в Андроид классы для работы с многопоточностью (была попытка реализовать в репозитории, но она ужасна). Также в проекте много грязи в styles

# ВАЖНО!
Приложение будет крашиться от NPE при открытии с устройства, которое не предусматривает наличие SD карты. Дело в том, что функционал, связанный с сохранением изображения на устройство пользователя, использует external storage, которого может и не быть. И при попытке считывания из файла, в который приложение должно сохранять фотографии при помощи метода getListFiles(), возвращается null, что и приводит к NPE. 
