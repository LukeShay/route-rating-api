#!/bin/bash -e

docker-build() {
  if [[ "${SKIP_IMAGE}" != "TRUE" ]]; then
    printf "Building image for %s" "${1}"
    dots docker build -t "lukeshaydocker/${1}:latest" -t "lukeshaydocker/${1}:${COMMIT}" -f docker/Dockerfile .
  fi
}

docker-build-service() {
  cd "${PROJECT_DIR}/${1}"
  docker-build $(service-name "${1}")
}

docker-build-all() {
  build
  for-each-service "docker-build-service"
  printf "Successfully build all docker images!"
}

