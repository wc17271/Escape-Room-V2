# SOFTENG 206 - EscAIpe Room

https://github.com/user-attachments/assets/2777519a-feb1-4abc-8409-d3a7029946c4

## To setup OpenAI's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside the credentials that you received from no-reply@digitaledu.ac.nz (put the quotes "")
  `    email: “UPI@aucklanduni.ac.nz"
    apiKey: “YOUR_KEY”
   `
  these are your credentials to invoke the OpenAI GPT APIs

## To setup codestyle's API

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `codestyle.config`
- put inside the credentials that you received from gradestyle@digitaledu.ac.nz (put the quotes "")
  `    email: “UPI@aucklanduni.ac.nz"
    accessToken: “YOUR_KEY”
   `
  these are your credentials to invoke gradestyle

## To run the game

`./mvnw clean javafx:run`

## To debug the game

`./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"

## To run codestyle

`./mvnw clean compile exec:java@style`
