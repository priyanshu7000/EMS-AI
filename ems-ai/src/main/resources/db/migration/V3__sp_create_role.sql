CREATE OR REPLACE FUNCTION ems.sp_create_role(
    p_org_id UUID,
    p_name VARCHAR
)
RETURNS UUID
LANGUAGE plpgsql
AS $$
DECLARE
    v_role_id UUID;
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM ems.organizations WHERE id = p_org_id
    ) THEN
        RAISE EXCEPTION 'Invalid organization';
    END IF;

    INSERT INTO ems.roles(org_id, name)
    VALUES (p_org_id, p_name)
    RETURNING id INTO v_role_id;

    RETURN v_role_id;
END;
$$;