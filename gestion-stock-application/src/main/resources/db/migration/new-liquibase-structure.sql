UPDATE databasechangelog
SET filename = replace(filename, 'db/1.0/', 'db/1.0/init/');

INSERT INTO public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id)
VALUES ('create-spring-batch-tables', 'svenglauser', 'db/1.0/batch/batch-create-tables.xml', '2026-07-30 14:22:42.167111', 13, 'EXECUTED', '9:e96525a8d1149e9bfcd6822e5e4ce56e', 'createSequence sequenceName=BATCH_STEP_EXECUTION_SEQ; createSequence sequenceName=BATCH_JOB_EXECUTION_SEQ; createSequence sequenceName=BATCH_JOB_INSTANCE_SEQ; createTable tableName=BATCH_JOB_INSTANCE; addUniqueConstraint constraintName=JOB_INST_UN...', '', null, '5.0.1', null, null, '5414160787');
