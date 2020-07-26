# Diploma_Marrakesh

## Начало работы:

1. Для запуска MySQL:

- Запустить docker-machine start default
- Для создания образа БД MySQL в терминале InteliJ Idea выполнить команду: docker-compose -f docker-compose-mysql.yml up -d
- Для запуска SUT выполнить команду: java -jar artifacts/aqa-shop.jar -P:jdbc.url=jdbc:mysql://192.168.99.100:3306/app -P:jdbc.user=app -P:jdbc.password=pass
- Для запуска тестов на исполнение в терминале выполнить команду: gradlew test


2. Для запуска Postgres:

- Для создания образа БД Postgres в терминале InteliJ Idea выполнить команду: docker-compose -f docker-compose-postgres.yml up -d
- Для запуска SUT выполнить команду: java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar artifacts/aqa-shop.jar
- Для запуска тестов на исполнение в терминале выполнить команду:gradlew -Ddb.url=jdbc:postgresql://192.168.99.100:5432/app test
