### N.B.

__There is an extensive documentation as per service, please see in project-specific folders.__

Please disable adblock, there might be some rendering issues with some combinations of platform, browser and system theme.


#### run tests

go to service specific directory and execute 
```shell
./gradlew check
```

to run locally 

1. Import in IDEA, run
2. Lookup for file `jhipster-registry.yml` (there are several, as per microservice)
3. `jhipster-registry.yml` with `docker compose up -d`
4. Run other services with IntelliJ IDEA runner or Gradle runner

run in `prod` mode

1. Execute `./gradlew bootJar -Pprod jibDockerBuild` for each service folder
2. Go to docker-compose folder and execute `docker-compose up`


Project base was created with JHipster, there is a common layout of entities in the manu bar.

You can check the functionality implemented in https://github.com/soberich/tsevrah-eulb/issues/1 via Swagger-UI in menu bar when logged as `admin:admin`
this is the most convenient way

Project did not aim to precisely target the business requirements of the task, but rather provide production-ready solution
Of course such a goal requires a lot of effort, so there are some compromises for the purpose of this demo.
Example of a compromise is docker-compose used instead K8s.
I would gladly discuss details with reviewers.

##### Requirments and their respective implementations

to authenticate if you prefer to use plain curl/httpie
```shell
curl \
  -X POST "http://localhost:8080/api/authenticate" -H "Content-Type: application/json" \
  -d "{ \"password\": \"admin\", \"rememberMe\": true, \"username\": \"admin\"}"
```

* The API will expose an endpoint which accepts the user information (customerID, initialCredit).
* Once the endpoint is called, a new account will be opened connected to the user whose ID is customerID.
* Also, if initialCredit is not 0, a transaction will be sent to the new account.
  ```shell
  curl \
    -X POST "http://localhost:8080/services/account/api/account-applications" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYwNDQ4MDA4MH0.q-sbKq-XOzOXKBwNkSnT4JrOTqjk9qfxQuP3nyYJD1YjOwRbJpQ6wj-4Q6tb9DznXK2ujj-F9mCp0udHpRnGQg" \
    -d "{ \"customerID\": 2, \"initialCredit\": 1350}"
  ```
* Another Endpoint will output the user information showing Name, Surname, balance, and transactions of the accounts.
  ```shell
  curl \
    -X GET "http://localhost:8080/services/account/api/my-account-applications/2" \
    -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYwNDQ4MDA4MH0.q-sbKq-XOzOXKBwNkSnT4JrOTqjk9qfxQuP3nyYJD1YjOwRbJpQ6wj-4Q6tb9DznXK2ujj-F9mCp0udHpRnGQg"
  ```
