#!/bin/bash -e

. ./scripts/new_service.sh
. ./scripts/deploy.sh
. ./scripts/utils.sh

help() {
  echo "See the functions scripts/new_service.sh and scripts/deploy.sh for possible commands."
}

echo "WARNING: This script will exit if there is a non-zero exit code!"

eval "${@}"