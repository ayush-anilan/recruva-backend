CREATE TABLE organization_members (
    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,
    organization_id UUID NOT NULL,

    role VARCHAR(255) NOT NULL,

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_organization_member_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_organization_member_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT uk_organization_member_user_organization
        UNIQUE (user_id, organization_id)
);