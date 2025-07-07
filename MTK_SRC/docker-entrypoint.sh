#!/bin/sh
/app/wait-for-it.sh postgres:5432 --timeout=120 --strict
/app/wait-for-it.sh elasticsearch:9200 --timeout=150 --strict
/app/wait-for-it.sh kafka:9092 --timeout=60 --strict
/app/wait-for-it.sh redis:6379 --timeout=60 --strict
exec java -jar mtk.jar