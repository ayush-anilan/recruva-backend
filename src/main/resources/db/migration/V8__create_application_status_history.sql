CREATE TABLE application_status_history (
    id UUID PRIMARY KEY,

    application_id UUID NOT NULL,
    organization_id UUID NOT NULL,

    previous_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,

    changed_by_user_id UUID NOT NULL,
    changed_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_application_status_history_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id),

    CONSTRAINT fk_application_status_history_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT fk_application_status_history_changed_by_user
        FOREIGN KEY (changed_by_user_id)
        REFERENCES users(id)
);

CREATE INDEX idx_application_status_history_app_org_changed_at
    ON application_status_history(
        application_id,
        organization_id,
        changed_at
    );