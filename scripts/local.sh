#!/bin/bash

BASE_PORT=5000

setup-api() {
  mkdir "../logs"
  touch "../logs/${1}.txt"
}

start-api() {
  PORT=$((BASE_PORT + $(index "${2}")))

  sam local start-api --port "${PORT}" --env-vars "local-env.json" --log-file "../logs/${1}.txt"
  # &> /dev/null &

  echo "${1} is process ${!} and is running on port ${PORT}"

  ((PORT++))
}

run-service() {
  FUNCTION_NAME="$(service-name "${1}")"

  cd "${PROJECT_DIR}/${1}"

  setup-api "${FUNCTION_NAME}"
  start-api "${FUNCTION_NAME}" "${1}"
}

run-all-services() {
  build

  docker-compose -f docker/docker-compose.yml up -d

  for-each-service "run-service"
}
