#!/bin/bash -e

EXIT_STATUS=0
PROJECT_DIR=${PWD}
SERVICES=(
  "gyms"
  "routes"
  "walls"
  "users"
  "ratings"
)
FUNCTION_PREFIX="route-rating-api-"

package() {
  if [[ "${SKIP_PACKAGE}" != "TRUE" ]]; then
    printf "Packaging %s" ${1}

    rm output.yml &> /dev/null || ignore

    sam package \
      --template-file template.yml \
      --s3-bucket "${1}-builds" \
      --s3-prefix "${1}" \
      --output-template-file output.yml \
      --region us-east-2 &> /dev/null &

    dots $!
  fi
}

deploy() {
  if [[ "${SKIP_DEPLOY}" != "TRUE" ]]; then
    printf "Deploying %s" ${1}

    sam deploy \
      --s3-bucket "${1}-builds" \
      --template-file output.yml \
      --region us-east-2 \
      --no-confirm-changeset \
      --stack-name "${1}" \
      --capabilities CAPABILITY_IAM &> /dev/null &

    dots $!
  fi
}

update-env() {
  if [[ "${SKIP_ENV}" != "TRUE" ]]; then
    printf "Updating env variables for %s" ${1}

    ENV_VARS="{
        GOOGLE_RECAPTCHA_TOKEN='${GOOGLE_RECAPTCHA_TOKEN}',
        REST_API_DB_PASSWORD='${REST_API_DB_PASSWORD}',
        DIGITAL_OCEAN_SECRET_KEY=${DIGITAL_OCEAN_SECRET_KEY},
        DIGITAL_OCEAN_ACCESS_KEY=${DIGITAL_OCEAN_ACCESS_KEY},
        REST_API_DB_URL=${REST_API_DB_URL},
        REST_API_JWT_SECRET=${REST_API_JWT_SECRET},
        REST_API_REFRESH_SECRET=${REST_API_REFRESH_SECRET},
        JAVA_TOOL_OPTIONS='-Xverify:none'
      }"

    FUNCTION_NAME=$(
      aws lambda list-functions |
        grep "${1}" |
        grep "FunctionName" |
        sed 's/"FunctionName": "//g' |
        sed 's/",//g' |
        sed 's/ //g'
    ) \
    && \
    aws lambda \
      update-function-configuration \
      --function-name "${FUNCTION_NAME}" \
      --environment "Variables=${ENV_VARS}" &> /dev/null &

    dots $!
  fi
}

deploy-service() {
    printf "Starting deploy for service %s\n" ${1}

    FUNCTION="${FUNCTION_PREFIX}${1}"

    cd "${PROJECT_DIR}/${1}"

    package "${FUNCTION}"
    deploy "${FUNCTION}"
    update-env "${FUNCTION}"
}

build() {
  if [[ "${SKIP_BUILD}" != "TRUE" ]]; then
    printf "Building services"

    find . -name build -type d | xargs rm -r || ignore
    ./gradlew clean build &> /dev/null &

    dots $!
  fi
}

deploy-all() {
  build

  for service in "${SERVICES[@]}"; do
    deploy-service ${service}
  done
}