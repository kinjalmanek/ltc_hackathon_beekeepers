# DB Scripts

### Table Creation

CREATE TABLE users (
    email VARCHAR (355) PRIMARY KEY NOT NULL,
    name VARCHAR (50) NOT NULL,
    password VARCHAR (50),
    country_code VARCHAR (5) NOT NULL,
    phone_number VARCHAR (10) NOT NULL,
    is_internal BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL,
    otp VARCHAR (6),
    created_on TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

CREATE TABLE portfolio (
    portfolio_id serial PRIMARY KEY,
    account_number VARCHAR (16) NOT NULL,
    account_balance NUMERIC NOT NULL,
    product_type VARCHAR (100) NOT NULL,
    loan_type VARCHAR (100),
    bill_type VARCHAR (100),
    email VARCHAR (355) NOT NULL,
    name VARCHAR (100) NOT NULL,
    amount NUMERIC NOT NULL,
    loan_tenure NUMERIC,
    bill_frequency VARCHAR(20),
    bill_date TIMESTAMP
);

CREATE TABLE perm (
    account_email VARCHAR (355) NOT NULL,
    email VARCHAR (355) NOT NULL,
    portfolio_id integer NOT NULL,
    PRIMARY KEY (account_email, email, portfolio_id),
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    is_access_permanent BOOLEAN NOT NULL,
    can_view BOOLEAN NOT NULL,
    can_pay BOOLEAN NOT NULL
);


INSERT INTO users (
    email,
    name,
    password,
    country_code,
    phone_number,
    is_internal,
    is_active,
    otp,
    created_on,
    last_login
)
VALUES
    ('rakesh@gmail.com', 'Rakesh', '12345678', '91','9999999999',true,true,null,NOW(),null),
    ('ramdas@gmail.com', 'Ramdas', '12345678', '91','9999999999',true,true,null,NOW(),null);


INSERT INTO portfolio ( email, name, account_number, account_balance, product_type, loan_type, loan_tenure, amount, bill_type, bill_frequency, bill_date ) 
VALUES
('rakesh@gmail.com','Rakesh Kotian', 'AC001999', '100000','BALANCE', null, null, '0', null, null, null);

INSERT INTO portfolio ( email, name, account_number, account_balance, product_type, loan_type, loan_tenure, amount, bill_type, bill_frequency, bill_date ) 
VALUES
('rakesh@gmail.com','Rakesh Kotian', 'AC001999', '100000','LOAN', 'HOME LOAN', '20', '2000000', null, null, null),
('rakesh@gmail.com','Rakesh Kotian', 'AC001999', '100000','BILL', null, null, '2000', 'PHONE BILL', 'MONTHLY', '01-JAN-2024'),
('rakesh@gmail.com','Rakesh Kotian', 'AC001999', '100000','BILL', null, null, '5000', 'ELECTRICITY BILL', 'MONTHLY', '05-JAN-2024');
