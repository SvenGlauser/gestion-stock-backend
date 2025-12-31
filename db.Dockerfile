FROM postgres:alpine
RUN apk add --no-cache supervisor \
&& mkdir /etc/supervisor.d \
&& echo "*/20 * * * * bash /scripts/backup.sh" >> /var/spool/cron/crontabs/root
COPY postgresql_scripts/postgres_cron.ini /etc/supervisor.d/postgres_cron.ini
ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]