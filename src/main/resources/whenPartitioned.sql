-- FUNCTIONS

CREATE OR REPLACE FUNCTION clinic_inventory_create_partition()
    RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_catalog.pg_class c
                          JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
        WHERE c.relname = 'clinic_inventory_partitioned'
    ) THEN
        EXECUTE 'CREATE TABLE clinic_inventory_partitioned (
            CHECK (clinic_id = ' || NEW.clinic_id || ')
        ) PARTITION OF clinic_inventory_partitioned FOR VALUES IN (' || NEW.clinic_id || ')';
    ELSE
        EXECUTE 'CREATE TABLE IF NOT EXISTS clinic_inventory_partitioned_' || NEW.clinic_id || ' PARTITION OF clinic_inventory_partitioned FOR VALUES IN (' || NEW.clinic_id || ')';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION inventory_transaction_create_yearly_partition() RETURNS TRIGGER AS $$
DECLARE
    current_year TEXT;
    next_year TEXT;
BEGIN
    current_year := to_char(current_date, 'YYYY');
    next_year := to_char(current_date + interval '1 year', 'YYYY');

    EXECUTE format('CREATE TABLE IF NOT EXISTS inventory_transaction_partition_%s PARTITION OF inventory_transaction_partitioned FOR VALUES FROM (%s-01-01) TO (%s-01-01)', current_year, current_year, next_year);

    EXECUTE format('CREATE TABLE IF NOT EXISTS inventory_transaction_partition_%s PARTITION OF inventory_transaction_partitioned FOR VALUES FROM (%s-01-01) TO (%s-01-01)', next_year, next_year, next_year);

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION create_product_lot_partitions_trigger() RETURNS TRIGGER AS $$
DECLARE
    current_year INTEGER;
    next_year INTEGER;
    current_partition_name TEXT;
    next_partition_name TEXT;
BEGIN
    SELECT EXTRACT(YEAR FROM CURRENT_DATE), EXTRACT(YEAR FROM CURRENT_DATE) + 1 INTO current_year, next_year;

    current_partition_name := 'product_lot_' || current_year;
    next_partition_name := 'product_lot_' || next_year;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = current_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF product_lot FOR VALUES FROM (%L-01-01) TO (%L-01-01)', current_partition_name, current_year, next_year);
    END IF;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = next_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF product_lot FOR VALUES FROM (%L-01-01) TO (%L-01-01)', next_partition_name, next_year, next_year+1);
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION create_inventory_transaction_detail_partitions_trigger() RETURNS TRIGGER AS $$
DECLARE
    current_semester_start DATE;
    next_semester_start DATE;
    next_next_semester_start DATE;
    current_partition_name TEXT;
    next_partition_name TEXT;
    next_next_partition_name TEXT;
BEGIN
    SELECT DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month' * MOD(EXTRACT(MONTH FROM CURRENT_DATE)::int - 1, 6) AS current_semester_start,
           DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '6 month' - INTERVAL '1 day' - INTERVAL '1 month' * MOD(EXTRACT(MONTH FROM CURRENT_DATE)::int - 1, 6) AS next_semester_start,
           DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '12 month' - INTERVAL '1 day' - INTERVAL '1 month' * MOD(EXTRACT(MONTH FROM CURRENT_DATE)::int - 1, 6) AS next_next_semester_start
    INTO current_semester_start, next_semester_start, next_next_semester_start;

    current_partition_name := 'inventory_transaction_detail_' || EXTRACT(YEAR FROM current_semester_start) || '_' || EXTRACT(MONTH FROM current_semester_start);
    next_partition_name := 'inventory_transaction_detail_' || EXTRACT(YEAR FROM next_semester_start) || '_' || EXTRACT(MONTH FROM next_semester_start);
    next_next_partition_name := 'inventory_transaction_detail_' || EXTRACT(YEAR FROM next_next_semester_start) || '_' || EXTRACT(MONTH FROM next_next_semester_start);

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = current_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF inventory_transaction_detail FOR VALUES FROM (%L) TO (%L)', current_partition_name, current_semester_start, next_semester_start);
    END IF;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = next_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF inventory_transaction_detail FOR VALUES FROM (%L) TO (%L)', next_partition_name, next_semester_start, next_next_semester_start);
    END IF;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = next_next_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF inventory_transaction_detail FOR VALUES FROM (%L) TO (%L)', next_next_partition_name, next_next_semester_start, next_next_semester_start + INTERVAL '6 month');
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION create_purchase_order_partitions_trigger() RETURNS TRIGGER AS $$
DECLARE
    current_year INTEGER;
    next_year INTEGER;
    current_partition_name TEXT;
    next_partition_name TEXT;
