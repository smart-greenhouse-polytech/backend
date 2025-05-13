CREATE TABLE greenhouse_setting
(
    id                  UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    temp_min            DECIMAL   NOT NULL,
    temp_max            DECIMAL   NOT NULL,
    humidity_min        DECIMAL   NOT NULL,
    humidity_max        DECIMAL   NOT NULL,
    light_intensity_min DECIMAL   NOT NULL,
    light_intensity_max DECIMAL   NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT now(),
    updated_at          TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE greenhouses
(
    id                    UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name                  VARCHAR   NOT NULL,
    location              VARCHAR,
    greenhouse_setting_id UUID      REFERENCES greenhouse_setting (id) ON DELETE SET NULL,
    created_at            TIMESTAMP NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE greenhouse_owners
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    greenhouse_id UUID      REFERENCES greenhouses (id) ON DELETE SET NULL,
    role          VARCHAR   NOT NULL,
    user_id       UUID      NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE crops
(
    id                       UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name                     VARCHAR   NOT NULL,
    water_requirement_liters DECIMAL   NOT NULL,
    temp_min                 DECIMAL   NOT NULL,
    temp_max                 DECIMAL   NOT NULL,
    created_at               TIMESTAMP NOT NULL DEFAULT now(),
    updated_at               TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO crops (name, water_requirement_liters, temp_min, temp_max)
VALUES ('Tomato', 1.5, 18.0, 30.0),
       ('Lettuce', 0.8, 10.0, 25.0),
       ('Strawberry', 1.2, 15.0, 28.0),
       ('Cucumber', 1.7, 16.0, 32.0);

CREATE TABLE beds
(
    id              UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    greenhouse_id   UUID      REFERENCES greenhouses (id) ON DELETE SET NULL,
    name            VARCHAR   NOT NULL,
    crops_id        UUID      REFERENCES crops (id) ON DELETE SET NULL,
    last_irrigation TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE devices
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name          VARCHAR   NOT NULL UNIQUE,
    type          VARCHAR   NOT NULL,
    greenhouse_id UUID      REFERENCES greenhouses (id) ON DELETE SET NULL,
    bed_id        UUID      REFERENCES beds (id) ON DELETE SET NULL,
    status        VARCHAR   NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE device_measurement
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    device_id  UUID      NOT NULL REFERENCES devices (id) ON DELETE SET NULL,
    value      DECIMAL   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

DROP TYPE IF EXISTS day_of_week;

CREATE TYPE day_of_week AS ENUM (
    'MONDAY', 'TUESDAY', 'WEDNESDAY',
    'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'
);

CREATE TABLE irrigation_schedule
(
    id           UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    bed_id       UUID      REFERENCES beds (id) ON DELETE SET NULL,
    days_of_week day_of_week[] NOT NULL,
    start_time   TIME      NOT NULL,
    end_time     TIME      NOT NULL,
    is_active    BOOLEAN   NOT NULL DEFAULT true,
    created_at   TIMESTAMP NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP NOT NULL DEFAULT now()
);

-- Вставка тестовых данных в таблицу greenhouse_setting
INSERT INTO greenhouse_setting (temp_min, temp_max, humidity_min, humidity_max, light_intensity_min, light_intensity_max)
VALUES
    (18.0, 30.0, 40.0, 80.0, 300.0, 1000.0),
    (15.0, 25.0, 30.0, 70.0, 250.0, 900.0),
    (10.0, 35.0, 50.0, 90.0, 350.0, 1100.0),
    (20.0, 28.0, 35.0, 75.0, 320.0, 950.0);

-- Вставка тестовых данных в таблицу greenhouses
INSERT INTO greenhouses (name, location, greenhouse_setting_id)
VALUES
    ('Greenhouse A', 'Location 1', (SELECT id FROM greenhouse_setting WHERE temp_min = 18.0 LIMIT 1)),
    ('Greenhouse B', 'Location 2', (SELECT id FROM greenhouse_setting WHERE temp_min = 15.0 LIMIT 1)),
    ('Greenhouse C', 'Location 3', (SELECT id FROM greenhouse_setting WHERE temp_min = 10.0 LIMIT 1)),
    ('Greenhouse D', 'Location 4', (SELECT id FROM greenhouse_setting WHERE temp_min = 20.0 LIMIT 1));

-- Вставка тестовых данных в таблицу crops
INSERT INTO crops (name, water_requirement_liters, temp_min, temp_max)
VALUES
    ('Tomato', 1.5, 18.0, 30.0),
    ('Lettuce', 0.8, 10.0, 25.0),
    ('Strawberry', 1.2, 15.0, 28.0),
    ('Cucumber', 1.7, 16.0, 32.0);

-- Вставка тестовых данных в таблицу beds
INSERT INTO beds (greenhouse_id, name, crops_id)
VALUES
    ((SELECT id FROM greenhouses WHERE name = 'Greenhouse A' LIMIT 1), 'Bed 1', (SELECT id FROM crops WHERE name = 'Tomato' LIMIT 1)),
    ((SELECT id FROM greenhouses WHERE name = 'Greenhouse A' LIMIT 1), 'Bed 2', (SELECT id FROM crops WHERE name = 'Lettuce' LIMIT 1)),
    ((SELECT id FROM greenhouses WHERE name = 'Greenhouse B' LIMIT 1), 'Bed 3', (SELECT id FROM crops WHERE name = 'Strawberry' LIMIT 1)),
    ((SELECT id FROM greenhouses WHERE name = 'Greenhouse C' LIMIT 1), 'Bed 4', (SELECT id FROM crops WHERE name = 'Cucumber' LIMIT 1));

-- Вставка тестовых данных в таблицу devices
INSERT INTO devices (name, type, greenhouse_id, bed_id, status)
VALUES
    ('Valve 1', 'WATER_VALVE', (SELECT id FROM greenhouses WHERE name = 'Greenhouse A' LIMIT 1), (SELECT id FROM beds WHERE name = 'Bed 1' LIMIT 1), 'ACTIVE'),
    ('Heater 1', 'HEATING', (SELECT id FROM greenhouses WHERE name = 'Greenhouse B' LIMIT 1), (SELECT id FROM beds WHERE name = 'Bed 3' LIMIT 1), 'ACTIVE'),
    ('Fan 1', 'FAN', (SELECT id FROM greenhouses WHERE name = 'Greenhouse C' LIMIT 1), (SELECT id FROM beds WHERE name = 'Bed 4' LIMIT 1), 'ACTIVE'),
    ('Window 1', 'WINDOW', (SELECT id FROM greenhouses WHERE name = 'Greenhouse D' LIMIT 1), (SELECT id FROM beds WHERE name = 'Bed 2' LIMIT 1), 'ACTIVE');

-- Вставка тестовых данных в таблицу device_measurement
INSERT INTO device_measurement (device_id, value)
VALUES
    ((SELECT id FROM devices WHERE name = 'Valve 1' LIMIT 1), 50.0),
    ((SELECT id FROM devices WHERE name = 'Heater 1' LIMIT 1), 22.0),
    ((SELECT id FROM devices WHERE name = 'Fan 1' LIMIT 1), 30.0),
    ((SELECT id FROM devices WHERE name = 'Window 1' LIMIT 1), 15.0);

-- Вставка данных в таблицу irrigation_schedule с явным приведением типов
INSERT INTO irrigation_schedule (bed_id, days_of_week, start_time, end_time, is_active)
VALUES
    ((SELECT id FROM beds WHERE name = 'Bed 1' LIMIT 1), ARRAY['MONDAY'::day_of_week, 'WEDNESDAY'::day_of_week, 'FRIDAY'::day_of_week], '08:00', '10:00', true),
    ((SELECT id FROM beds WHERE name = 'Bed 2' LIMIT 1), ARRAY['TUESDAY'::day_of_week, 'THURSDAY'::day_of_week], '09:00', '11:00', true),
    ((SELECT id FROM beds WHERE name = 'Bed 3' LIMIT 1), ARRAY['MONDAY'::day_of_week, 'THURSDAY'::day_of_week], '07:00', '09:00', false),
    ((SELECT id FROM beds WHERE name = 'Bed 4' LIMIT 1), ARRAY['WEDNESDAY'::day_of_week, 'SATURDAY'::day_of_week], '10:00', '12:00', true);
