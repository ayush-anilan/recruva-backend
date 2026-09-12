CREATE TABLE applications (
    id UUID PRIMARY KEY,

    organization_candidate_id UUID NOT NULL,
    job_id UUID NOT NULL,

    status VARCHAR(50) NOT NULL DEFAULT 'APPLIED',

    applied_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_application_organization_candidate
        FOREIGN KEY (organization_candidate_id)
        REFERENCES organization_candidates(id),

    CONSTRAINT fk_application_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(id),

    CONSTRAINT uk_application_candidate_job
        UNIQUE (organization_candidate_id, job_id)
);