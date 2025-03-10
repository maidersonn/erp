-- Insert admin user with encrypted password
INSERT INTO users (username, password, roles)
VALUES (
    'admin',
    '$2a$12$KIHkF9/VsPzE2WcSLfzJMeE8qiBPcEdBlirR96K7mzAy8HyS.rcCG', -- Replace with the hashed password
    ARRAY['ADMIN']
);