#!/bin/bash
set -e
section_message(){
    title=$1
    echo ""
    echo "**********************************************"
    echo "**********************************************"
    echo "${title}"
    echo "**********************************************"
    echo "**********************************************"
    echo ""
}
section_message "Obtaining Data Base host"
echo ""
echo "*****************************************"
echo "STARTING  Service..."
echo "*****************************************"
exec java -jar -Xmx${JDK_MAX_MEMORY} app.jar
exec "$@"