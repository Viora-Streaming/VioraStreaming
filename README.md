# Project Code Style 

Google Java Style Guide: https://google.github.io/styleguide/javaguide.html

Front-End Guidelines: https://gist.github.com/stowball/6ca2fc1d868ebb049f043dbec782dd68

## База даних
База даних має бути консистентною з Backend-моделями (через ORM).

Таблиці: snake\_case, множина.

Колонки: snake\_case, однина.

Зовнішні ключі (FK): суть\_id.

## Загальні правила для всіх
Boolean змінні: Мають починатися з префіксів is\_, has\_, can\_.

Абревіатури: Не пишіть GetUI, пишіть GetUi (в PascalCase) або get\_ui (в snake\_case).

Заборонити «сміттєві / Meaningful» назви: Офіційно заборонити використання data, info, temp для файлів та сутностей. Файл data.\*— це "смітник". Краще product\_schemas.\*.

Formatting: Розмір відступу (2 чи 4 пробіли), використання крапки з комою, максимальна довжина рядка (наприклад, 100 або 120 символів).

Comments: Правила документування коду (наприклад, обов'язкові JSDoc або JavaDoc для публічних методів).

