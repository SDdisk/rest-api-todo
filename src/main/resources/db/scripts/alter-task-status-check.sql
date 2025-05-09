ALTER TABLE
    task
    ADD CONSTRAINT
        task_status_check
        CHECK (
            status IN (
                       'NEW',
                       'IN_PROGRESS',
                       'DONE',
                       'EXPIRED',
                       'ARCHIVED')
            );
