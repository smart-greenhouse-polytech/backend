CREATE TABLE greenhouses
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name       TEXT      NOT NULL,
    location   TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE greenhouse_owners
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    greenhouse_id UUID      NOT NULL REFERENCES greenhouses (id) ON DELETE CASCADE,
    role          TEXT      NOT NULL,
    user_id       UUID      NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE greenhouse_setting
(
    id                  UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
    greenhouse_id       UUID             NOT NULL REFERENCES greenhouses (id) ON DELETE CASCADE,
    temp_min            DOUBLE PRECISION NOT NULL,
    temp_max            DOUBLE PRECISION NOT NULL,
    humidity_min        DOUBLE PRECISION NOT NULL,
    humidity_max        DOUBLE PRECISION NOT NULL,
    light_intensity_min DOUBLE PRECISION NOT NULL,
    light_intensity_max DOUBLE PRECISION NOT NULL,
    created_at          TIMESTAMP        NOT NULL DEFAULT now(),
    updated_at          TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE TABLE crops
(
    id                UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
    name              TEXT             NOT NULL,
    water_requirement DOUBLE PRECISION NOT NULL,
    temp_min          DOUBLE PRECISION NOT NULL,
    temp_max          DOUBLE PRECISION NOT NULL,
    created_at        TIMESTAMP        NOT NULL DEFAULT now(),
    updated_at        TIMESTAMP        NOT NULL DEFAULT now()
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
    name          TEXT      NOT NULL,
    crops_id      UUID      REFERENCES crops (id) ON DELETE SET NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE devices
(
    id            UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    name          TEXT      NOT NULL,
    type          TEXT      NOT NULL,
    greenhouse_id UUID      NOT NULL REFERENCES greenhouses (id) ON DELETE CASCADE,
    bed_id        UUID      REFERENCES beds (id) ON DELETE SET NULL,
    status        TEXT      NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'ERROR')),
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE device_measurement
(
    id         UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
    device_id  UUID             NOT NULL REFERENCES devices (id) ON DELETE CASCADE,
    value      DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE TABLE irrigation_schedule
(
    id           UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    bed_id       UUID      NOT NULL REFERENCES beds (id) ON DELETE CASCADE,
    days_of_week TEXT      NOT NULL,
    start_time   TIMESTAMP NOT NULL,
    end_time     TIMESTAMP NOT NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP NOT NULL DEFAULT now()
);
