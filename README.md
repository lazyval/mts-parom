mts-parom
=========

Краткий гайд для git-новичка:
Клонируем репозиторий 
```
git clone https://github.com/lazyval/mts-parom.git
```
После этого нас попросят ввести логин и пароль (так как мы выбрали https для общения). Например у меня:

```
Username for 'https://github.com': lazyval
Password for 'https://lazyval@github.com': 
```

Дальше, лучше всего открыть проект просто в идее и если ты работаешь над какой-то новой фичой сделать ветку и все ключевые изменения коммитить в нее 

**TODO: написать как с ними работать** <-- пока не написал, можешь подходить и спрашивать Костю.

Структура проекта
==

Проект состоит из двух модулей: сорсы и другие файлы приложения (корень директории) и /iceberg (проект для тестов).

Правила именования ресурсов/исходных кодов
==
Все это делается для удобства поиска/автокомплита/эстетики
Ресурсы именуем не camelCase, а_c_нижними_подчеркиваниями. Подчеркивание разделяет слова.
Первое слово означает тип ресурса. например activity_main если это активити, fragment_time_line если это фрагмент, view_gallery_item если это - какая-то вьюшка.
Идентификаторы(id) тоже именуются c первым словом типа например btn_go или ic_main_logo (иконка) bg_footer( бекграунд) и т.д.

В проекте уже нарисовалась некая структура - стараемся следовать ей.
Можно и приветствуется менять струтуру.
Нельзя хуярить все в одном пакете.

commons-io
===
дисккасс: в guava есть IO пекедж, и  откровенно говоря его(и не только)  уже начал использовать.
Либо оставляем guava либо commons-io