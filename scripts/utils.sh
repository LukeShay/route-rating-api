#!/bin/bash -e

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
