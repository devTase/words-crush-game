#!/bin/bash

echo "🎮 Words Crush Game - Demo Launcher"
echo "=================================="
echo ""
echo "Choose an option:"
echo "1) Start Demo Server (in-memory database)"
echo "2) Start Client GUI"
echo "3) Start both Server and Client"
echo "4) Start Server + 2 Clients (Multiplayer Demo)"
echo ""
read -p "Enter choice (1-4): " choice

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
    4)
        echo "🚀 Starting Demo Server in background..."
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.DemoProgram" &
        SERVER_PID=$!
        sleep 7
        echo "🖥️  Starting Client 1 (Player)..."
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client" &
        CLIENT1_PID=$!
        sleep 3
        echo "🖥️  Starting Client 2 (Admin)..."
        mvn exec:java -Dexec.mainClass="org.academiadecodigo.wordsgame.application.client.Client" &
        CLIENT2_PID=$!
        echo ""
        echo "✅ Multiplayer Demo Setup Complete!"
        echo "📋 Two clients connected:"
        echo "   • Client 1: Use player/player to login"
        echo "   • Client 2: Use admin/admin to login"
        echo ""
        echo "⏹️  Press ENTER to stop all processes..."
        read
        echo "🛑 Stopping clients and server..."
        kill $CLIENT1_PID 2>/dev/null
        kill $CLIENT2_PID 2>/dev/null
        kill $SERVER_PID 2>/dev/null
        sleep 2
        echo "✅ All processes stopped!"
        ;;
    *)
        echo "❌ Invalid choice. Please run again and choose 1, 2, 3, or 4."
        exit 1
        ;;
esac
