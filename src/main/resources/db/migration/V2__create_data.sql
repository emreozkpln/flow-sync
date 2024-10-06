INSERT INTO store (store_name, store_id, lat, lng) VALUES ('Store 1', 'IST', '40.976', '28.814');
INSERT INTO store (store_name, store_id, lat, lng) VALUES ('Store 2', 'SAW', '40.899', '29.309');
INSERT INTO store (store_name, store_id, lat, lng) VALUES ('Store 3', 'LHR', '51.470', '-0.454');
INSERT INTO store (store_name, store_id, lat, lng) VALUES ('Store 4', 'JFK', '40.641', '-73.778');
INSERT INTO store (store_name, store_id, lat, lng) VALUES ('Store 5', 'CDG', '49.009', '2.549');

INSERT INTO product (product_id, product_name, stat, reading_time, passing_time, battery_level, store_id)
VALUES ('P12345', 'Product A', 'Active', NOW(), '2 hours', '85%', 1);
INSERT INTO product (product_id, product_name, stat, reading_time, passing_time, battery_level, store_id)
VALUES ('P12346', 'Product B', 'Inactive', NOW(), '3 hours', '60%', 2);
INSERT INTO product (product_id, product_name, stat, reading_time, passing_time, battery_level, store_id)
VALUES ('P12347', 'Product C', 'Active', NOW(), '1 hour', '90%', 3);
INSERT INTO product (product_id, product_name, stat, reading_time, passing_time, battery_level, store_id)
VALUES ('P12348', 'Product D', 'Inactive', NOW(), '5 hours', '40%', 4);
INSERT INTO product (product_id, product_name, stat, reading_time, passing_time, battery_level, store_id)
VALUES ('P12349', 'Product E', 'Active', NOW(), '6 hours', '70%', 5);

INSERT INTO report (report_name, description) VALUES ('Daily Report', 'Daily summary of product movements.');
INSERT INTO report (report_name, description) VALUES ('Weekly Report', 'Weekly overview of store operations.');
INSERT INTO report (report_name, description) VALUES ('Monthly Report', 'Monthly analysis of product statistics.');
INSERT INTO report (report_name, description) VALUES ('Maintenance Report', 'Report on product maintenance activities.');
INSERT INTO report (report_name, description) VALUES ('Security Report', 'Detailed report on security breaches.');

