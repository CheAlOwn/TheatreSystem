# TheatreSystem
***
## Об информационной системе театра
Информационная система театра - это программное обеспечение позволяющее автоматизировать процессы учета сотрудников, продажи билетов, поездок на гастроли, спектаклей, ролей, а также управление репертуарами.
***
## Зависимости
<li>JavaFX-21.0.4 </li>
<li>postgresql-42.7.4</li>

## Требуемое программное обеспечение
<li>PostgreSQL-16.6</li>
<li>pgadmin4</li>

## Установка
1. Скачать проект ZIP архивом или выполнить клонирование данного репозитория.

   ```git
   git clone https://github.com/CheAlOwn/TheatreSystem.git
2. Выгрузить резервную копию базы данных из файла `backdb.sql`. Для этого нужно:
   
   1). Создать в СУБД базу данных с названием `theatre_system`.

   ![image](https://github.com/user-attachments/assets/a6b27c64-f477-4a49-9cfd-a3cfe1bdc408)

   2). Открыть терминал и ввести следующую команду:

   ```zsh
   psql -d theatre_system -U postgres -f ~/backdb.sql
   ```
3. Запустить IntelliJ IDEA.
4. Открыть папку со скачанной информационной системой.
5. Найти файл `Main.java` по следующему пути: `src/main/java/com/theatre/theatre_system/Main.java`.
6. Запустить `Main.java`.
