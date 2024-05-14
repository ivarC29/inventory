-- This query is executed when the application is launched, checking the bean initializer in the InventoryApplication.java file.

-- Creation of tables
CREATE TABLE IF NOT EXISTS category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    category_description TEXT
);

CREATE TABLE IF NOT EXISTS unit_of_measure (
    measure_id SERIAL PRIMARY KEY,
    measure_name VARCHAR(100) NOT NULL,
    measure_abbreviation VARCHAR(10) NOT NULL,
    measure_description TEXT
);

CREATE TABLE IF NOT EXISTS laboratory (
    laboratory_id SERIAL PRIMARY KEY,
    laboratory_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmacy (
    pharmacy_id SERIAL PRIMARY KEY,
    pharmacy_name VARCHAR(100) NOT NULL,
    pharmacy_ruc VARCHAR(20) NOT NULL,
    pharmacy_address VARCHAR(100) NOT NULL,
    pharmacy_city VARCHAR(100) NOT NULL,
    pharmacy_country VARCHAR(100),
    pharmacy_cellphone VARCHAR(20) NOT NULL,
    pharmacy_email VARCHAR(50) NOT NULL,
    responsible VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS clinic (
    clinic_id SERIAL PRIMARY KEY,
    clinic_name VARCHAR(255) NOT NULL,
    clinic_ruc VARCHAR(20) NOT NULL,
    clinic_address VARCHAR(255) NOT NULL,
    clinic_cellphone VARCHAR(20) NOT NULL,
    clinic_city VARCHAR(100) NOT NULL,
    clinic_state VARCHAR(100),
    clinic_zip VARCHAR(20),
    clinic_country VARCHAR(100),
    clinic_email VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS supplier (
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(255) NOT NULL,
    supplier_ruc VARCHAR(20) NOT NULL,
    supplier_address VARCHAR(255),
    supplier_cellphone VARCHAR(20) NOT NULL,
    supplier_email VARCHAR(50)NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse_location (
    location_id SERIAL PRIMARY KEY,
    clinic_id BIGINT NOT NULL,
    location_name VARCHAR(255) NOT NULL,
    location_address VARCHAR(255) NOT NULL,
    location_capacity DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (clinic_id) REFERENCES clinic(clinic_id)
);

CREATE TABLE IF NOT EXISTS product (
    product_id SERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL,
    measure_id BIGINT NOT NULL,
    laboratory_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_description TEXT,
    product_barcode VARCHAR(100) NOT NULL,
    product_cost DOUBLE PRECISION NOT NULL,
    product_price DOUBLE PRECISION NOT NULL,
    quantity DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (measure_id) REFERENCES unit_of_measure(measure_id),
    FOREIGN KEY (laboratory_id) REFERENCES laboratory(laboratory_id)
);

CREATE TABLE IF NOT EXISTS product_lot (
    lot_id SERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    manufacturing_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    lot_number VARCHAR(50),
    quantity DOUBLE PRECISION,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE IF NOT EXISTS clinic_inventory (
    inventory_id SERIAL PRIMARY KEY,
    product_id BIGINT,
    location_id BIGINT,
    quantity_in_stock DOUBLE PRECISION,
    location_in_warehouse VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (location_id) REFERENCES warehouse_location(location_id)
);

CREATE TABLE IF NOT EXISTS inventory_transaction (
    transaction_id SERIAL PRIMARY KEY,
    supplier_id BIGINT,
    inventory_id BIGINT NOT NULL,
    pharmacy_id BIGINT,
    transaction_type VARCHAR(50) NOT NULL,
    transaction_datetime TIMESTAMP NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id),
    FOREIGN KEY (inventory_id) REFERENCES clinic_inventory(inventory_id),
    FOREIGN KEY (pharmacy_id) REFERENCES pharmacy(pharmacy_id),
    CONSTRAINT check_client_supplier CHECK (
        ( pharmacy_id IS NOT NULL AND supplier_id IS NULL ) OR
        ( pharmacy_id IS NULL AND supplier_id IS NOT NULL )
   )
);

CREATE TABLE IF NOT EXISTS inventory_transaction_detail (
    transaction_detail_id SERIAL,
    transaction_id BIGINT,
    lot_id BIGINT NOT NULL,
    quantity_affected DOUBLE PRECISION NOT NULL,
    transaction_datetime TIMESTAMP NOT NULL,
    PRIMARY KEY (transaction_id, transaction_detail_id),
    FOREIGN KEY (transaction_id) REFERENCES inventory_transaction(transaction_id),
    FOREIGN KEY (lot_id) REFERENCES product_lot(lot_id)
);

CREATE TABLE IF NOT EXISTS purchase_order (
    order_id SERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    order_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id)
);

CREATE TABLE IF NOT EXISTS purchase_order_detail (
    order_detail_id SERIAL NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity_ordered DOUBLE PRECISION NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL,
    order_date DATE NOT NULL,
    PRIMARY KEY (order_id, order_detail_id),
    FOREIGN KEY (order_id) REFERENCES purchase_order(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
