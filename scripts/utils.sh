#!/bin/bash -e

dots() {
  trap "kill ${1} 2> /dev/null" EXIT

  while kill -0 "${1}" 2> /dev/null; do
      printf "."
      sleep 0.5
  done

  trap - EXIT

  printf "\n"
}

ignore() {
  printf ""
}
