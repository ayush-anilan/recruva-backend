CREATE TABLE organization_candidates (
    id UUID PRIMARY KEY,

    candidate_id UUID NOT NULL,
    organization_id UUID NOT NULL,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_organization_candidate_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id),

    CONSTRAINT fk_organization_candidate_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT uk_organization_candidate_candidate_organization
        UNIQUE (candidate_id, organization_id)
);