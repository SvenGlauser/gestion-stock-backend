cd C:\dev\workspaces\gestion-stock
docker compose exec -it database pg_dump postgres -f /backups/backup_%time:~0,2%%time:~3,2%%time:~6,2%_%date:~-10,2%%date:~-7,2%%date:~-4,4%.sql