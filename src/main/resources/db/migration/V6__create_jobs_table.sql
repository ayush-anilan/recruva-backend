CREATE TABLE jobs (
    id UUID PRIMARY KEY,

    organization_id UUID NOT NULL,
    created_by_user_id UUID NOT NULL,

    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(255),
    category VARCHAR(255),

    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',

    posted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_job_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT fk_job_created_by_user
        FOREIGN KEY (created_by_user_id)
        REFERENCES users(id)
);