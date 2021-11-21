PID=$(cat "${HOME}"/.Stools/application.pid)
if [ "$PID" != "" ]; then
    kill "${PID}"
    echo "STools stop!";
else
    echo "STools not run!";
fi

nohup sudo java -jar -Dspring.profiles.active=online ./build/libs/STools-0.0.1-SNAPSHOT.jar > info.log 2>&1 &