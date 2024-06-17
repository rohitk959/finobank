-- ACCOUNTS DATA
INSERT INTO t_account (id, account_name, account_number, status, created_at, created_by, updated_at, updated_by)
VALUES ('47c6b570-ac31-42d1-932f-07667d481c69', 'Savings account', 'LU983414420027839700', 'ENABLED',
        '2024-06-16 12:47:01.891757', '5c75d60a-8ba7-4d28-846f-5488400178f6', null, null);
INSERT INTO t_account (id, account_name, account_number, status, created_at, created_by, updated_at, updated_by)
VALUES ('1dfcb850-0496-440c-b3ca-12b99cc852fc', 'Current account', 'LU120010001234567891', 'ENABLED',
        '2024-06-16 12:47:23.287792', '5c75d60a-8ba7-4d28-846f-5488400178f6', null, null);
INSERT INTO t_account (id, account_name, account_number, status, created_at, created_by, updated_at, updated_by)
VALUES ('1ff2ed84-99a1-4531-9659-6b7bad729cbe', 'Savings account', 'DE89370400440532013000', 'ENABLED',
        '2024-06-16 13:09:41.881184', '22a96824-893e-41ff-b5ad-3913ea6599a6', null, null);
INSERT INTO t_account (id, account_name, account_number, status, created_at, created_by, updated_at, updated_by)
VALUES ('0af1ea47-fc01-4cb5-b972-c819a982dfa0', 'Savings account', 'DE75512108001245126199', 'ENABLED',
        '2024-06-16 13:10:55.543183', '22a96824-893e-41ff-b5ad-3913ea6599a6', null, null);
INSERT INTO t_account (id, account_name, account_number, status, created_at, created_by, updated_at, updated_by)
VALUES ('5fa7391d-6052-48cd-a419-bce131f9948e', 'Savings account', 'LU120010001234567891', 'ENABLED',
        '2024-06-16 13:14:41.495136', '5690ed7c-0274-406f-9a1d-2fc7b57b524a', null, null);
INSERT INTO t_account (id, account_name, account_number, status, created_at, created_by, updated_at, updated_by)
VALUES ('87bac0f1-96fc-4b39-ae8e-6dd1709fc7ec', 'Current account', 'BE71096123456769', 'ENABLED',
        '2024-06-16 13:14:53.565860', '5690ed7c-0274-406f-9a1d-2fc7b57b524a', null, null);

-- ACCOUNTS AND USERS MAPPING
INSERT INTO t_account_user_mapping (account_id, users)
VALUES ('47c6b570-ac31-42d1-932f-07667d481c69', '5c75d60a-8ba7-4d28-846f-5488400178f6');
INSERT INTO t_account_user_mapping (account_id, users)
VALUES ('1dfcb850-0496-440c-b3ca-12b99cc852fc', '5c75d60a-8ba7-4d28-846f-5488400178f6');
INSERT INTO t_account_user_mapping (account_id, users)
VALUES ('1ff2ed84-99a1-4531-9659-6b7bad729cbe', '22a96824-893e-41ff-b5ad-3913ea6599a6');
INSERT INTO t_account_user_mapping (account_id, users)
VALUES ('0af1ea47-fc01-4cb5-b972-c819a982dfa0', '22a96824-893e-41ff-b5ad-3913ea6599a6');
INSERT INTO t_account_user_mapping (account_id, users)
VALUES ('5fa7391d-6052-48cd-a419-bce131f9948e', '5690ed7c-0274-406f-9a1d-2fc7b57b524a');
INSERT INTO t_account_user_mapping (account_id, users)
VALUES ('87bac0f1-96fc-4b39-ae8e-6dd1709fc7ec', '5690ed7c-0274-406f-9a1d-2fc7b57b524a');

-- BALANCE
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('0236b11f-e122-45b9-b369-4e57bf63e189', '47c6b570-ac31-42d1-932f-07667d481c69', 0, 'EUR', 'AVAILABLE',
        '2024-06-16 12:47:01.900758', '5c75d60a-8ba7-4d28-846f-5488400178f6');
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('a7bf708d-e86d-44ff-83a2-dab85acfdb35', '1dfcb850-0496-440c-b3ca-12b99cc852fc', 0, 'EUR', 'AVAILABLE',
        '2024-06-16 12:47:23.287792', '5c75d60a-8ba7-4d28-846f-5488400178f6');
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('089c3d77-1f37-4c16-847c-380b5a0f1030', '47c6b570-ac31-42d1-932f-07667d481c69', 20, 'EUR', 'AVAILABLE',
        '2024-06-16 12:47:51.005304', '5c75d60a-8ba7-4d28-846f-5488400178f6');
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('38a16e03-8071-406c-8bd5-3397abdff617', '1ff2ed84-99a1-4531-9659-6b7bad729cbe', 0, 'EUR', 'AVAILABLE',
        '2024-06-16 13:09:41.882184', '22a96824-893e-41ff-b5ad-3913ea6599a6');
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('55d468a2-f705-4e27-b87f-91700688a385', '0af1ea47-fc01-4cb5-b972-c819a982dfa0', 0, 'EUR', 'AVAILABLE',
        '2024-06-16 13:10:55.544183', '22a96824-893e-41ff-b5ad-3913ea6599a6');
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('38d021e7-71a0-4e56-b628-10f0ade9cc19', '5fa7391d-6052-48cd-a419-bce131f9948e', 0, 'EUR', 'AVAILABLE',
        '2024-06-16 13:14:41.496135', '5690ed7c-0274-406f-9a1d-2fc7b57b524a');
INSERT INTO t_balance (id, account_id, amount, currency, type, created_at, created_by)
VALUES ('60bd71d4-5ab7-44af-8b5b-490484d53e0c', '87bac0f1-96fc-4b39-ae8e-6dd1709fc7ec', 0, 'EUR', 'AVAILABLE',
        '2024-06-16 13:14:53.565860', '5690ed7c-0274-406f-9a1d-2fc7b57b524a');
