CREATE OR REPLACE FUNCTION ems.sp_create_organization(
    p_name VARCHAR
)
RETURNS UUID
LANGUAGE plpgsql
AS $$
DECLARE
    v_org_id UUID;
BEGIN
    INSERT INTO ems.organizations(name)
    VALUES (p_name)
    RETURNING id INTO v_org_id;

    RETURN v_org_id;
END;
$$;