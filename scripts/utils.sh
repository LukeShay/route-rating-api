#!/bin/bash -e

PROJECT_DIR=${PWD}
SERVICES=(
  "gyms"
  "routes"
  "walls"
  "users"
  "ratings"
)
COMMIT=$(git rev-parse --short HEAD)$(git diff --quiet || echo ".uncommitted")

dots() {
  eval ${@} &> /dev/null &

  trap "kill ${!} 2> /dev/null" EXIT

  while kill -0 "${!}" 2> /dev/null; do
      printf "."
      sleep 0.5
  done

  trap - EXIT

  printf "\n"
}

ignore() {
  printf ""
}

service-name() {
  printf "route-rating-api-%s" "${1}"
}

build() {
  if [[ "${SKIP_BUILD}" != "TRUE" ]]; then
    printf "Building services"

    find . -name build -type d | xargs rm -r || ignore
    dots ./gradlew clean build
  fi
}

for-each-service() {
  for service in "${SERVICES[@]}"; do
    eval "${1}" "${service}"
  done
}

index() {
  for i in "${!SERVICES[@]}"; do
    if [[ "${SERVICES[$i]}" = "${1}" ]]; then
      printf "%s" "${i}";
    fi
  done
}