BEGIN
    SELECT EXTRACT(YEAR FROM CURRENT_DATE), EXTRACT(YEAR FROM CURRENT_DATE) + 1 INTO current_year, next_year;

    current_partition_name := 'purchase_order_' || current_year;
    next_partition_name := 'purchase_order_' || next_year;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = current_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF purchase_order FOR VALUES FROM (%L-01-01) TO (%L-01-01)', current_partition_name, current_year, next_year);
    END IF;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = next_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF purchase_order FOR VALUES FROM (%L-01-01) TO (%L-01-01)', next_partition_name, next_year, next_year+1);
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION create_purchase_order_detail_partitions_trigger() RETURNS TRIGGER AS $$
DECLARE
    current_semester_start DATE;
    next_semester_start DATE;
    next_next_semester_start DATE;
    current_partition_name TEXT;
    next_partition_name TEXT;
    next_next_partition_name TEXT;
BEGIN
    SELECT DATE_TRUNC('month', CURRENT_DATE) - INTERVAL '1 month' * MOD(EXTRACT(MONTH FROM CURRENT_DATE)::int - 1, 6) AS current_semester_start,
           DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '6 month' - INTERVAL '1 day' - INTERVAL '1 month' * MOD(EXTRACT(MONTH FROM CURRENT_DATE)::int - 1, 6) AS next_semester_start,
           DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '12 month' - INTERVAL '1 day' - INTERVAL '1 month' * MOD(EXTRACT(MONTH FROM CURRENT_DATE)::int - 1, 6) AS next_next_semester_start
    INTO current_semester_start, next_semester_start, next_next_semester_start;

    current_partition_name := 'purchase_order_detail_' || EXTRACT(YEAR FROM current_semester_start) || '_' || EXTRACT(MONTH FROM current_semester_start);
    next_partition_name := 'purchase_order_detail_' || EXTRACT(YEAR FROM next_semester_start) || '_' || EXTRACT(MONTH FROM next_semester_start);
    next_next_partition_name := 'purchase_order_detail_' || EXTRACT(YEAR FROM next_next_semester_start) || '_' || EXTRACT(MONTH FROM next_next_semester_start);

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = current_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF purchase_order_detail FOR VALUES FROM (%L) TO (%L)', current_partition_name, current_semester_start, next_semester_start);
    END IF;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = next_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF purchase_order_detail FOR VALUES FROM (%L) TO (%L)', next_partition_name, next_semester_start, next_next_semester_start);
    END IF;

    IF NOT EXISTS(SELECT relname FROM pg_class WHERE relname = next_next_partition_name) THEN
        EXECUTE FORMAT('CREATE TABLE %I PARTITION OF purchase_order_detail FOR VALUES FROM (%L) TO (%L)', next_next_partition_name, next_next_semester_start, next_next_semester_start + INTERVAL '6 month');
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Queries

CREATE TABLE IF NOT EXISTS clinic_inventory_0 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 0);
CREATE TABLE IF NOT EXISTS clinic_inventory_1 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 1);
CREATE TABLE IF NOT EXISTS clinic_inventory_2 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 2);
CREATE TABLE IF NOT EXISTS clinic_inventory_3 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 3);
CREATE TABLE IF NOT EXISTS clinic_inventory_4 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 4);
CREATE TABLE IF NOT EXISTS clinic_inventory_5 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 5);
CREATE TABLE IF NOT EXISTS clinic_inventory_6 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 6);
CREATE TABLE IF NOT EXISTS clinic_inventory_7 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 7);
CREATE TABLE IF NOT EXISTS clinic_inventory_8 PARTITION OF clinic_inventory FOR VALUES WITH (MODULUS 9, REMAINDER 8);

--- Triggers
CREATE TRIGGER insert_clinic_inventory_trigger
    AFTER INSERT ON clinic_inventory
    FOR EACH ROW
EXECUTE FUNCTION clinic_inventory_create_partition();

CREATE TRIGGER create_yearly_partition_trigger
    BEFORE INSERT ON inventory_transaction
    FOR EACH ROW
EXECUTE FUNCTION inventory_transaction_create_yearly_partition();

CREATE TRIGGER insert_product_lot_trigger
    AFTER INSERT ON product_lot
    FOR EACH ROW
EXECUTE FUNCTION create_product_lot_partitions_trigger();

CREATE TRIGGER insert_inventory_transaction_detail_trigger
    AFTER INSERT ON inventory_transaction_detail
    FOR EACH ROW
EXECUTE FUNCTION create_inventory_transaction_detail_partitions_trigger();

CREATE TRIGGER insert_purchase_order_trigger
    AFTER INSERT ON purchase_order
    FOR EACH ROW
EXECUTE FUNCTION create_purchase_order_partitions_trigger();

CREATE TRIGGER insert_purchase_order_trigger
    AFTER INSERT ON purchase_order
    FOR EACH ROW
EXECUTE FUNCTION create_purchase_order_partitions_trigger();