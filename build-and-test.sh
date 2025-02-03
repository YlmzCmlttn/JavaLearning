#!/bin/bash

# Function to build and test a service
build_and_test_service() {
  local service_dir=$1
  echo "Building and testing service in $service_dir"
  cd $service_dir || exit
  mvn clean package
  if [ $? -ne 0 ]; then
    echo "Build failed for $service_dir"
    exit 1
  fi
  cd - > /dev/null || exit
}

# List of services
services=(
  "services/flight-scheduling"
  "services/booking"
)

# Iterate over each service and build/test
for service in "${services[@]}"; do
  build_and_test_service $service
done

echo "All services built and tested successfully." 