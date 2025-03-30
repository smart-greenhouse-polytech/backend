CREATE TABLE greenhouses
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name       VARCHAR   NOT NULL,
    location   VARCHAR   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE greenhouse_owners
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    greenhouse_id UUID      NOT NULL REFERENCES greenhouses (id) ON DELETE CASCADE,
    role          VARCHAR   NOT NULL,
    user_id       UUID      NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE greenhouse_setting
(
    id                  UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    greenhouse_id       UUID      NOT NULL REFERENCES greenhouses (id) ON DELETE CASCADE,
    temp_min            DECIMAL   NOT NULL,
    temp_max            DECIMAL   NOT NULL,
    humidity_min        DECIMAL   NOT NULL,
    humidity_max        DECIMAL   NOT NULL,
    light_intensity_min DECIMAL   NOT NULL,
    light_intensity_max DECIMAL   NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT now(),
    updated_at          TIMESTAMP NOT NULL DEFAULT now()
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

INSERT INTO crops (name, water_requirement, temp_min, temp_max)
VALUES ('Tomato', 1.5, 18.0, 30.0),
       ('Lettuce', 0.8, 10.0, 25.0),
       ('Strawberry', 1.2, 15.0, 28.0),
       ('Cucumber', 1.7, 16.0, 32.0);

CREATE TABLE beds
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    greenhouse_id UUID      NOT NULL REFERENCES greenhouses (id) ON DELETE CASCADE,
    name          VARCHAR   NOT NULL,
    crops_id      UUID      REFERENCES crops (id) ON DELETE SET NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE devices
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name          VARCHAR   NOT NULL,
    type          VARCHAR   NOT NULL,
    device_id     VARCHAR   NOT NULL,
    mac_address   VARCHAR   NOT NULL,
    mqtt_username VARCHAR   NOT NULL,
    mqtt_password VARCHAR   NOT NULL,
    greenhouse_id UUID REFERENCES greenhouses (id) ON DELETE CASCADE,
    bed_id        UUID      REFERENCES beds (id) ON DELETE SET NULL,
    status        VARCHAR   NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'ERROR')),
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE device_measurement
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    device_id  UUID      NOT NULL REFERENCES devices (id) ON DELETE CASCADE,
    value      DECIMAL   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE irrigation_schedule
(
    id                     UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    bed_id                 UUID      NOT NULL REFERENCES beds (id) ON DELETE CASCADE,
    days_of_week           VARCHAR   NOT NULL,
    required_volume_liters DECIMAL   NOT NULL,
    start_time             TIMESTAMP NOT NULL,
    is_active              BOOLEAN   NOT NULL,
    created_at             TIMESTAMP NOT NULL DEFAULT now(),
    updated_at             TIMESTAMP NOT NULL DEFAULT now()
);
