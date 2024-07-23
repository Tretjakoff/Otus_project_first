#!/bin/bash

BROWSER_NAME="${BROWSER_NAME:-chrome}"

mvn test -Dbrowser=$BROWSER_NAME