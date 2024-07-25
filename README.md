# ltc_hack_24_cust_eng_beekeepers

## Pre-requisites

1. Java 21
2. Maven 3.8.9
3. Postgres - psql client
4. Google Cloud CLI - gcloud CLI
5. Git
6. Postman
7. Flutter UI
8. Docker

## Flutter Android Studio Commands
Use these commands in terminal when the flutter SDK path is configured in the environment local variable in Path :
1. flutter clean                    - Cleans the build related files.
2. flutter pub get                  - Reloads all the dependencies and builds the project to run
3. flutter build apk --release      - Builds the apk. After the build completes, find the APK in the **build/app/outputs/flutter-apk** directory.


## mac set up

1. Install homebrew
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   eval "$(/opt/homebrew/bin/brew shellenv)"
3. Install postgresql
   brew install postgresql@14

   echo 'export PATH="/opt/homebrew/opt/postgresql@14/bin:$PATH"' >> ~/.zshrc

## project set up on gcp
1. Create and login to GCP account
2. Under SQL -> Select Create Instance -> Postgres SQL
3. Select the configuration with minimum viable cost and click on "Create Instance"
4. Create a db named "ce_ltc_hack_24_beekeepers"
5. Connect to the database and run DB script (db_script.md) to create tables and make data entry
6. Create a service account in google cloud console and give right to cloud sql client role
7. Push the java spring boot which connects to Postgres SQL db in Github repo.
8. Create a dockerfile which will list down the steps of building the project, create the image and deploy.
9. Create a GCP Cloud Build trigger, pointing to the git repo, feature branch push.
10. Using the cloud build image created, create a cloud run service with public IP and allowing all network connections
11. Set following environment variables to connect to cloud sql via cloud run.

Instance ID - postgres
Password - beekeepers

Database Name - ce_ltc_hack_24_beekeepers

## gcloud commands
gcloud init

gcloud components list
gcloud components install cloud-sql-proxy


## GCP POSTGRES SQL DB Commands

gcloud sql databases list -i postgres

gcloud auth application-default login

echo 'export GOOGLE_APPLICATION_CREDENTIALS=$HOME/.config/gcloud/application_default_credentials.json' >> ~/.zshenv

echo 'export GOOGLE_APPLICATION_CREDENTIALS=$HOME/uplifted-woods-429810-t9-7ee6cd739174.json' >> ~/.zshenv

docker build .

docker run -d -v "$HOME/.config/gcloud/application_default_credentials.json":/gcp/creds.json:ro -env GOOGLE_APPLICATION_CREDENTIALS=/gcp/creds.json -p 8080:8080 flex

gcloud sql connect postgres --user=postgres --database=ce_ltc_hack_24_beekeepers

# Important URLs and Cheat Sheets

1. GCPing - https://gcping.com
2. GCLOUD Commands - https://cloud.google.com/sdk/docs/cheatsheet
3. SQL Syntax Checker - https://www.scaler.com/topics/sql/sql-validator/
4. Postgres SQL - https://www.geeksforgeeks.org/postgresql-cheat-sheet/
5. Online API Tester - https://reqbin.com/




