# Github app guide

It's a java app created using Quarkus

## Requirements

To compile and run the app you will need:

- JDK 17+
- Mandrel or GraalVM

Use this command to run: mvnw quarkus:dev

### Available endpoints:
- getRepositories(String username)

It allows the user to list all repos per user. Each repo
contains its name, owner login, all branches which are not forks 
along with their last commit sha.

### Example response:

[
{
"repositoryName": "bank-app",
"login": "Barhwald",
"branches": [
{
"name": "main",
"sha": "3ef75367e02f47ebc2951a608c1c090177cd44b2"
}
]
},
{
"repositoryName": "BankingApp",
"login": "Barhwald",
"branches": [
{
"name": "main",
"sha": "2b0f3b3950240c026a8aee4686ff1c06d2417c71"
}
]
}
]