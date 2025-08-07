#!/bin/bash

echo "🎮 Words Crush Game - Demo Launcher"
echo "=================================="
echo ""
echo "Choose an option:"
echo "1) Start Demo Server (in-memory database)"
echo "2) Start Client GUI"
echo "3) Start both Server and Client"
echo ""
read -p "Enter choice (1-3): " choice

case $choice in
    1)
        echo "🚀 Starting Demo Server..."
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.DemoProgram"
        ;;
    2)
        echo "🖥️  Starting Client GUI..."
        echo "💡 Connect to: 127.0.0.1:8001"
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client"
        ;;
    3)
        echo "🚀 Starting Demo Server in background..."
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.DemoProgram" &
        SERVER_PID=$!
        sleep 5
        echo "🖥️  Starting Client GUI..."
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client"
        echo "🛑 Stopping server..."
        kill $SERVER_PID 2>/dev/null
        ;;
    *)
        echo "❌ Invalid choice. Please run again and choose 1, 2, or 3."
        exit 1
        ;;
esac
