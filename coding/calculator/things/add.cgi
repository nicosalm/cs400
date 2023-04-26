#!/usr/bin/env bash
echo "Content-type: application/json"
echo ""

#echo "<p>this page was generated at:"
#date
#echo "</p>"

java Add "$QUERY_STRING"
