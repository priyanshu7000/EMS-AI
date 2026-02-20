CREATE SCHEMA IF NOT EXISTS ems;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE ems.organizations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ems.roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    org_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_roles_org FOREIGN KEY (org_id)
        REFERENCES ems.organizations(id) ON DELETE CASCADE
);

CREATE TABLE ems.users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    org_id UUID NOT NULL,
    role_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_org FOREIGN KEY (org_id)
        REFERENCES ems.organizations(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id)
        REFERENCES ems.roles(id)
);

CREATE UNIQUE INDEX idx_users_email_org
ON ems.users(email, org_id);