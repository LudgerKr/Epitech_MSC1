psql -h localhost -p 5432 -U richard timeManager_dev -c "insert into roles values(1, 'Administrator', now(), now())";
psql -h localhost -p 5432 -U richard timeManager_dev -c "insert into roles values(2, 'Manager', now(), now())";
psql -h localhost -p 5432 -U richard timeManager_dev -c "insert into roles values(3, 'Employee', now(), now())";