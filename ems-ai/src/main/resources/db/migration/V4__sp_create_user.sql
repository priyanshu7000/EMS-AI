CREATE OR REPLACE FUNCTION ems.sp_create_user(
    p_org_id UUID,
    p_role_id UUID,
    p_name VARCHAR,
    p_email VARCHAR,
    p_password VARCHAR
)
RETURNS UUID
LANGUAGE plpgsql
AS $$
DECLARE
    v_user_id UUID;
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM ems.roles
        WHERE id = p_role_id AND org_id = p_org_id
    ) THEN
        RAISE EXCEPTION 'Invalid role for this organization';
    END IF;

    INSERT INTO ems.users(
        org_id,
        role_id,
        name,
        email,
        password
    )
    VALUES (
        p_org_id,
        p_role_id,
        p_name,
        p_email,
        p_password
    )
    RETURNING id INTO v_user_id;

    RETURN v_user_id;
END;
$$;