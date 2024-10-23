CREATE TABLE bill (
      id UUID PRIMARY KEY,
      type VARCHAR(255) NOT NULL,
      name VARCHAR(255) NOT NULL,
      estimated_value DECIMAL NOT NULL,
      paid_value DECIMAL,
      user_id UUID NOT NULL,
      created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      updated_at TIMESTAMP WITHOUT TIME ZONE,

      CONSTRAINT fk_user
          FOREIGN KEY(user_id)
              REFERENCES users(id)
              ON DELETE CASCADE
);