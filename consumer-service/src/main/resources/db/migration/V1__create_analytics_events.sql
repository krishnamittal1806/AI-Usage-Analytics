CREATE TABLE analytics_events (
                                  event_id    UUID PRIMARY KEY,
                                  event_type  VARCHAR(32)  NOT NULL,
                                  user_id     VARCHAR(64)  NOT NULL,
                                  event_time  TIMESTAMPTZ  NOT NULL,
                                  received_at TIMESTAMPTZ  NOT NULL,
                                  metadata    JSONB        NOT NULL
);

CREATE INDEX idx_events_type_time ON analytics_events (event_type, event_time);
CREATE INDEX idx_events_user_time ON analytics_events (user_id, event_time);