  Elective
There is a list of courses divided into topics. One teacher is assigned to each course. It is 
necessary to implement the following functionality:
- sort courses by name (az, za), duration, number of students enrolled in the course;
- a selection of courses related to a specific topic;
- a selection of the particular teacher courses.
  The student enrolls in one or more courses, registration data is stored. At the end of the
  course the teacher gives the student a grade, which is stored in the journal.
  Each user has a personal account, which displays brief information about the user, as well as
  for the student:
- a list of courses for which the student has registered but which have not yet begun;
- a list of courses for which the student has registered and which are in progress;
- a list of completed courses with information about grades;
  for the teacher:
- viewing and editing an e-journal for assigned courses.
  The system administrator has the rights:
- registration of the teacher and assignment of the course to him;
- adding, deleting, editing a course;
- blocking, unlocking the student.

Вимоги до реалізації:
1. На основі сутностей предметної області створити класи, які їм відповідають.
2. Класи і методи повинні мати назви, що відображають їх функціональність, і повинні бути
   рознесені по пакетам.
3. Оформлення коду має відповідати Java Code Convention.
4. Інформацію щодо предметної області зберігати у реляційній базі даних (в якості СУБД
   рекомендується використовувати MySQL або PostgreSQL).
5. Для доступу до даних використовувати JDBC API із застосуванням готового або ж
   розробленого самостійно пулу з'єднань.

НЕ допускається використання ORM фреймворків

6. Застосунок має підтримувати роботу з кирилицею (бути багатомовним), в тому числі при
   зберіганні інформації в базі даних:
   a. повинна бути можливість перемикання мови інтерфейсу;
   b. повинна бути підтримка введення, виведення і зберігання інформації (в базі даних),
   записаної на різних мовах;
   c. в якості мов обрати мінімум дві: одна на основі кирилиці (українська або російська),
   інша на основі латиниці (англійська).

7. Архітектура застосунка повинна відповідати шаблону MVC.

НЕ допускається використання MVC-фреймворків

8. При реалізації бізнес-логіки необхідно використовувати шаблони проектування: Команда,
   Стратегія, Фабрика, Будівельник, Сінглтон, Фронт-контролер, Спостерігач, Адаптер та ін.

Використання шаблонів повинно бути обґрунтованим

9. Використовуючи сервлети і JSP, реалізувати функціональність, наведену в постановці
   завдання.
10. Використовувати Apache Tomcat у якості контейнера сервлетів.
11. На сторінках JSP застосовувати теги з бібліотеки JSTL та розроблені власні теги (мінімум: один
    тег custom tag library і один тег tag file).
12. Реалізувати захист від повторної відправки даних на сервер при оновленні сторінки
    (реалізувати PRG).
13. При розробці використовувати сесії, фільтри, слухачі.
14. У застосунку повинні бути реалізовані аутентифікація і авторизація, розмежування прав
    доступу користувачів системи до компонентів програми. Шифрування паролів заохочується.
15. Впровадити у проект журнал подій із використанням бібліотеки log4j.
16. Код повинен містити коментарі документації (всі класи верхнього рівня, нетривіальні методи
    і конструктори).
17. Застосунок має бути покритим модульними тестами (мінімальний відсоток покриття 40%).
    Написання інтеграційних тестів заохочуються.
18. Реалізувати механізм пагінації сторінок з даними.
19. Всі поля введення повинні бути із валідацією даних.
20. Застосунок має коректно реагувати на помилки та виключні ситуації різного роду (кінцевий
    користувач не повинен бачити stack trace на стороні клієнта).
21. Самостійне розширення постановки задачі по функціональності заохочується! (додавання
    капчі, формування звітів у різних форматах, тощо)
22. Використання HTML, CSS, JS фреймворків для інтерфейсу користувача (Bootstrap, Materialize,
    ін.) заохочується!

За три дні до моменту старту захистів проектів (інтерв’ю) необхідно підготувати у
вигляді окремого файлу схему бази даних, а також надати посилання на репозиторій із проектом

## <a id="extra"></a>Extra functionality
- send an email when you reset your password, enroll/leave a course
- captcha for login
- three failed login attempts and the user will be redirected to the password reset page
- print reports (for all courses and course with grades)
- autoload page every 30 minutes for update data and after 61 minutes to end session
- two indexes for courses (name) and users (login) in the database for faster information retrieval
- pagination
- error processing
- sort courses by it`s name (using like)
- sort courses by start_date from the current moment and above
- sort courses by user login
- was modernised the functionality "At the end of the course the teacher gives the student a grade, which is stored in the journal" to "The course has a separate grade for each topic and an average overall grade"
- for a better user experience: drop-down lists with up-to-date data, hints, informing the user with messages about the results of his actions, etc.

Application logic:
- created 1 trigger in user for stop changing role from: student ==> teacher and back if he has at least one course 
- created 1 trigger in user for stopping deleting user if user has at least 1 course 
- created 1 stored procedure for checking all statuses in courses (for invoking trigger update in courses)
- created 2 triggers in courses for checking status and date_start/date_end 
- created 1 trigger in performance for deleting topic from course if topic doesn't have score bigger than 0 then it can be deleted (deleted due to user want to leave the course)
- created 1 triggers in performance for checking inputting grade (can be 0 or between 60 and 100) and status must be "CURRENT" (for teacher possibility  to change grades)
- created 1 trigger in performance for cancelling enroll student or add topics to course if courses status not "NOT_STARTED"
- created 1 trigger in users_has_courses to prevent the user from leaving the course unless the course status is "NOT_STARTED"

Database Schema:
![](src/main/resources/scripts/schema.png)