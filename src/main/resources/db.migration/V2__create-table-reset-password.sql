CREATE TABLE reset_password (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    token VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    expiration_time TIMESTAMP NOT NULL
)