#!/bin/bash

echo "🔧 Applying code formatting with Spotless..."
mvn spotless:apply

if [ $? -eq 0 ]; then
  echo "✅ Code formatting completed successfully!"
  echo "💡 You can now commit your changes."
else
  echo "❌ Code formatting failed!"
  exit 1
fi
