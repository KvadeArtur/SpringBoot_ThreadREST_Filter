## Задача

#### Создать REST с параметрами страницы и фильтра. 
В фильтер передается регулярное выражение, и мы получаем данные из базы НЕ содержащие то что мы передали.
Также должна поддерживатся многопоточность.

Например:

Запрос **/list?page=2&nameFilter=^K.*$**

Получаем ***{ "id": 11, "name": "Thing", "description": "poiuz" }***
