#!/usr/bin/env bash
echo "Content-type: application/json"
echo "Access-Control-Allow-Origin: *"
echo ""
java SearchBackend "$QUERY_STRING" 2>&1
