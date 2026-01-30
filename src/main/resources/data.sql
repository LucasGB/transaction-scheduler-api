INSERT INTO transaction_fee_rule (min_amount, max_amount, min_days, max_days, rate, fixed_fee, active)
VALUES (0.00, 1000.00, 0, 0, 0.03, 3.00, true);

INSERT INTO transaction_fee_rule (min_amount, max_amount, min_days, max_days, rate, fixed_fee, active)
VALUES (1001.00, 2000.00, 1, 10, 0.09, 0.00, true);

INSERT INTO transaction_fee_rule (min_amount, max_amount, min_days, max_days, rate, fixed_fee, active)
VALUES (2000.01, 999999999.99, 11, 20, 0.082, 0.00, true);

INSERT INTO transaction_fee_rule (min_amount, max_amount, min_days, max_days, rate, fixed_fee, active)
VALUES (2000.01, 999999999.99, 21, 30, 0.069, 0.00, true);

INSERT INTO transaction_fee_rule (min_amount, max_amount, min_days, max_days, rate, fixed_fee, active)
VALUES (2000.01, 999999999.99, 31, 40, 0.047, 0.00, true);

INSERT INTO transaction_fee_rule (min_amount, max_amount, min_days, max_days, rate, fixed_fee, active)
VALUES (2000.01, 999999999.99, 41, 999999, 0.017, 0.00, true);